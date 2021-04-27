package com.herocheer.instructor.service.impl;

import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.domain.entity.EquipmentBorrowDetails;
import com.herocheer.instructor.dao.EquipmentBorrowDetailsDao;
import com.herocheer.instructor.domain.entity.EquipmentInfo;
import com.herocheer.instructor.domain.vo.EquipmentBorrowDetailsVo;
import com.herocheer.instructor.service.EquipmentBorrowDetailsService;
import com.herocheer.instructor.service.EquipmentInfoService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author makejava
 * @desc  器材借用详情 (EquipmentBorrowDetails)表服务实现类
 * @date 2021-04-20 15:34:08
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class EquipmentBorrowDetailsServiceImpl extends BaseServiceImpl<EquipmentBorrowDetailsDao, EquipmentBorrowDetails,Long> implements EquipmentBorrowDetailsService {

    @Resource
    EquipmentInfoService equipmentInfoService;

    @Override
    public Integer getUnreturnedQuantity(Long equipmentId) {
        Integer unreturnedQuantity=this.dao.getUnreturnedQuantity(equipmentId);
        if(unreturnedQuantity==null){
            unreturnedQuantity=0;
        }
        return unreturnedQuantity;
    }

    @Override
    public Integer saveBorrowDetails(EquipmentBorrowDetails equipmentBorrowDetails,Integer flag) {
        EquipmentInfo equipmentInfo=equipmentInfoService.get(equipmentBorrowDetails.getEquipmentId());
        //待归还数量
        Integer unreturnedQuantity=this.dao.getUnreturnedQuantity(equipmentBorrowDetails.getEquipmentId());
        if(unreturnedQuantity==null){
            unreturnedQuantity=0;
        }
        if(equipmentInfo==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "获取器材失败!");
        }
        if (flag.equals(0)){
            if(equipmentInfo.getStockNumber()-unreturnedQuantity<equipmentBorrowDetails.getBorrowQuantity()){
                throw new CommonException("{}已库存不足",equipmentBorrowDetails.getEquipmentName());
            }
        }else if(flag.equals(1)){
            if(equipmentInfo.getStockNumber()-unreturnedQuantity<equipmentBorrowDetails.getActualBorrowQuantity()){
                throw new CommonException("{}已库存不足",equipmentBorrowDetails.getEquipmentName());
            }
        }
        Integer count;
        if(equipmentBorrowDetails.getId()==null){
            count=this.dao.insert(equipmentBorrowDetails);
        }else {
            count=this.dao.update(equipmentBorrowDetails);
        }
        return count;
    }

    @Override
    public List<EquipmentBorrowDetailsVo> getDetailsByBorrowId(Long borrowId) {
        return this.dao.getDetailsByBorrowId(borrowId);
    }
}