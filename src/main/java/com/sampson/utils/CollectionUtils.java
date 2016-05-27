package com.sampson.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created with IntelliJ IDEA
 * ProjectName: util
 * CreateUser:  lixiaopeng
 * CreateTime : 2016/5/16 16:28
 * Email: lixiaopeng913@sina.com
 * ModifyUser: lixiaopeng
 * Class Description:
 * To change this template use File | Settings | File Template
 */
public class CollectionUtils {

    private static Logger logger = LoggerFactory.getLogger(CollectionUtils.class);

    /**
     * 判断collection是否为空
     * @param collection 集合类
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断collection不为空
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection collection){
        return collection.size()>0 || collection !=null;
    }

    /**
     * 对数据排序
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> sort(List<T> list){
        if (!isEmpty(list)) {
            Collections.sort((List)list);
        }
        return list;
    }

    /**
     * 根据泛型和分隔符组合字符串
     * @param list 集合
     * @param separator 分隔符，如,
     * @return
     */
    public static String join(List<?> list,String separator){
        StringBuilder builder = new StringBuilder();
        if (isEmpty(list)) {
            logger.error("Illegal argument exception, list must not be null !");
        }
        for (Object o : list) {
            if (builder.length()>0) {
                builder.append(separator);
            }
            builder.append(o);
        }

        return builder.toString();
    }

    /**
     * map分隔字符串
     * @param map map对象
     * @param separator 分隔符
     * @return
     */
    public static List<String> join(Map<String,String> map,String separator){
        if (map == null) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value == null || value.length()==0) {
                list.add(key);
            }else{
                list.add(key+separator+value);
            }
        }
        return list;
    }

    /**
     * 通过Object数组拼接字符串
     * @param separator 分隔符
     * @param obj 数组对象
     * @return
     */
    public static String join(String separator, Object... obj){
        if (obj.length == 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < obj.length; i++) {
            if (builder.length()>0) {
                builder.append(separator);
            }
            builder.append(obj[i]);
        }
        return builder.toString();
    }

    //将字符串转化为List数组
    public static <T> List<T> toList(String str,String separator){
        List<T> list = new ArrayList<T>();
        String[] strArray = str.split(separator);
        for (int i = 0; i < strArray.length; i++) {
            T obj = (T) strArray[i];
            list.add(obj);
        }
        return list;
    }

    /**
     * 基本类型判断
     * @param object
     * @param <T>
     * @return
     */
    private static  <T> T validDataType(Object object){
        if (object instanceof Integer) {
            Integer.valueOf(object.toString());
        }
        if (object instanceof String) {
            String.valueOf(object);
        }
        if (object instanceof Double) {
            Double.valueOf(object.toString());
        }
        if (object instanceof BigDecimal) {
            BigDecimal.valueOf(Double.valueOf(object.toString()));
        }
        if (object instanceof Character) {
            Character.valueOf(((Character) object).charValue());
        }
        if (object instanceof Byte) {
            Byte.valueOf(object.toString());
        }
        return (T) object;
    }



}
