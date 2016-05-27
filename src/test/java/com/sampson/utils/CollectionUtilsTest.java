package com.sampson.utils;

import main.java.com.sampson.utils.CollectionUtils;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * ProjectName: utils
 * CreateUser:  lixiaopeng
 * CreateTime : 2016/5/23 10:48
 * Email: lixiaopeng913@sina.com
 * ModifyUser: lixiaopeng
 * Class Description:
 * To change this template use File | Settings | File Template
 */
public class CollectionUtilsTest extends TestCase{
    public void testSortList(){
        List<String> list = new LinkedList<String>();
        list.add("owen");
        list.add("kaka");
        list.add("backham");
        list.add("messi");

        List<String> l = CollectionUtils.sort(list);
        System.out.println(l.toString());

    }

    public void testJoin(){
        Map<String,String> map = new HashMap<>();
        map.put("owen","18");
        map.put("kaka","20");
        map.put("messi","25");

        List<String> list = CollectionUtils.join(map, ":");
        System.out.println(list.toString());
    }

    public void testArrayJoin(){
        String[] str = new String[]{"10","20","30"};
        String s = CollectionUtils.join(",",str);
        System.out.println(CollectionUtils.join(",",str));
    }

    @Test
    public void testCollectionUtils(){
//        testSortList();
//        testJoin();
        testArrayJoin();
    }
}
