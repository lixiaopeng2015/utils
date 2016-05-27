package main.java.com.sampson.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * ProjectName: util
 * CreateUser:  lixiaopeng
 * CreateTime : 2016/5/24 11:23
 * Email: lixiaopeng913@sina.com
 * ModifyUser: lixiaopeng
 * Class Description:
 * To change this template use File | Settings | File Template
 */
public class HttpUtils {

    private HttpServletRequest request;

    public HttpUtils(HttpServletRequest request) {
        this.request = request;
    }

    //获取IP地址
    public String fetchRequestIP(){
        String ip = request.getHeader(Constants.X_FORWARD_FOR);
        if (StringUtils.isEmpty(ip) || !Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(Constants.PROXY_IP);
        }
        if (StringUtils.isEmpty(ip) || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(Constants.WL_PROXY_IP);
        }
        if (StringUtils.isEmpty(ip) || Constants.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    /**
     * GET请求
     *
     * @param url 请求地址
     */
    public static String doGet(String url) {
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod(Constants.HTTP_REQUEST_METHOD_GET);
            conn.setRequestProperty("Charset", Constants.CHATSET_UTF_8);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            in = new BufferedInputStream(conn.getInputStream());
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length=in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            out.flush();
            return new String(out.toByteArray(),Constants.CHATSET_UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeIO(out);
            closeIO(in);
        }
    }

    /**
     * POST请求
     *
     * @param url 请求地址
     * @param params 参数Map
     */
    public static String doPost(String url, Map<String, String> params) throws IOException {
        return doPost(url, genParamStr(params));
    }

    /**
     * POST请求
     *
     * @param url 请求地址
     * @param params 参数String
     */
    private static String doPost(String url, String params) throws IOException {
        OutputStream os = null;
        try {
            HttpURLConnection urlConn = (HttpURLConnection) new URL(url + "?" + params).openConnection();
            urlConn.setRequestMethod(Constants.HTTP_REQUEST_METHOD_POST);
            urlConn.setConnectTimeout(10000);
            urlConn.setReadTimeout(10000);
            urlConn.setDoOutput(true);
            os = urlConn.getOutputStream();
            byte[] b = params.getBytes(Constants.CHATSET_UTF_8);
            os.write(b, 0, b.length);
            os.flush();
            return getContent(urlConn).trim();
        } finally {
            closeIO(os);
        }
    }

    private static String genParamStr(Map<String, String> parameters) {
        StringBuffer params = new StringBuffer();

        if (parameters != null) {
            for (Iterator<String> iter = parameters.keySet().iterator(); iter.hasNext();) {
                String name = iter.next();
                String value = parameters.get(name);
                if(value == null) continue;

                params.append(name).append("=");
                try {
                    params.append(URLEncoder.encode(value, Constants.CHATSET_UTF_8));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    String message = String.format("'%s'='%s'", name, value);
                    throw new RuntimeException(message, e);
                }

                if (iter.hasNext()) params.append("&");
            }
        }

        return params.toString();
    }

    private static String getContent(HttpURLConnection urlConn) throws IOException {
        BufferedReader rd = null;

        try {
            rd = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), Constants.CHATSET_UTF_8));
            String tempLine = rd.readLine();
            StringBuffer tempStr = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                tempStr.append(tempLine);
                tempStr.append(crlf);
                tempLine = rd.readLine();
            }

            return tempStr.toString();
        } finally {
            closeIO(rd);
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

    /**
     * 获取post参数列表值
     * @param request
     * @return
     */
    public static List<String> getPostParameters(HttpServletRequest request){
        Map<String,String> map = request.getParameterMap();
        List<String> list = new ArrayList<String>();
        for (String s : map.keySet()) {
            String value = request.getParameter(s);
            list.add(value);
        }
        return list;
    }
}
