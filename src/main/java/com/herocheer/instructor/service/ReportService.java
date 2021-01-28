package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.vo.ServiceHoursQueryVo;
import com.herocheer.instructor.domain.vo.ServiceHoursReportVo;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/28
 * @company 厦门熙重电子科技有限公司
 */
public interface ReportService {
    /**
     * @author chenwf
     * @desc 服务报表统计
     * @date 2021/1/28
     * @param serviceHoursQueryVo
     * @return
     */
    Page<ServiceHoursReportVo> serviceHoursReport(ServiceHoursQueryVo serviceHoursQueryVo);
}
