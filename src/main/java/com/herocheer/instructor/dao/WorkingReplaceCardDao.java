package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.WorkingReplaceCard;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.Map;

/**
 * @author chenwf
 * @desc  值班补卡(WorkingReplaceCard)表数据库访问层
 * @date 2021-01-20 19:43:45
 * @company 厦门熙重电子科技有限公司
 */
public interface WorkingReplaceCardDao extends BaseDao<WorkingReplaceCard,Long>{

    /**
     * 查询补卡数量
     * @param cardMap
     * @return
     */
    int findReplaceCardCount(Map<String, Object> cardMap);
}