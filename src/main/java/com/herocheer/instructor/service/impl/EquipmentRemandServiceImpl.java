package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.domain.entity.EquipmentRemand;
import com.herocheer.instructor.dao.EquipmentRemandDao;
import com.herocheer.instructor.domain.vo.EquipmentRemandVo;
import com.herocheer.instructor.service.EquipmentRemandService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author makejava
 * @desc  器材归还记录 (EquipmentRemand)表服务实现类
 * @date 2021-04-20 15:22:50
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class EquipmentRemandServiceImpl extends BaseServiceImpl<EquipmentRemandDao, EquipmentRemand,Long> implements EquipmentRemandService {

    @Override
    public List<EquipmentRemandVo> findRemandList(Long borrowId, Integer remandStatus) {
        return this.dao.findRemandList(borrowId,remandStatus);
    }

    @Override
    public Integer updateRemandStatus(Long borrowId, Integer remandStatus) {
        return this.dao.updateRemandStatus(borrowId,remandStatus);
    }

    @Override
    public List<EquipmentRemand> getRemandByDetailsId(Long borrowDetailsId) {
        return this.dao.getRemandByDetailsId(borrowDetailsId);
    }

    @Override
    public List<Long> findTimeOutRemand() {
        return this.dao.findTimeOutRemand();
    }
}