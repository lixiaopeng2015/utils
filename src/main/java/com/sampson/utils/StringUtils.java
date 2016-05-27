package main.java.com.sampson.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * ProjectName: utils
 * CreateUser:  lixiaopeng
 * Email: lixiaopeng913@sina.com
 * CreateTime:  2015/11/30 9:59
 * ModifyUser:  lixiaopeng
 * ModifyTime:  2015/11/30 9:59
 * Class Description:
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils implements Serializable{

    private static final long serialVersionUID = -7275266202386580296L;

    private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

    private static final Pattern INT_PATTERN = Pattern.compile("^\\d+$");

    public static final Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*[,]+\\s*");

    public static String trim(String str) {
        return str == null?null:str.trim();
    }


    //The String object whether is null
    public static boolean isBlank(String str)
    {
        if( str == null || str.length() == 0 )
            return true;
        return false;
    }

    /**
     * 判断是否为null或空字符串
     * @param str
     * @return
     */
    public static boolean isEmpty(String str)
    {
        if( str == null || str.length() == 0 )
            return true;
        return false;
    }

    public static boolean isEmpty(Object str) {
        return str == null || Constants.BLANK.equals(str);
    }

    /**
     * 判断字符串不为null或空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str)
    {
        return str != null && str.length() > 0;
    }

    /**判断对象是否为空
     * @param obj
     * @return
     */
    public static String isNull(Object obj) {
        String str = Constants.BLANK;
        if (obj != null) {
            if (obj instanceof String) {
                str = (String) obj;
            } else {
                str = obj.toString();
            }
        }
        return str;
    }

    /**
     * 判断字符串是否为空
     * @param str 源字符串
     * @param defaultVal 默认值
     * @return
     */
    public static String isNullAndDefaultValue(String str, String defaultVal) {
        return str != null ? str.trim() : defaultVal;
    }

    /**
     * 判断Object是否为null
     * @param obj object对象
     * @param defaultValue 默认值
     * @return
     */
    public static String isNull(Object obj,String defaultValue){
        return obj != null ? obj.toString().trim() : defaultValue;
    }

    /**
     * 比较两个字符串是否相等
     * @param s1
     * @param s2
     * @return
     */
    public static boolean isEquals(String s1, String s2) {
        if (s1 == null && s2 == null)
            return true;
        if (s1 == null || s2 == null)
            return false;
        return s1.equals(s2);
    }

    /**
     * 判断字符串是否为Integer类型
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        if (str == null || str.length() == 0)
            return false;
        return INT_PATTERN.matcher(str).matches();
    }

    /**
     * 判断String是否为数字类型
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     *@param str
     *@return
     *@Description:验证str是否为数字形式 包括小数
     *@Author:wangshupeng#zplay.cn
     *@Since:2015年3月11日  下午6:34:47
     *@Version:1.0
     */
    public static Boolean validNumber(String str){
        Pattern pattern = Pattern.compile("^[0-9]+(\\.[0-9]+)?$");
        Matcher matchAmount = pattern.matcher(str);
        return matchAmount.matches();
    }

    /**
     * 将String转换为Integer类型
     * @param str
     * @return
     */
    public static int parseInteger(String str) {
        if (! isInteger(str))
            return 0;
        return Integer.parseInt(str);
    }

    /**
     * 判断指定字符串中是否包含某个字符串
     * @param values 源字符串
     * @param value 需要检查的字符串
     * @return
     */
    public static boolean isContains(String values, String value) {
        if (values == null || values.length() == 0) {
            return false;
        }
        return isContains(COMMA_SPLIT_PATTERN.split(values), value);
    }

    /**
     * 判断字符串数组中是否包含某个字符串
     * @param values 字符串数组
     * @param value 需要检查的字符串
     * @return contains
     */
    public static boolean isContains(String[] values, String value) {
        if (value != null && value.length() > 0 && values != null && values.length > 0) {
            for (String v : values) {
                if (value.equals(v)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 分割字符串
     * @param str 源字符串
     * @param ch 需要分隔的字符
     * @return
     */
    public static String[] split(String str, char ch)
    {
        List<String> list = null;
        char c;
        int ix = 0,len=str.length();
        for(int i=0;i<len;i++)
        {
            c = str.charAt(i);
            if( c == ch )
            {
                if( list == null )
                    list = new ArrayList<String>();
                list.add(str.substring(ix, i));
                ix = i + 1;
            }
        }
        if( ix > 0 )
            list.add(str.substring(ix));
        return list == null ? Constants.EMPTY_STRING_ARRAY : (String[])list.toArray(Constants.EMPTY_STRING_ARRAY);
    }

    /**
     * 对字符串进行URLEncoder.encode编码
     *
     * @param str     要指定的字符串
     * @param charset 指定要编码url的字符集
     * @return        返回url指定编码集，如果为空则进行编码
     */
    public static String UrlEncode(String str, String charset) {
        if (str == null)
            return "";
        String s = str;
        try {
            s = URLEncoder.encode(str, charset);
        } catch (Exception e) {
            logger.error(" when encode for string encountered an exception! "+ "\t" +e.getMessage());
        }
        return s;
    }

    /**
     * 对字符串进行UrlDecode解码
     *
     * @param str 要指定的字符串
     * @param charset 指定要解码url的字符集
     * @return 返回解码字符集
     */
    public static String UrlDecode(String str, String charset) {
        if (str == null)
            return Constants.BLANK;
        String s = str;
        try {
            s = URLDecoder.decode(str, charset);
        } catch (Exception e) {

        }
        return s;
    }

    /**
     * 所有字符大写
     *
     * @param  str 传入的字符串
     * @return     返回所有字符串大写
     */
    public static String upperCase(String str) {
        if (str == null) {
            return null;
        } else {
            return str.toUpperCase();
        }
    }

    /**
     * 所有字符小写
     *
     * @param  str 传入的字符串
     * @return     返回所有字符串小写
     */
    public static String lowerCase(String str) {
        if (str == null) {
            return null;
        } else {
            return str.toLowerCase();
        }
    }

    /**
     * 产生随机的字符串（大小写字母和数字的组合
     * @param size 指定长度
     * @return
     */
    public static String getRandomString(final int size) {// 随机字符串?
        char[] c = {'1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','p','q','r','s','t','u','v','w','x',
                    'y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z'};
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size; i++) {
            sb.append(c[Math.abs(Constants.random.nextInt()) % c.length]);
        }
        return sb.toString();
    }

    /**
     * 去掉字符串里面的html代码。
     *
     * @param content 内容
     * @return 去掉后的内容
     */
    public static String stripHtml(String content) {
        // 换行换为空串
        content = content.replaceAll("\r\n", Constants.BLANK);
        // 去掉<>之间的东西
        content = content.replaceAll("\\<.*?>", Constants.BLANK);
        return content;
    }

    /**
     * 判断传入的字符串是否含有汉字。
     *
     * @param str 内容
     * @return 返回判断结果，true  含有 ;false 不含有
     */
    public static boolean IsHaveChinese(String str) {
        boolean f = false;
        f = !(str.length() == str.getBytes().length);
        return f;

    }

    /**
     * String array to String object
     * @param array
     * @return
     */
    public static String join(String[] array)
    {
        if( array.length == 0 ) return Constants.BLANK;
        StringBuilder sb = new StringBuilder();
        for( String s : array )
            sb.append(s);
        return sb.toString();
    }

}
