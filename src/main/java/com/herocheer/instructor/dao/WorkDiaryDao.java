package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.WorkDiary;
import com.herocheer.instructor.domain.vo.WorkDiaryVO;
import com.herocheer.mybatis.base.dao.BaseDao;

import java.util.List;

/**
 * @author gaorh
 * @desc 工作日志(WorkDiary)表数据库访问层
 * @date 2021-06-07 17:32:37
 * @company 厦门熙重电子科技有限公司
 */
public interface WorkDiaryDao extends BaseDao<WorkDiary, Long> {

    /**
     * 选择日记列表
     *
     * @param workDiaryVO 工作日记签证官
     * @return {@link List<WorkDiary>}
     */
    List<WorkDiary> selectDiaryByPage(WorkDiaryVO workDiaryVO);

}
