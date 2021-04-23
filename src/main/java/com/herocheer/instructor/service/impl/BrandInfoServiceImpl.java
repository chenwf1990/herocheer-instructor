package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.instructor.domain.entity.BrandInfo;
import com.herocheer.instructor.dao.BrandInfoDao;
import com.herocheer.instructor.domain.vo.AssociationManageVo;
import com.herocheer.instructor.service.BrandInfoService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author makejava
 * @desc  品牌管理 (BrandInfo)表服务实现类
 * @date 2021-04-19 15:52:24
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class BrandInfoServiceImpl extends BaseServiceImpl<BrandInfoDao, BrandInfo,Long> implements BrandInfoService {

    @Override
    public Page<BrandInfo> queryPage(String brandName, Integer pageNo, Integer pageSize) {
        Page page = Page.startPage(pageNo,pageSize);
        Map<String,Object> map=new HashMap<>();
        map.put("brandName",brandName);
        List<BrandInfo> list=this.dao.findList(map);
        page.setDataList(list);
        return page;
    }

    @Override
    public Integer isEnable(Long id, Integer isEnable) {
        BrandInfo brandInfo=new BrandInfo();
        brandInfo.setId(id);
        brandInfo.setIsEnable(isEnable);
        return this.dao.update(brandInfo);
    }

    @Override
    public Integer addBrand(BrandInfo brandInfo) {
        Integer count=this.dao.getCount(brandInfo.getNumbering(),null);
        if(count>0){
            throw new CommonException(ResponseCode.SERVER_ERROR, "编号重复!");
        }
        return this.dao.insert(brandInfo);
    }

    @Override
    public Integer updateBrand(BrandInfo brandInfo) {
        Integer count=this.dao.getCount(brandInfo.getNumbering(),brandInfo.getId());
        if(count>0){
            throw new CommonException(ResponseCode.SERVER_ERROR, "编号重复!");
        }
        return this.dao.update(brandInfo);
    }

    @Override
    public Integer deleteBrand(Long id) {
        return this.dao.delete(id);
    }

    @Override
    public List<BrandInfo> queryList() {
        Map<String,Object> map=new HashedMap();
        map.put("isEnable",0);
        return this.dao.findByLimit(map);
    }
}