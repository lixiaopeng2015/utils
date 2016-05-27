package main.java.com.sampson.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA
 * ProjectName: util
 * CreateUser:  lixiaopeng
 * CreateTime : 2016/5/12 18:41
 * Email: lixiaopeng913@sina.com
 * ModifyUser: lixiaopeng
 * Class Description:
 * To change this template use File | Settings | File Template
 */
public class DateUtils implements Serializable{

    private static final long serialVersionUID = -5212243097876847200L;

    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    private static Long DAY_TIMES = 24 * 60 * 60 * 1000l;
    private static Long HOUR_TIMES = 60 * 60 * 1000l;
    private static Long MIN_TIMES = 60 * 1000l;
    private static Long SECOND_TIMES = 1000l;

    public static final String FORMAT_DATE_ONLY = "yyyy-MM-dd";
    public static final String FORMAT_COMPACT = "yyyyMMddHHmmss";
    public static final String FORMAT_NORMAL = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_NO_SECOND = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_CHAIN_ONLY = "yyyy年MM月dd日";
    public static final String FORMAT_YEAR_MONTH = "yyyy-MM";

    public static final long DATE_SECOND=86400;//一天有86400秒
    public static final long DATE_MINUTE=1440;//一天有1440分
    public static final long MINUTE_SECOND=60;//一天有60分

    public final static int ONE_DAY_TIMES = 86400000;

    /**
     * String 转化成Date
     * @param str 源字符串
     * @param format 格式化格式
     * @return Date
     */
    public static Date parse(String str, String format) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat(format);
            sf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            return sf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 格式化日期格式
     * @param date 日期
     * @param format 格式化格式（参见类头部的final类型）
     * @return String
     */
    public static String format(Date date, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        sf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return sf.format(date);
    }

    /**
     * 格式化Timestamp格式
     * @param timestamp 源数据类型
     * @param format 格式化格式
     * @return String
     */
    public static String format(Timestamp timestamp, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        sf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return sf.format(timestamp);
    }

