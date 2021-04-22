package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.BrandInfo;
import com.herocheer.instructor.domain.entity.EquipmentInfo;
import com.herocheer.instructor.dao.EquipmentInfoDao;
import com.herocheer.instructor.domain.vo.EquipmentInfoQueryVo;
import com.herocheer.instructor.domain.vo.EquipmentInfoStockVo;
import com.herocheer.instructor.service.EquipmentInfoService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author makejava
 * @desc  器材信息 (EquipmentInfo)表服务实现类
 * @date 2021-04-19 17:18:25
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class EquipmentInfoServiceImpl extends BaseServiceImpl<EquipmentInfoDao, EquipmentInfo,Long> implements EquipmentInfoService {


    @Override
    public Page<EquipmentInfo> queryPage(EquipmentInfoQueryVo queryVo) {
        Page page = Page.startPage(queryVo.getPageNo(),queryVo.getPageSize());
        List<EquipmentInfo> list=this.dao.findList(queryVo);
        page.setDataList(list);
        return page;
    }

    @Override
    public Page<EquipmentInfoStockVo> queryStockPage(EquipmentInfoQueryVo queryVo) {
        Page page = Page.startPage(queryVo.getPageNo(),queryVo.getPageSize());
        List<EquipmentInfoStockVo> list=this.dao.findStockList(queryVo);
        page.setDataList(list);
        return page;
    }

    @Override
    public EquipmentInfo getEquipment(Long id) {
        return this.dao.get(id);
    }

    @Override
    public Integer addEquipment(EquipmentInfo equipmentInfo) {
        return this.dao.insert(equipmentInfo);
    }

    @Override
    public Integer updateEquipment(EquipmentInfo equipmentInfo) {
        return this.dao.update(equipmentInfo);
    }

    @Override
    public Integer deleteEquipment(Long id) {
        return this.dao.delete(id);
    }
}