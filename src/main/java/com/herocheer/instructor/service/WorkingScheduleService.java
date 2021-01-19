package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.WorkingSchedule;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.WorkingScheduleListVo;
import com.herocheer.instructor.domain.vo.WorkingScheduleQueryVo;
import com.herocheer.instructor.domain.vo.WorkingVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author chenwf
 * @desc  排班表(WorkingSchedule)表服务接口
 * @date 2021-01-11 14:30:02
 * @company 厦门熙重电子科技有限公司
 */
public interface WorkingScheduleService extends BaseService<WorkingSchedule,Long> {
    /**
     * @author chenwf
     * @desc  添加排班信息
     * @date 2021-01-11 14:30:02
     * @param workingSchedulsVo
     */
    void addWorkingScheduls(List<WorkingVo> workingSchedulsVo);
    /**
     * @author chenwf
     * @desc  根据id获取排班信息
     * @date 2021-01-11 14:30:02
     * @param id
     */
    WorkingVo getWorkingScheduls(Long id);

    /**
     * @author chenwf
     * @desc  编辑排班信息
     * @date 2021-01-12 08:47:02
     * @param workingVo
     */
    void updateWorkingScheduls(WorkingVo workingVo);

    /**
     * @author chenwf
     * @desc  排班列表
     * @date 2021-01-12 08:47:02
     * @param workingScheduleQueryVo
     * @return
     */
    Page<WorkingScheduleListVo> queryPageList(WorkingScheduleQueryVo workingScheduleQueryVo);

    /**
     * @author chenwf
     * @desc  批量删除排班信息
     * @date 2021-01-12 08:47:02
     * @param ids
     * @return
     */
    long batchDelete(String ids);

    /**
     * @author chenwf
     * @desc  排班模板导出
     * @date 2021-01-12 08:47:02
     * @param courierStationId
     * @param serviceTimeId
     * @param response
     */
    void templateExport(Long courierStationId, Long serviceTimeId, HttpServletResponse response);

    /**
     * @author chenwf
     * @desc  排班信息导入
     * @date 2021-01-19 09:47:02
     * @param courierStationId
     * @param serviceTimeId
     * @param multipartFile
     */
    void workingScheduleImport(Long courierStationId, Long serviceTimeId, MultipartFile multipartFile);
}