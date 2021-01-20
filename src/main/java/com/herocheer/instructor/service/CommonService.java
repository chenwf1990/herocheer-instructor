package com.herocheer.instructor.service;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/20
 * @company 厦门熙重电子科技有限公司
 */
public interface CommonService {
    /**
     * @author chenwf
     * @desc 获取打卡状态
     * @date 2021/1/20
     * @param serviceBeginTime
     * @param serviceEndTime
     * @param signInTime
     * @param signOutTime
     * @return
     */
    int getPunchCardStatus(Long serviceBeginTime, Long serviceEndTime, Long signInTime, Long signOutTime);
}
