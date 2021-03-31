package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.Banner;
import com.herocheer.instructor.domain.vo.BannerQueryVo;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;

/**
 * @author chenwf
 * @desc  banner管理(Banner)表数据库访问层
 * @date 2021-03-17 09:43:08
 * @company 厦门熙重电子科技有限公司
 */
public interface BannerDao extends BaseDao<Banner,Long>{

    /**
     * 分页查询banner
     * @param bannerQueryVo
     * @return
     */
    List<Banner> queryPageList(BannerQueryVo bannerQueryVo);
}