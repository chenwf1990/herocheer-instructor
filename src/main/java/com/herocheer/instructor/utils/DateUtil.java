package com.herocheer.instructor.utils;

import com.herocheer.common.utils.StringUtils;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-01-18 15:18
 **/
public class DateUtil {
    /**
     * 把毫秒转成时分秒
     * @param seconds
     * @return
     */
    public static String secToTime(Long seconds) {
        seconds=seconds/1000;
        Long hour = seconds / 3600;
        Long minute = (seconds - hour * 3600) / 60;
        Long second = (seconds - hour * 3600 - minute * 60);

        StringBuffer sb = new StringBuffer();
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
        return sb.toString();
    }

    /**
     * 时间(HH:ss)转成毫秒
     * @param time
     * @return
     */
    public static Long timeToUnix(String time){
        if (StringUtils.isBlank(time)){
            return 0L;
        }
        String[] times=time.split(":");
        int hour=Integer.parseInt(times[0]);
        int min=Integer.parseInt(times[1]);
        Long unixTime=Long.valueOf((hour*3600+min*60)*1000);
        return unixTime;
    }
}
