package com.sampson.utils;

import main.java.com.sampson.utils.DateUtils;
import main.java.com.sampson.utils.ExcelUtils;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * ProjectName: utils
 * CreateUser:  lixiaopeng
 * CreateTime : 2016/5/23 10:13
 * Email: lixiaopeng913@sina.com
 * ModifyUser: lixiaopeng
 * Class Description:
 * To change this template use File | Settings | File Template
 */
public class ExcelUtilsTest extends TestCase {

    private void exportExcel(){
        ExcelUtils excelUtils = new ExcelUtils();
        String[] titles = new String[]{"日期","邮箱","应用名称","OS"};
        List<Object[]> dataSet = new LinkedList<Object[]>();
        for (int i = 0; i <= 1000; i++) {
            Object[] obj = new Object[titles.length];
            obj[0]= DateUtils.format(new Date(), DateUtils.FORMAT_DATE_ONLY);
            obj[1] = "owen" + i + "@163.com";
            obj[2] = "开心消消乐" + i + "";
            obj[3] = "iOS";

            dataSet.add(obj);
        }
        excelUtils.exportExcel("应用信息",titles,dataSet);
    }

    @Test
    public void testExportExcel(){
        exportExcel();
    }
}
