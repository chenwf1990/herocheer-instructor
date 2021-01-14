package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.RecruitInfo;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.RecruitInfoQueryVo;
import com.herocheer.instructor.domain.vo.RecruitInfoVo;

import java.util.List;

/**
 * @author makejava
 * @desc  招募信息主表 (RecruitInfo)表服务接口
 * @date 2021-01-06 17:32:02
 * @company 厦门熙重电子科技有限公司
 */
public interface RecruitInfoService extends BaseService<RecruitInfo,Long> {
    Page<RecruitInfo> queryPageList(RecruitInfoQueryVo queryVo);

    RecruitInfoVo getRecruitInfoVoById(Long id);
}