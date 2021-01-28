package com.herocheer.instructor.service.impl;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.dao.WorkingScheduleUserDao;
import com.herocheer.instructor.domain.vo.ServiceHoursQueryVo;
import com.herocheer.instructor.domain.vo.ServiceHoursReportVo;
import com.herocheer.instructor.service.ReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/28
 * @company 厦门熙重电子科技有限公司
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Resource
    private WorkingScheduleUserDao workingScheduleUserDao;
    /**
     * @param serviceHoursQueryVo
     * @return
     * @author chenwf
     * @desc 服务报表统计
     * @date 2021/1/28
     */
    @Override
    public Page<ServiceHoursReportVo> serviceHoursReport(ServiceHoursQueryVo serviceHoursQueryVo) {
        Page page = Page.startPage(serviceHoursQueryVo.getPageNo(),serviceHoursQueryVo.getPageSize());
        List<ServiceHoursReportVo> reportVos = workingScheduleUserDao.serviceHoursReport(serviceHoursQueryVo);
        page.setDataList(reportVos);
        return page;
    }
}
