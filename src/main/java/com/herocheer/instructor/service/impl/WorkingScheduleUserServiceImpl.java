package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.WorkingScheduleUser;
import com.herocheer.instructor.dao.WorkingScheduleUserDao;
import com.herocheer.instructor.domain.vo.WorkingScheduleUserQueryVo;
import com.herocheer.instructor.domain.vo.WorkingSchedulsUserVo;
import com.herocheer.instructor.service.WorkingScheduleUserService;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author chenwf
 * @desc  排班表人员表(WorkingScheduleUser)表服务实现类
 * @date 2021-01-11 14:30:02
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class WorkingScheduleUserServiceImpl extends BaseServiceImpl<WorkingScheduleUserDao, WorkingScheduleUser,Long> implements WorkingScheduleUserService {


    /**
     * @param workingScheduleUserQueryVo
     * @return
     * @author chenwf
     * @desc 值班人员列表查询
     * @date 2021-01-12 08:57:02
     */
    @Override
    public Page<WorkingSchedulsUserVo> queryPageList(WorkingScheduleUserQueryVo workingScheduleUserQueryVo) {
        Page page = Page.startPage(workingScheduleUserQueryVo.getPageNo(),workingScheduleUserQueryVo.getPageSize());
        List<WorkingSchedulsUserVo> dataList = this.dao.queryPageList(workingScheduleUserQueryVo);
        page.setDataList(dataList);
        return page;
    }

    /**
     * @param workingScheduleUsers
     * @author chenwf
     * @desc 批量插入值班人员信息
     * @date 2021-01-11 14:30:02
     */
    @Override
    public void batchInsert(List<WorkingScheduleUser> workingScheduleUsers) {
        this.dao.batchInsert(workingScheduleUsers);
    }

    /**
     * @param params
     * @author chenwf
     * @desc 根据相关参数删除值班人员信息
     * @date 2021-01-12 08:57:02
     */
    @Override
    public long deleteByMap(Map<String, Object> params) {
        return this.dao.deleteByMap(params);
    }
}