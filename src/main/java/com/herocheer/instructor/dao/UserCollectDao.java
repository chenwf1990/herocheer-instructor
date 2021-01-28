package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.UserCollect;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.Map;

/**
 * @author chenwf
 * @desc  用户收藏(UserCollect)表数据库访问层
 * @date 2021-01-26 11:24:26
 * @company 厦门熙重电子科技有限公司
 */
public interface UserCollectDao extends BaseDao<UserCollect,Long>{
    /**
     * 删除收藏
     * @param params
     * @return
     */
    int deleteByParams(Map<String, Object> params);
}