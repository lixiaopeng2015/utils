package main.java.com.sampson.utils;

import java.io.File;
import java.util.Date;
import java.util.Random;

/**
 * Created with IntelliJ IDEA
 * ProjectName: util
 * CreateUser:  lixiaopeng
 * CreateTime : 2016/5/17 11:53
 * Email: lixiaopeng913@sina.com
 * ModifyUser: lixiaopeng
 * Class Description:
 * To change this template use File | Settings | File Template
 */
public class Constants {
    //文件操作符
    public static final String FILE_SEPARATOR = File.separator;
    //空格
    public static final String BLANK = "";
    //空数组
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    //随机数
    public static Random random = new Random(new Date().getTime());

    public static final String DEFAULT_ENCODING = "UTF-8";

    public static final String CHATSET_UTF_8 = "UTF-8";

    public static final String HTTP_REQUEST_METHOD_GET = "GET";

    public static final String HTTP_REQUEST_METHOD_POST = "POST";

    public static final String PROXY_IP = "Proxy-Client-IP";

    public static final String X_FORWARD_FOR = "x-forwarded-for";

    public static final String WL_PROXY_IP = "WL-Proxy-Client-IP";

    public static final String UNKNOWN = "unknown";

    public static final String COMMA = ",";
}
