package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.WorkDiary;
import com.herocheer.instructor.domain.vo.WorkDiaryVO;

/**
 * @author gaorh
 * @desc 工作日志(WorkDiary)表服务接口
 * @date 2021-06-07 17:32:37
 * @company 厦门熙重电子科技有限公司
 */
public interface WorkDiaryService extends BaseService<WorkDiary, Long> {

    /**
     * 找到工作日记列表
     *
     * @param workDiaryVO 工作日记签证官
     * @return {@link Page<WorkDiary>}
     */
    Page<WorkDiary> findWorkDiaryByPage(WorkDiaryVO workDiaryVO);

}
