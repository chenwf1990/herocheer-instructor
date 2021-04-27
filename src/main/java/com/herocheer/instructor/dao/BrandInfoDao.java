package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.BrandInfo;
import com.herocheer.mybatis.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author makejava
 * @desc  品牌管理 (BrandInfo)表数据库访问层
 * @date 2021-04-19 15:52:24
 * @company 厦门熙重电子科技有限公司
 */
public interface BrandInfoDao extends BaseDao<BrandInfo,Long>{

    List<BrandInfo> findList(Map<String,Object> map);

    Integer getCount(@Param("numbering")String numbering,@Param("id")Long id);

}