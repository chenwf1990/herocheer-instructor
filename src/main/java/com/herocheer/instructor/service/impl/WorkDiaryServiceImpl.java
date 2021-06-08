package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.dao.WorkDiaryDao;
import com.herocheer.instructor.domain.entity.WorkDiary;
import com.herocheer.instructor.domain.vo.WorkDiaryVO;
import com.herocheer.instructor.service.WorkDiaryService;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author gaorh
 * @desc 工作日志(WorkDiary)表服务实现类
 * @date 2021-06-07 17:32:37
 * @company 厦门熙重电子科技有限公司
 */
@Slf4j
@Service
@Transactional
public class WorkDiaryServiceImpl extends BaseServiceImpl<WorkDiaryDao, WorkDiary, Long> implements WorkDiaryService {

    /**
     * 找到工作日记列表
     *
     * @param workDiaryVO 工作日记签证官
     * @return {@link Page <WorkDiary>}
     */
    @Override
    public Page<WorkDiary> findWorkDiaryByPage(WorkDiaryVO workDiaryVO) {
        Page<WorkDiary> page = Page.startPage(workDiaryVO.getPageNo(), workDiaryVO.getPageSize());
        List<WorkDiary> diaryList = this.dao.selectDiaryByPage(workDiaryVO);
        page.setDataList(diaryList);
        return page;
    }
}
