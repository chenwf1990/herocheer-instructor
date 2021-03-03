package com.herocheer.instructor.utils;

import cn.hutool.core.util.ObjectUtil;
import com.herocheer.common.utils.StringUtils;

import java.text.DateFormat;
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
    public static final String YYYY_MM_DD_1 = "yyyy/MM/dd";
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

    /**
     *  判断是否符合指定时间格式
     * @param o
     * @param format
     * @return
     */
    public static Date isFormat(Object o, String format) {
        if(ObjectUtil.isEmpty(o)){
            return null;
        }
        if(o instanceof Date){
            return (Date) o;
        }
        DateFormat formatter = new SimpleDateFormat(format);
        formatter.setLenient(false);
        try{
            Date date = formatter.parse(o.toString());
            return date;
        }catch(Exception e){
            return null;
        }
    }

    /**
     * 时间增加一分钟
     * @param time
     * @param gap
     * @return
     */
    public static String addMin(String time, int gap){
        if (time.equals("23:59")){
            return time;
        }
        try {
            String[] s = time.split(":");
            int min = Integer.parseInt(s[1]) + gap % 60;
            int hour =  Integer.parseInt(s[0]) + (gap / 60) ;
            if (min>=60){
                hour+=1;
                min -=60;
            }
            if (hour>=24){
                hour =  hour % 24;
            }
            return ("0"+hour).substring(("0"+hour).length()-2,("0"+hour).length())+
                    ":"+
                    ("0"+min).substring(("0"+min).length()-2,(("0"+min).length()));

        } catch (Exception e) {
            System.out.println("addMin Error!" + e.toString());
            return "";
        }
    }
    public static String lessMin(String time, int gap){
        if(time.equals("00:00")){
            return time;
        }
        try {
            String[] s = time.split(":");
            int min = Integer.parseInt(s[1]);
            int hour =  Integer.parseInt(s[0]) ;
            if (min<1){
                hour-=1;
                min +=60;
            }
            min=min-1;
            if (hour>=24){
                hour =  hour % 24;
            }
            return ("0"+hour).substring(("0"+hour).length()-2,("0"+hour).length())+
                    ":"+
                    ("0"+min).substring(("0"+min).length()-2,(("0"+min).length()));

        } catch (Exception e) {
            System.out.println("addMin Error!" + e.toString());
            return "";
        }
    }
    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到天的字符串
     * @param
     * @return
     */
    public static String timeStamp2Date(Long seconds) {
        if(seconds == null){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        return sdf.format(new Date(seconds));
    }
    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @param
     * @return
     */
    public static String timeStamp2DateTime(Long seconds) {
        if(seconds == null){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return sdf.format(new Date(seconds));
    }
}
