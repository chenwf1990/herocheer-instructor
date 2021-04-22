package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.domain.entity.EquipmentBorrow;
import com.herocheer.instructor.dao.EquipmentBorrowDao;
import com.herocheer.instructor.domain.entity.EquipmentBorrowDetails;
import com.herocheer.instructor.domain.entity.EquipmentInfo;
import com.herocheer.instructor.domain.entity.EquipmentRemand;
import com.herocheer.instructor.domain.entity.User;
import com.herocheer.instructor.domain.vo.EquipmentBorrowDetailsVo;
import com.herocheer.instructor.domain.vo.EquipmentBorrowQueryVo;
import com.herocheer.instructor.domain.vo.EquipmentBorrowSaveVo;
import com.herocheer.instructor.domain.vo.EquipmentBorrowVo;
import com.herocheer.instructor.domain.vo.EquipmentRemandVo;
import com.herocheer.instructor.enums.BorrowStatusEnums;
import com.herocheer.instructor.enums.RemandStatusEnums;
import com.herocheer.instructor.service.EquipmentBorrowDetailsService;
import com.herocheer.instructor.service.EquipmentBorrowService;
import com.herocheer.instructor.service.EquipmentInfoService;
import com.herocheer.instructor.service.EquipmentRemandService;
import com.herocheer.instructor.service.UserService;
import com.sun.org.apache.xalan.internal.res.XSLTErrorResources;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author makejava
 * @desc  器材借用 (EquipmentBorrow)表服务实现类
 * @date 2021-04-20 15:22:50
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class EquipmentBorrowServiceImpl extends BaseServiceImpl<EquipmentBorrowDao, EquipmentBorrow,Long> implements EquipmentBorrowService {

    @Resource
    private UserService userService;

    @Resource
    private EquipmentBorrowDetailsService equipmentBorrowDetailsService;

    @Resource
    private EquipmentRemandService equipmentRemandService;

    @Override
    public Page<EquipmentBorrow> queryPage(EquipmentBorrowQueryVo queryVo) {
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
        if(!user.getInsuranceStatus().equals(4)){
            throw new CommonException(ResponseCode.SERVER_ERROR, "保险未生效,无法借用器材!");
        }
        vo.setBorrower(user.getIxmUserRealName());
        vo.setGender(user.getSex());
        vo.setIdentityNumber(user.getCertificateNo());
        vo.setPhoneNumber(user.getPhone());
        //拼接借用器材
        StringBuffer borrowEquipment=new StringBuffer();
        if (vo.getBorrowDetails().isEmpty()){
            throw new CommonException(ResponseCode.SERVER_ERROR, "请选择要借用的器材!");
        }
        for(EquipmentBorrowDetails details:vo.getBorrowDetails()){
            borrowEquipment.append(details.getEquipmentName());
            borrowEquipment.append("*");
            borrowEquipment.append(details.getBorrowQuantity());
            borrowEquipment.append(",");
        }
        vo.setBorrowEquipment(borrowEquipment.toString().substring(0,borrowEquipment.length()-1));
        Integer count=this.dao.insert(vo);
        for(EquipmentBorrowDetails details:vo.getBorrowDetails()){
            details.setBorrowId(vo.getId());
            equipmentBorrowDetailsService.saveBorrowDetails(details,0);
        }
        return count;
    }

    @Override
    public Integer confirmBorrow(List<EquipmentBorrowDetails> details, Long userId) {
        StringBuffer borrowEquipment=new StringBuffer();
        User user=userService.get(userId);
        if(user==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取用户信息失败!");
        }
        Long borrowId=0L;
        for (EquipmentBorrowDetails borrowDetails:details){
            borrowEquipment.append(borrowDetails.getEquipmentName());
            borrowEquipment.append("*");
            borrowEquipment.append(borrowDetails.getActualBorrowQuantity());
            borrowEquipment.append(",");
            //待归还数量等于实际借用数量
            borrowDetails.setUnreturnedQuantity(borrowDetails.getActualBorrowQuantity());
            //保存借出人信息
            borrowDetails.setBorrowBy(user.getIxmUserRealName());
            borrowDetails.setBorrowUserId(user.getId());
            borrowDetails.setBorrowTime(System.currentTimeMillis());
            borrowDetails.setPhoneNumber(user.getPhone());
            equipmentBorrowDetailsService.saveBorrowDetails(borrowDetails,1);
            if(borrowDetails.getBorrowId()!=null){
                borrowId=borrowDetails.getBorrowId();
            }
        }
        EquipmentBorrow equipmentBorrow=this.dao.get(borrowId);
        if(equipmentBorrow==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取借用单据失败!");
        }
        equipmentBorrow.setStatus(BorrowStatusEnums.to_remand.getStatus());
        equipmentBorrow.setBorrowEquipment(borrowEquipment.toString().substring(0,borrowEquipment.length()-1));
        return this.dao.insert(equipmentBorrow);
    }

    @Override
    public Integer userReceipt(Long id) {
        EquipmentBorrow equipmentBorrow=new EquipmentBorrow();
        equipmentBorrow.setId(id);
        equipmentBorrow.setStatus(BorrowStatusEnums.to_borrow.getStatus());
        return this.dao.update(equipmentBorrow);
    }

    @Override
    public Integer overrule(Long id) {
        EquipmentBorrow equipmentBorrow=new EquipmentBorrow();
        equipmentBorrow.setId(id);
        equipmentBorrow.setStatus(BorrowStatusEnums.overrule.getStatus());
        return this.dao.update(equipmentBorrow);
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
    public Integer confirmRemand(List<EquipmentRemand> remand) {
        EquipmentBorrowDetails borrowDetails;
        Long borrowId=0L;
        for(EquipmentRemand equipmentRemand:remand){
            Integer remandQuantity=equipmentRemand.getRemandQuantity();
            equipmentRemand=equipmentRemandService.get(equipmentRemand.getId());
            if(equipmentRemand==null){
                throw new CommonException(ResponseCode.SERVER_ERROR, "获取归还申请失败!");
            }
            borrowDetails=equipmentBorrowDetailsService.get(equipmentRemand.getBorrowDetailsId());
            if(borrowDetails.getUnreturnedQuantity()<remandQuantity){
                throw new CommonException("{}超过待归还数量!",borrowDetails.getEquipmentName());
            }
            borrowDetails.setUnreturnedQuantity(borrowDetails.getUnreturnedQuantity()-remandQuantity);
            borrowDetails.setRemandQuantity(borrowDetails.getRemandQuantity()+remandQuantity);
            //没有待归还数量,设置器材为已归还
            if(borrowDetails.getUnreturnedQuantity().equals(0)){
                borrowDetails.setRemandStatus(1);
            }
            equipmentBorrowDetailsService.update(borrowDetails);
            equipmentRemand.setRemandQuantity(remandQuantity);
            equipmentRemand.setRemandStatus(RemandStatusEnums.to_confirmed.getStatus());
            equipmentRemandService.update(equipmentRemand);
            if(equipmentRemand.getBorrowId()!=null){
                borrowId=equipmentRemand.getBorrowId();
            }
        }
        EquipmentBorrow equipmentBorrow=this.dao.get(borrowId);
        equipmentBorrow.setRemandStatus(RemandStatusEnums.to_confirmed.getStatus());
        if(equipmentBorrow==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取借用单据失败!");
        }
        //查询是否还有未归还的器材
        Map<String,Object> map=new HashedMap();
        map.put("borrowId",equipmentBorrow.getId());
        map.put("remandStatus",0);
        Integer count=equipmentBorrowDetailsService.count(map);
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
                equipment.append("未归还,请及时归换。");
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
        this.dao.update(equipmentBorrow);
        return this.dao.update(equipmentBorrow);
    }

    @Override
    public EquipmentBorrowVo getEquipmentBorrow(Long id) {
        EquipmentBorrowVo borrowVo=this.dao.getEquipmentBorrow(id);
        if(borrowVo==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取借用单据不存在!");
        }
        List<EquipmentBorrowDetailsVo> detailsVoList=equipmentBorrowDetailsService.getDetailsByBorrowId(id);
        if(!detailsVoList.isEmpty()){
            for(int i=0;i<detailsVoList.size();i++){
                List<EquipmentRemand> remands=
                        equipmentRemandService.getRemandByDetailsId(detailsVoList.get(i).getId());
                if (!remands.isEmpty()){
                    detailsVoList.get(i).setRemands(remands);
                }
            }
            borrowVo.setBorrowDetails(detailsVoList);
        }
        return borrowVo;
    }
}