package cn.zplay.utils;

import main.java.com.sampson.utils.DateUtils;
import junit.framework.TestCase;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * ProjectName: utils
 * CreateUser:  lixiaopeng
 * CreateTime : 2016/5/23 10:33
 * Email: lixiaopeng913@sina.com
 * ModifyUser: lixiaopeng
 * Class Description:
 * To change this template use File | Settings | File Template
 */
public class DateUtilsTest extends TestCase{

    public void testParse(){
        Date d = new Date();
        String str = DateUtils.format(d, DateUtils.FORMAT_NORMAL);
        System.out.println(str);
    }

    public void testGetTimeSpace(){
        String beginTime = "2015-05-08 08:30:45";
        String endTime = "2015-05-10 20:30:00";
        String diff = DateUtils.getTimeSpace(beginTime, endTime);
        System.out.println(diff);
    }

    public void testGetFutureDate(){
        Date d = new Date();
        String s = DateUtils.getFutureDate(d, 3);
        System.out.println(s);
    }

    public void testGetAbsoluteDate() throws ParseException {
        String s = DateUtils.getAbsoluteDate("2015-05-12 08:15:45", 3);
        System.out.println(s);
    }

    public void testValidDate(){
        String date = "2003-02-15";
        boolean b = DateUtils.validDateFormat(date,"yyyyMMdd");
        System.out.println(b);
    }

    @Test
    public void testDateUtils(){
//        testParse();
//        testGetTimeSpace();
//        testGetFutureDate();
//        try {
//            testGetAbsoluteDate();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        testValidDate();
    }


}
