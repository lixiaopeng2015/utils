package com.sampson.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA
 * ProjectName: utils
 * CreateUser:  lixiaopeng
 * CreateTime : 2016/5/24 11:06
 * Email: lixiaopeng913@sina.com
 * ModifyUser: lixiaopeng
 * Class Description:
 * To change this template use File | Settings | File Template
 */
public class PropertiesUtils {
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    static Properties properties = new Properties();
    private static PropertiesUtils propUtil = new PropertiesUtils(properties);

    private PropertiesUtils(Properties properties) {
        this.properties = properties;
    }

    /**
     * 根据key获取Properties的value值
     * @param key
     * @return
     */
    public static String findPropertiesKey(String key) {

        try {
            Properties prop = getProperties();
            return prop.getProperty(key);
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * 获取Properties文件
     * @param fileName 文件名称（完整路径）
     * @return
     */
    public synchronized static PropertiesUtils getInstance(String fileName){
        try {
            InputStream inputStream = PropertiesUtils.class.getResourceAsStream(fileName);
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            properties.load(bf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("global.properties not found exception...",e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("read properties file exception...",e);
        }
        return propUtil;
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        PropertiesUtils.properties = properties;
    }

}
