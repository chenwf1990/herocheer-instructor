package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.RecruitInfo;
import com.herocheer.instructor.domain.vo.RecruitInfoQueryVo;
import com.herocheer.instructor.domain.vo.RecruitInfoVo;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;

/**
 * @author makejava
 * @desc  招募信息主表 (RecruitInfo)表数据库访问层
 * @date 2021-01-06 17:32:02
 * @company 厦门熙重电子科技有限公司
 */
public interface RecruitInfoDao extends BaseDao<RecruitInfo,Long>{
    /**
     * 查询招募信息列表
     * @param queryVo
     * @return
     */
    List<RecruitInfo> queryList(RecruitInfoQueryVo queryVo);

    /**
     * 根据id查询招募信息详情
     * @param Id
     * @return
     */
    RecruitInfoVo getRecruitInfoVoById(Long Id);

}