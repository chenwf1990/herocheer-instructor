package com.herocheer.instructor.utils;

import com.herocheer.common.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-01-18 15:18
 **/
public class DateUtil extends cn.hutool.core.date.DateUtil {
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY = "yyyy";
    public static final String YYYYMM = "yyyyMM";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDDHH = "yyyyMMddHH";
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String IMAGE_PARAENT = "yyyy/MM/dd";
    public static final Long ONE_HOURS = 1 * 60 * 60 * 1000L;
    public static final Long TWO_HOURS = 2 * 60 * 60 * 1000L;
    /**
     * 把毫秒转成时分秒
     * @param seconds
     * @return
     */
    public static String secToTime(Long seconds) {
        if(seconds<0){
            return "0小时";
        }
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

    /**
     * 判断时间搓是否再时间段内
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean betweenTime(Long beginTime,Long endTime){
        return betweenTime(beginTime,endTime,System.currentTimeMillis());
    }

    public static boolean betweenTime(Long beginTime,Long endTime,Long curTime){
        if(curTime >= beginTime && curTime <= endTime){
            return true;
        }
        return false;
    }

    public static boolean betweenTime(){
        Date date = new Date();

        return betweenTime(DateUtil.beginOfDay(date).getTime(),DateUtil.endOfDay(date).getTime(),date.getTime());
    }

    /**
     * 获取当前时间
     * @return
     */
    public static String getNewTime(){
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }
}
