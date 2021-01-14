package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.RecruitInfo;
import com.herocheer.instructor.dao.RecruitInfoDao;
import com.herocheer.instructor.domain.vo.RecruitInfoQueryVo;
import com.herocheer.instructor.domain.vo.RecruitInfoVo;
import com.herocheer.instructor.service.RecruitInfoService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author makejava
 * @desc  招募信息主表 (RecruitInfo)表服务实现类
 * @date 2021-01-06 17:32:02
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class RecruitInfoServiceImpl extends BaseServiceImpl<RecruitInfoDao, RecruitInfo,Long> implements RecruitInfoService {

    @Override
    public Page<RecruitInfo> queryPageList(RecruitInfoQueryVo queryVo) {
        Page page=Page.startPage(queryVo.getPageNo(),queryVo.getPageSize());
        List<RecruitInfo> recruitInfoList=dao.queryList(queryVo);
        page.setDataList(recruitInfoList);
        return page;
    }

    @Override
    public RecruitInfoVo getRecruitInfoVoById(Long id) {
        return dao.getRecruitInfoVoById(id);
    }
}