    /**
     * 生成制定日期的Date对象，从0点开始
     * @param year
     * @param month
     * @param days
     * @return
     */
    public static Date createDate(int year, int month, int days) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, days, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 计算时间差
     * @param beginTime 开始时间，格式：yyyy-MM-dd HH:mm:ss
     * @param endTime 结束时间，格式：yyyy-MM-dd HH:mm:ss
     * @return 从开始时间到结束时间之间的时间差（秒）
     */
    public static long getTimeDifference(String beginTime, String endTime) {
        long between = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date end = null;
        Date begin = null;
        try {// 将截取到的时间字符串转化为时间格式的字符串
            end = sdf.parse(endTime);
            begin = sdf.parse(beginTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒

        return between;
    }

    /**
     * 计算时间差
     * @param beginTime 开始时间字符串
     * @param endTime 结束时间字符串
     * @return String（格式为：xx小时xx分xx秒，如超过24小时，则为xx天xx小时xx分钟xx秒）
     */
    public static String getTimeSpace(String beginTime,String endTime){
        long between = getTimeDifference(beginTime,endTime);
        long date = between / DATE_SECOND;
        long hour = (between - date * DATE_SECOND)/3600;
        long minute = (between -date * DATE_SECOND - hour*3600) / 60;
        long sec = (between -date * DATE_SECOND - hour*3600 - minute*60) ;

        if (date == 0) {
            return hour + "小时" + minute + "分钟"+ sec + "秒";
        }
        else {
            if (date == 1 && hour ==0) {
                return "24小时";
            }else if (date>1 && hour > 0 && minute==0) {
                return date + "天" + hour + "小时";
            }else if (date >1 && hour >0 && minute >0 && sec ==0) {
                return date + "天" + hour + "小时" + minute + "分钟";
            }else{
                return  date + "天" + hour + "小时" + minute + "分钟" + sec + "秒";
            }
        }
    }

    /**
     * 计算时间差
     * @param beginTime 开始时间字符串（Date类型）
     * @param endTime 结束时间字符串（Date类型）
     * @return String
     */
    public static String getTimeDifference(Date beginTime, Date endTime) {
        long between = endTime.getTime() - beginTime.getTime() ;
        if (between <= 0){
            return "已过期";
        }

        between = between / 1000;// 除以1000是为了转换成秒
        long date = between / DATE_SECOND;
        long hour = (between - date * DATE_SECOND)/3600;
        long minute = (between -date * DATE_SECOND - hour*3600) / 60;
        long sec = (between -date * DATE_SECOND - hour*3600 - minute*60) ;
        if (date == 0) {
            return hour + "小时" + minute + "分钟"+ sec + "秒";
        }
        else {
            if (date == 1 && hour ==0) {
                return "24小时";
            }else if (date>1 && hour > 0 && minute==0) {
                return date + "天" + hour + "小时";
            }else if (date >1 && hour >0 && minute >0 && sec ==0) {
                return date + "天" + hour + "小时" + minute + "分钟";
            }else{
                return  date + "天" + hour + "小时" + minute + "分钟" + sec + "秒";
            }
        }
    }

    /**
     * 得到x天x小时
     * @param second 秒
     * @return String
     */
    public static String getDateExplain(long second){
        String time = "";
        long hourCount_ = second / 3600;
        long dayCount_ = hourCount_ / 24;
        long remnantHour = hourCount_ % 24;
        if(dayCount_ != 0) time = dayCount_ + "天";
        if(remnantHour != 0) time += remnantHour + "小时";
        return time;
    }

    /**
     * 获得距离当前时间后几天的日期
     * @param currentDate 当前日期(Date类型)
     * @param addDay 天数
     * @return Date（默认时间格式:如：yyyy-MM-dd HH:mm:ss）
     */
    public static String getFutureDate(Date currentDate,int addDay) {
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, addDay);
        new SimpleDateFormat().format(c.getTime());

        return format(c.getTime(), FORMAT_NORMAL);
    }

    public static String getAbsoluteDate(String dateString,int day) throws ParseException {
        Date d = parse(dateString, FORMAT_NORMAL);
        Calendar calender = Calendar.getInstance();
        calender.setTime(d);
        calender.add(Calendar.DATE, day);
        return format(calender.getTime(),FORMAT_NORMAL);
    }

    /**
     * 得到未来x年的日期
     * @param oldDate 源日期
     * @param addYear 年
     * @return Date
     */
    public static Date getFutrueYear(Date oldDate,int addYear) {
        Calendar c = Calendar.getInstance();
        c.setTime(oldDate);
        c.add(Calendar.YEAR, addYear);

        return c.getTime();
    }

    /**
     * 比较日期大小
     * @param d1 源日期（Date类型）
     * @param d2 目标日期（Date类型）
     * @return String(大于、小于、等于符号)
     */
    public static String compare(Date d1,Date d2){
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);

        if(c1.after(c2)){
            return ">";
        } else if(c1.before(c2)){
            return "<";
        } else {
            return "=";
        }
    }

    /**
     * 比较日期大小
     * @param s1 源日期字符串（String类型）
     * @param s2 目标日期字符串（String类型）
     * @return String(大于、小于、等于符号)
     */
    public static String compare(String s1,String s2){
        Date d1 = parse(s1,FORMAT_DATE_ONLY);
        Date d2 = parse(s2, FORMAT_DATE_ONLY);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        if(c1.after(c2)){
            return ">";
        } else if(c1.before(c2)){
            return "<";
        } else {
            return "=";
        }
    }

    /**
     * 获取月份
     * @param date 日期
     * @param Month 月份
     * @return
     */
    public static Date absoluteMonth(Date date, int Month) {
        if (date == null) {
            return new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, Month);
        return cal.getTime();
    }

    public static Date addDays(Date date, int i, Long parameter) {
        if (date == null) {
            return null;
        } else {
            parameter = StringUtils.isEmpty(parameter) ? DAY_TIMES : parameter;
            date.setTime(date.getTime() + i * parameter);
            return date;
        }
    }

    /**
     * 获取几天后的日期
     * @param date 时间
     * @param day 天数
     * @return
     */
    public static Date addDateByDay(Date date, int day) {
        return addDays(date, day, DAY_TIMES);
    }

    /**
     * 获取几个小时前后的日期
     * @param date 时间
     * @param hour 小时数
     * @return
     */
    public static Date addHourByHour(Date date, int hour) {
        return addDays(date, hour, HOUR_TIMES);
    }

    /**
     * 获取X分钟后的日期
     * @param date 1115869084
     * @param minutes
     * @return
     */
    public static Date addDateByMinutes(Date date, int minutes) {
        return addDays(date, minutes, MIN_TIMES);
    }

    //判断是否为日期类型
    public static boolean validDateFormat(String dateStr,String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            logger.error("The date from is illegal !");
        }
           return false;
    }

}
