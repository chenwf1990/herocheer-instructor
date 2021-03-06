package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.config.DelayTaskProducer;
import com.herocheer.instructor.dao.EquipmentBorrowDao;
import com.herocheer.instructor.domain.entity.CourierStation;
import com.herocheer.instructor.domain.entity.EquipmentBorrow;
import com.herocheer.instructor.domain.entity.EquipmentBorrowDetails;
import com.herocheer.instructor.domain.entity.EquipmentInfo;
import com.herocheer.instructor.domain.entity.EquipmentRemand;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.EquipmentBorrowDetailsVo;
import com.herocheer.instructor.domain.vo.EquipmentBorrowQueryVo;
import com.herocheer.instructor.domain.vo.EquipmentBorrowSaveVo;
import com.herocheer.instructor.domain.vo.EquipmentBorrowVo;
import com.herocheer.instructor.domain.vo.EquipmentDamageDetailsVo;
import com.herocheer.instructor.domain.vo.EquipmentDamageVo;
import com.herocheer.instructor.domain.vo.EquipmentRemandVo;
import com.herocheer.instructor.enums.BorrowStatusEnums;
import com.herocheer.instructor.enums.CacheKeyConst;
import com.herocheer.instructor.enums.RemandStatusEnums;
import com.herocheer.instructor.service.CourierStationService;
import com.herocheer.instructor.service.EquipmentBorrowDetailsService;
import com.herocheer.instructor.service.EquipmentBorrowService;
import com.herocheer.instructor.service.EquipmentDamageDetailsService;
import com.herocheer.instructor.service.EquipmentDamageService;
import com.herocheer.instructor.service.EquipmentInfoService;
import com.herocheer.instructor.service.EquipmentRemandService;
import com.herocheer.instructor.service.UserService;
import com.herocheer.instructor.service.WorkingScheduleUserService;
import com.herocheer.instructor.utils.DateUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author makejava
 * @desc  器材借用 (EquipmentBorrow)表服务实现类
 * @date 2021-04-20 15:22:50
 * @company 厦门熙重电子科技有限公司
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class EquipmentBorrowServiceImpl extends BaseServiceImpl<EquipmentBorrowDao, EquipmentBorrow,Long> implements EquipmentBorrowService {

    @Resource
    private UserService userService;

    @Resource
    private EquipmentBorrowDetailsService equipmentBorrowDetailsService;

    @Resource
    private EquipmentRemandService equipmentRemandService;

    @Resource
    private EquipmentInfoService equipmentInfoService;

    @Resource
    private CourierStationService courierStationService;

    @Resource
    private EquipmentDamageService equipmentDamageService;

    @Resource
    private EquipmentDamageDetailsService equipmentDamageDetailsService;

    @Resource
    private WorkingScheduleUserService workingScheduleUserService;

    @Resource
    private DelayTaskProducer delayTaskProducer;

    @Override
    public Page<EquipmentBorrow> queryPage(EquipmentBorrowQueryVo queryVo,Long userId) {
        User user=userService.get(userId);
        if (user==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取用户信息失败!");
        }

        //后台管理员 查询不做限制   (给陈以翔开通借用归还审核所有权限，公众号上所有数据)
        if(user.getUserType().equals(4) || "PmwOukoG5GhhLUgkrhyDXrC6omB3eMSlMI+pZB1D2Qw=".equalsIgnoreCase(user.getCertificateNo())){
            queryVo.setQueryType(3);
        }

        // 用户查询
        if(queryVo.getQueryType()!=null && queryVo.getQueryType().equals(2)){
            queryVo.setUserId(userId);
        }

        // 驿站值班人员查询，注意：只能获取当天的器材借用记录，当天之后的暂不能获取
        if(queryVo.getQueryType()!=null && queryVo.getQueryType().equals(1)){
            List<Long> courierStationIds= workingScheduleUserService.findCourierStationId(userId);
            queryVo.setCourierStationIds(courierStationIds);
            queryVo.setUserId(userId);
        }

        // 分页
        Page page = Page.startPage(queryVo.getPageNo(),queryVo.getPageSize());
        List<EquipmentBorrow> list=this.dao.findList(queryVo);
        page.setDataList(list);
        return page;
    }

    @Override
    public Integer addBorrow(EquipmentBorrowSaveVo vo,Long userId) {
        User user=userService.get(userId);
        if(user==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取用户信息失败!");
        }
//        if(!user.getInsuranceStatus().equals(4)){
//            throw new CommonException(ResponseCode.SERVER_ERROR, "保险未生效,无法借用器材!");
//        }
        CourierStation courierStation=courierStationService.get(vo.getCourierId());
        if(courierStation==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取驿站信息失败");
        }
        vo.setCourierName(courierStation.getName());
        vo.setBorrower(user.getUserName());
        vo.setGender(user.getSex());
        vo.setIdentityNumber(user.getCertificateNo());
        vo.setPhoneNumber(user.getPhone());

        //拼接借用器材
        StringBuffer borrowEquipment=new StringBuffer();
        if (vo.getBorrowDetails().isEmpty()){
            throw new CommonException(ResponseCode.SERVER_ERROR, "请选择要借用的器材!");
        }
        List<EquipmentBorrowDetails> detailsList=vo.getBorrowDetails();
        for(int i=0;i<detailsList.size();i++){
            EquipmentInfo equipmentInfo=equipmentInfoService.get(detailsList.get(i).getEquipmentId());
            if (equipmentInfo==null){
                throw new CommonException(ResponseCode.SERVER_ERROR, "器材错误!");
            }
            borrowEquipment.append(equipmentInfo.getEquipmentName());
            borrowEquipment.append("*");
            borrowEquipment.append(detailsList.get(i).getBorrowQuantity());
            borrowEquipment.append(",");

            //保存器材信息
            detailsList.get(i).setEquipmentName(equipmentInfo.getEquipmentName());
            detailsList.get(i).setBrandName(equipmentInfo.getBrandName());
            detailsList.get(i).setModel(equipmentInfo.getModel());
            // 占用库存
            detailsList.get(i).setUnreturnedQuantity(detailsList.get(i).getBorrowQuantity());
        }
        vo.setBorrowEquipment(borrowEquipment.toString().substring(0,borrowEquipment.length()-1));

        // 生成借用单号(QCJY2021010001)
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMM");
        String borrowReceipt="QCJY"+dateFormat.format(new Date());
        Integer receiptCount=this.dao.getCountByReceipt(borrowReceipt);
        vo.setBorrowReceipt(String.format(borrowReceipt+ "%04d",receiptCount+1));
        Integer count=this.dao.insert(vo);

        // 保存器材借用明细
        for(EquipmentBorrowDetails details:vo.getBorrowDetails()){
            details.setBorrowId(vo.getId());
            equipmentBorrowDetailsService.saveBorrowDetails(details,0);
        }

        // 线上借用需在规定时间内领取，否则自动取消借用
        log.debug("当前借用信息：{}",vo);
        log.debug("当前借用信息ID：{}",vo.getId());
        log.debug("当前借用信息数据源：{}",vo.getSource());
        log.debug("当前借用信息借用时间开始：{}",vo.getBorrowTimeRangeStart());

        if(vo.getSource() != null && vo.getSource().equals(2) && StringUtils.hasText(vo.getBorrowTimeRangeStart())){
            log.debug("发送自动取消预约队列:{}",vo.getId());

            // 放入延时队列
            boolean delayBorrowStart = vo.getBorrowDate() + DateUtil.timeToUnix(vo.getBorrowTimeRangeStart()) <= System.currentTimeMillis();
            boolean delayBorrowEnd = vo.getBorrowDate() + DateUtil.timeToUnix(vo.getBorrowTimeRangeEnd()) >= System.currentTimeMillis();
            if(delayBorrowStart && delayBorrowEnd ){
                // 借用时段内借用的，释放库存时间按照当前借用时间+半小时后释放
                log.debug("借用时段内");
                delayTaskProducer.produce(vo.getId(),System.currentTimeMillis() + CacheKeyConst.AUTO_EXPIRETIME);
            }else {
                log.debug("借用时段外",vo);
                // 借用时段外借用的，释放库存时间按照借用开始时间后半个小时进行释放
                delayTaskProducer.produce(vo.getId(),vo.getBorrowDate()+ DateUtil.timeToUnix(vo.getBorrowTimeRangeStart()) + CacheKeyConst.AUTO_EXPIRETIME);
            }
        }
        return count;
    }

    @Override
    public Integer confirmBorrow(List<EquipmentBorrowDetails> details, Long userId) {
        User user = userService.get(userId);
        if(user == null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取用户信息失败!");
        }

        // 确认借用记录详情
        Long borrowId = 0L;
        StringBuffer borrowEquipment = new StringBuffer();
        for (EquipmentBorrowDetails borrowDetails:details){
            borrowEquipment.append(borrowDetails.getEquipmentName());
            borrowEquipment.append("*");
            borrowEquipment.append(borrowDetails.getActualBorrowQuantity());
            borrowEquipment.append(",");
            //待归还数量 == 实际借用数量
            borrowDetails.setUnreturnedQuantity(borrowDetails.getActualBorrowQuantity());
            //保存借出人信息
            borrowDetails.setBorrowBy(user.getUserName());
            borrowDetails.setBorrowUserId(user.getId());
            borrowDetails.setBorrowTime(System.currentTimeMillis());
            borrowDetails.setPhoneNumber(user.getPhone());
            equipmentBorrowDetailsService.saveBorrowDetails(borrowDetails,1);

            if(borrowDetails.getBorrowId() != null){
                borrowId=borrowDetails.getBorrowId();
            }
        }

        // 更新借用记录状态为待归还
        EquipmentBorrow equipmentBorrow=this.dao.get(borrowId);
        if(equipmentBorrow==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取借用单据失败!");
        }

        // 驳回必须在状态为待审核（0）时才能操作
        if(!equipmentBorrow.getStatus().equals(0)){
            throw new CommonException(ResponseCode.SERVER_ERROR, "确认借出失败，此状态下无法确认借出");
        }

        equipmentBorrow.setStatus(BorrowStatusEnums.to_borrow.getStatus());
        equipmentBorrow.setBorrowEquipment(borrowEquipment.toString().substring(0,borrowEquipment.length()-1));
        equipmentBorrow.setLenderId(user.getId());
        return this.dao.update(equipmentBorrow);
    }

    @Override
    public Integer userReceipt(Long id) {
        EquipmentBorrow equipmentBorrow=new EquipmentBorrow();
        equipmentBorrow.setId(id);
        equipmentBorrow.setStatus(BorrowStatusEnums.to_borrow.getStatus());
        return this.dao.update(equipmentBorrow);
    }

    @Override
    public EquipmentBorrow overrule(Long id,String reason,Long userId) {
        // 用户信息
        User user = userService.get(userId);
        if(user == null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取用户信息失败!");
        }

        EquipmentBorrow equipmentBorrow = this.dao.get(id);
        if(ObjectUtils.isEmpty(equipmentBorrow)){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取借用单据失败!");
        }

        // 驳回必须在状态为待审核（0）时才能操作
        if(!equipmentBorrow.getStatus().equals(0)){
            throw new CommonException(ResponseCode.SERVER_ERROR, "驳回失败，此状态下无法驳回!");
        }

        // 更新状态
        equipmentBorrow.setRejectReason(reason);
        equipmentBorrow.setStatus(BorrowStatusEnums.overrule.getStatus());
        this.dao.update(equipmentBorrow);

        // 驳回要释放库存
        List<EquipmentBorrowDetailsVo> equipmentBorrowDetailsList = equipmentBorrowDetailsService.getDetailsByBorrowId(id);
        for (EquipmentBorrowDetailsVo equipmentBorrowDetailsVO:equipmentBorrowDetailsList){
            EquipmentBorrowDetails equipmentBorrowDetails = equipmentBorrowDetailsVO.voToEntity(equipmentBorrowDetailsVO);
            // 取消借用时设置待归还数量为0，即释放库存
            equipmentBorrowDetails.setUnreturnedQuantity(0);

            // 更新驳回人信息和驳回时间
            equipmentBorrowDetails.setBorrowBy(user.getUserName());
            equipmentBorrowDetails.setBorrowUserId(user.getId());
            equipmentBorrowDetails.setBorrowTime(System.currentTimeMillis());
            equipmentBorrowDetailsService.update(equipmentBorrowDetails);
        }
        return equipmentBorrow;
    }

    @Override
    public Integer applyRemand(List<EquipmentRemand> remand) {
        Long borrowId=0L;
        for(EquipmentRemand equipmentRemand:remand){
            if(equipmentRemand.getBorrowId()==null||equipmentRemand.getBorrowDetailsId()==null){
                throw new CommonException(ResponseCode.SERVER_ERROR, "入参错误!");
            }
            EquipmentBorrowDetails details=equipmentBorrowDetailsService.get(equipmentRemand.getBorrowDetailsId());
            if(details==null){
                throw new CommonException(ResponseCode.SERVER_ERROR, "获取借用器材失败!");
            }
            equipmentRemand.setRemandStatus(RemandStatusEnums.apply_remand.getStatus());
            if(details.getUnreturnedQuantity()>0 && details.getRemandStatus().equals(0)){
                equipmentRemandService.insert(equipmentRemand);
            }
            if(equipmentRemand.getBorrowId()!=null){
                borrowId=equipmentRemand.getBorrowId();
            }
        }
        EquipmentBorrow equipmentBorrow=this.dao.get(borrowId);
        if(equipmentBorrow==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取借用单据失败!");
        }
        equipmentBorrow.setRemandStatus(RemandStatusEnums.apply_remand.getStatus());
        return this.dao.update(equipmentBorrow);
    }

    @Override
    public Integer confirmRemand(List<EquipmentRemand> remand,Long userId) {
        User user = userService.get(userId);
        if(user==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取用户信息失败!");
        }

        Long borrowId=0L;
        EquipmentBorrowDetails borrowDetails;
        for(EquipmentRemand equipmentRemand : remand){
            Integer remandQuantity = equipmentRemand.getRemandQuantity();
            equipmentRemand = equipmentRemandService.get(equipmentRemand.getId());
            if(equipmentRemand==null){
                throw new CommonException(ResponseCode.SERVER_ERROR, "获取归还申请失败!");
            }

            // 值班人员确认归还更新借用记录
            borrowDetails=equipmentBorrowDetailsService.get(equipmentRemand.getBorrowDetailsId());
            if(borrowDetails.getUnreturnedQuantity()<remandQuantity){
                throw new CommonException("{}超过待归还数量!",borrowDetails.getEquipmentName());
            }
            // 影响库存数
            borrowDetails.setUnreturnedQuantity(borrowDetails.getUnreturnedQuantity()-remandQuantity);
            borrowDetails.setRemandQuantity(borrowDetails.getRemandQuantity()+remandQuantity);
            //没有待归还数量,设置器材为已归还
            if(borrowDetails.getUnreturnedQuantity().equals(0)){
                borrowDetails.setRemandStatus(1);
            }
            equipmentBorrowDetailsService.update(borrowDetails);

            // 值班人员确认归还更新归还记录
            equipmentRemand.setRemandQuantity(remandQuantity);
            equipmentRemand.setRemandStatus(RemandStatusEnums.to_confirmed.getStatus());
            equipmentRemand.setReceiveBy(user.getUserName());
            equipmentRemand.setReceiveId(user.getId());
            equipmentRemand.setReceiveTime(System.currentTimeMillis());
            equipmentRemand.setPhoneNumber(user.getPhone());
            equipmentRemandService.update(equipmentRemand);

            if(equipmentRemand.getBorrowId()!=null){
                borrowId=equipmentRemand.getBorrowId();
            }
        }

        EquipmentBorrow equipmentBorrow=this.dao.get(borrowId);
        equipmentBorrow.setRemandStatus(RemandStatusEnums.to_confirmed.getStatus());
        equipmentBorrow.setRemandTime(System.currentTimeMillis());
        if(equipmentBorrow==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取借用单据失败!");
        }
        //查询是否还有未归还的器材
        Map<String,Object> map=new HashedMap();
        map.put("borrowId",equipmentBorrow.getId());
        List<EquipmentBorrowDetails> details=equipmentBorrowDetailsService.findByLimit(map);
        StringBuffer remandEquipment=new StringBuffer();
        Integer count=0;
        for(EquipmentBorrowDetails equipmentBorrowDetails:details){
            if(equipmentBorrowDetails.getRemandQuantity()>0){
                remandEquipment.append(equipmentBorrowDetails.getEquipmentName());
                remandEquipment.append("*");
                remandEquipment.append(equipmentBorrowDetails.getRemandQuantity());
                remandEquipment.append(",");
            }
            if(equipmentBorrowDetails.getRemandStatus()==0){
                count++;
            }
        }
        equipmentBorrow.setRemandEquipment(remandEquipment.toString().substring(0,remandEquipment.length()-1));
        //没有待归还的 设置订单状态为已归还
        if(count==0){
            equipmentBorrow.setStatus(BorrowStatusEnums.already_borrow.getStatus());
        }
        return this.dao.update(equipmentBorrow);
    }

    @Override
    public List<EquipmentRemandVo> remandList(Long id) {
        return equipmentRemandService.findRemandList(id, RemandStatusEnums.apply_remand.getStatus());
    }

    @Override
    public String getRemandMsg(Long id) {
        List<EquipmentRemandVo> remandVoList=equipmentRemandService.findRemandList(id, RemandStatusEnums.to_confirmed.getStatus());
        if(remandVoList.isEmpty()){
            return "";
        }
        StringBuffer equipment=new StringBuffer();
        equipment.append("你所借的");
        for(EquipmentRemandVo vo:remandVoList){
            equipment.append(vo.getEquipmentName()+"*"+vo.getThisRemandQuantity()+",");
        }
        equipment.append("已经归还成功。");
        Map<String,Object> map=new HashedMap();
        map.put("borrowId",id);
        map.put("remandStatus",0);
        List<EquipmentBorrowDetails> borrowDetails=equipmentBorrowDetailsService.findByLimit(map);
        if (!borrowDetails.isEmpty()){
            Integer flag=0;
            for (EquipmentBorrowDetails details:borrowDetails){
                if (details.getUnreturnedQuantity()>0){
                    equipment.append(details.getEquipmentName()+"*"+details.getUnreturnedQuantity()+",");
                    flag++;
                }
            }
            if(flag>0){
                equipment.append("未归还,请及时归还。");
            }
        }
        return equipment.toString();
    }

    @Override
    public Integer userConfirmRemand(Long id) {
        equipmentRemandService.updateRemandStatus(id,RemandStatusEnums.already_borrow.getStatus());
        EquipmentBorrow equipmentBorrow=new EquipmentBorrow();
        equipmentBorrow.setId(id);
        equipmentBorrow.setRemandStatus(RemandStatusEnums.already_borrow.getStatus());
        return this.dao.update(equipmentBorrow);
    }

    @Override
    public Integer confirmRemandTack() {
        List<Long> borrowIds=equipmentRemandService.findTimeOutRemand();
        Integer count=0;
        for(Long id:borrowIds){
            count+=userConfirmRemand(id);
        }
        return count;
    }

    @Override
    public EquipmentBorrowVo getEquipmentBorrow(Long id) {
        EquipmentBorrowVo borrowVo = this.dao.getEquipmentBorrow(id);
        if(borrowVo==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取借用单据不存在!");
        }
        List<EquipmentBorrowDetailsVo> detailsVoList = equipmentBorrowDetailsService.getDetailsByBorrowId(id);
        if(!detailsVoList.isEmpty()){
            for(int i=0;i<detailsVoList.size();i++){
                List<EquipmentRemand> remands = equipmentRemandService.getRemandByDetailsId(detailsVoList.get(i).getId());
                if (!remands.isEmpty()){
                    detailsVoList.get(i).setRemands(remands);
                }
            }
            borrowVo.setBorrowDetails(detailsVoList);
        }
        return borrowVo;
    }

    @Override
    public Integer getCountByUserId(Long id) {
        return this.dao.getCountByUserId(id);
    }

    @Override
    public Integer andDamage(EquipmentDamageVo vo) {
        Integer count=equipmentDamageService.insert(vo);
        if(vo.getDamageDetailsVos().isEmpty()){
            throw new CommonException(ResponseCode.SERVER_ERROR, "请选择报废器材!");
        }
        for(EquipmentDamageDetailsVo damageDetailsVo:vo.getDamageDetailsVos()){
            EquipmentBorrowDetails borrowDetails=equipmentBorrowDetailsService.get(damageDetailsVo.getBorrowDetailsId());
            if(borrowDetails.getUnreturnedQuantity()<1){
                throw new CommonException(ResponseCode.SERVER_ERROR, "该器材以归还成功,无需报损!");
            }
            if(borrowDetails==null){
                throw new CommonException(ResponseCode.SERVER_ERROR, "获取借用详情失败!");
            }
            if(damageDetailsVo.getDamagQuantity()>borrowDetails.getUnreturnedQuantity()){
                throw new CommonException(ResponseCode.SERVER_ERROR, "报损数量不能大于待还数量!");
            }
            borrowDetails.setUnreturnedQuantity(borrowDetails.getUnreturnedQuantity()-damageDetailsVo.getDamagQuantity());
            borrowDetails.setDamageQuantity(borrowDetails.getDamageQuantity()+damageDetailsVo.getDamagQuantity());
            //没有待归还数量,设置器材为已归还
            if(borrowDetails.getUnreturnedQuantity()<1){
                borrowDetails.setRemandStatus(1);
            }
            equipmentBorrowDetailsService.update(borrowDetails);
            damageDetailsVo.setDamageId(vo.getId());
            equipmentDamageDetailsService.insert(damageDetailsVo);
            EquipmentInfo equipmentInfo=equipmentInfoService.get(borrowDetails.getEquipmentId());
            if(equipmentInfo==null){
                throw new CommonException(ResponseCode.SERVER_ERROR, "获取器材信息失败!");
            }
            equipmentInfo.setStockNumber(equipmentInfo.getStockNumber()-damageDetailsVo.getDamagQuantity());
            equipmentInfoService.updateEquipment(equipmentInfo,damageDetailsVo.getId());
        }
        Map<String,Object> map=new HashedMap();
        map.put("borrowId",vo.getBorrowId());
        map.put("remandStatus",0);
        if(equipmentBorrowDetailsService.count(map)==0){
            EquipmentBorrow equipmentBorrow=new EquipmentBorrow();
            equipmentBorrow.setId(vo.getBorrowId());
            equipmentBorrow.setStatus(BorrowStatusEnums.already_borrow.getStatus());
            this.dao.update(equipmentBorrow);
        }
        return count;
    }

    @Override
    public List<EquipmentDamageVo> getDamage(Long borrowId) {
        List<EquipmentDamageVo> damageVos=equipmentDamageService.findList(borrowId);
        if(damageVos.isEmpty()){
            return null;
        }
        for(int i=0;i<damageVos.size();i++){
            List<EquipmentDamageDetailsVo> list=equipmentDamageDetailsService.findList(damageVos.get(i).getId());
            if(!list.isEmpty()){
                damageVos.get(i).setDamageDetailsVos(list);
            }
        }
        return damageVos;
    }

    /**
     * 取消借用预约并释放库存
     *
     * @param borrowId 借身份证
     * @param status   状态
     * @return {@link EquipmentBorrow}
     */
    @Override
    public EquipmentBorrow modifyBorrowInfoByInfo(Long borrowId,Integer status) {
        EquipmentBorrow equipmentBorrow = this.dao.get(borrowId);
        if(ObjectUtils.isEmpty(equipmentBorrow)){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取借用单据失败!");
        }

        // 只有器材借用预约30分钟后还是待审核状态才需更新状态为已过期
        if(equipmentBorrow.getStatus().equals(0)){
            throw new CommonException( "您已在规定时间借出器材，无法取消预约！");
        }

        // 更新状态为已过期
        equipmentBorrow.setStatus(status);
        this.dao.update(equipmentBorrow);

        // 释放库存
        List<EquipmentBorrowDetailsVo> equipmentBorrowDetailsList = equipmentBorrowDetailsService.getDetailsByBorrowId(borrowId);
        for (EquipmentBorrowDetailsVo equipmentBorrowDetailsVO:equipmentBorrowDetailsList){
            EquipmentBorrowDetails equipmentBorrowDetails = equipmentBorrowDetailsVO.voToEntity(equipmentBorrowDetailsVO);
            // 取消借用时设置待归还数量为0，即释放库存
            equipmentBorrowDetails.setUnreturnedQuantity(0);
            equipmentBorrowDetailsService.update(equipmentBorrowDetails);
        }

        return equipmentBorrow;
    }
}