package cn.zplay.utils;

import main.java.com.sampson.utils.StringUtils;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA
 * ProjectName: utils
 * CreateUser:  lixiaopeng
 * CreateTime : 2016/5/23 11:17
 * Email: lixiaopeng913@sina.com
 * ModifyUser: lixiaopeng
 * Class Description:
 * To change this template use File | Settings | File Template
 */
public class StringUtilsTest extends TestCase{
    //验证数字
    public void testValidNumber(){
        String str = "12.5";
        boolean b = StringUtils.validNumber(str);
        System.out.println(b);
    }

    public void testJoin(){
        String[] s = new String[]{"a","b","c"};
        String str = StringUtils.join(s);
        System.out.println(str);
    }

    @Test
    public void testStringUtils(){
//        testValidNumber();
//        testJoin();
    }
}
