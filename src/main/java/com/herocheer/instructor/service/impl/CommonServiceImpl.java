package com.herocheer.instructor.service.impl;

import com.herocheer.instructor.enums.SignStatusEnums;
import com.herocheer.instructor.service.CommonService;
import com.herocheer.instructor.utils.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenwf
 * @desc 通用业务层
 * @date 2021/1/20
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class CommonServiceImpl implements CommonService {
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
    @Override
    public int getPunchCardStatus(Long serviceBeginTime, Long serviceEndTime, Long signInTime, Long signOutTime) {
        Long curTime = System.currentTimeMillis();
        int signStatus = SignStatusEnums.SIGN_ABNORMAL.getStatus();//0.正常签到 1.异常签到 2.待完成
        if(signInTime != null && signOutTime != null){
            if(signInTime <= serviceBeginTime || signOutTime <= serviceEndTime + DateUtil.TWO_HOURS){
                signStatus = SignStatusEnums.SIGN_NORMAL.getStatus();
            }
        }else if (signInTime != null && signOutTime == null){
            if(curTime <= serviceBeginTime + DateUtil.ONE_HOURS){//签到时间
                if(signInTime <= serviceBeginTime){
                    signStatus = SignStatusEnums.SIGN_UN_FINISH.getStatus();
                }
            }else if(curTime >= serviceBeginTime + DateUtil.ONE_HOURS && curTime <= serviceEndTime + DateUtil.TWO_HOURS){
                signStatus = SignStatusEnums.SIGN_UN_FINISH.getStatus();
            }
        }else if (signInTime == null || signOutTime == null) {
            if (curTime <= serviceBeginTime) {
                signStatus = SignStatusEnums.SIGN_UN_FINISH.getStatus();
            }
        }
        return signStatus;
    }
}
