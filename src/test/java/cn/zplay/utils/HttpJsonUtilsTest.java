package cn.zplay.utils;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA
 * ProjectName: utils
 * CreateUser:  lixiaopeng
 * CreateTime : 2016/5/24 12:04
 * Email: lixiaopeng913@sina.com
 * ModifyUser: lixiaopeng
 * Class Description:
 * To change this template use File | Settings | File Template
 */
public class HttpJsonUtilsTest extends TestCase{

    @Test
    public void testSendGet(){
        String url = "http://ppgz.zplay.cn/api.php?auth_key=sgrkrP4PK2o0kdCTwsdu35YYzmO9FM1U&action=huobi&code=USD&month=2016-06";
        String json = HttpJsonUtils.sendHttpGet(url);
        System.out.println(json);
    }
}
