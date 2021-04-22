package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.BrandInfo;
import com.herocheer.common.base.service.BaseService;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @author makejava
 * @desc  品牌管理 (BrandInfo)表服务接口
 * @date 2021-04-19 15:52:24
 * @company 厦门熙重电子科技有限公司
 */
public interface BrandInfoService extends BaseService<BrandInfo,Long> {

    /**
     * 分页查询器材品牌
     * @param brandName
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<BrandInfo> queryPage(String brandName,Integer pageNo,Integer pageSize);

    /**
     * 保存品牌
     * @param brandInfo
     * @return
     */
    Integer addBrand(BrandInfo brandInfo);

    /**
     * 修改品牌
     * @param brandInfo
     * @return
     */
    Integer updateBrand(BrandInfo brandInfo);

    /**
     * 删除品牌
     * @param id
     * @return
     */
    Integer deleteBrand(Long id);

    /**
     * 不分页查询品牌
     * @return
     */
    List<BrandInfo> queryList();

}