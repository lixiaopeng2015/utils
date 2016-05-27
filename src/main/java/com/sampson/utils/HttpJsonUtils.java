package main.java.com.sampson.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA
 * ProjectName: utils
 * CreateUser:  lixiaopeng
 * CreateTime : 2016/5/24 11:46
 * Email: lixiaopeng913@sina.com
 * ModifyUser: lixiaopeng
 * Class Description:
 * To change this template use File | Settings | File Template
 */
public class HttpJsonUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpJsonUtils.class);

    //发送get请求
    public static String sendHttpGet(String url){
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            in = new BufferedInputStream(conn.getInputStream());
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length=in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            out.flush();
            return new String(out.toByteArray(),"UTF-8");
        } catch (Exception e) {
            logger.error("请求url异常 \t"+HttpJsonUtils.class + "\t"+e.getMessage());
            throw new RuntimeException(e);
        } finally {
            closeIO(out);
            closeIO(in);
        }
    }

    private static void closeIO(Closeable io) {
        if(io == null) return;
        try {
            io.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
