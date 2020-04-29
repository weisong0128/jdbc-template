package com.java.jdbc;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * author: ws
 * time: 2020/4/29 23:13
 */
public class Test {
    public static void main(String[] args) {
        String sql = "insert into person_table (name,sex,age) values(?,?,?)";
//        List<List<String>> data = {""}
        List<String> list1 = new ArrayList<String>();
        list1.add("weisong");
        list1.add("男");
        list1.add("16");
        List<String> list2 = new ArrayList<String>();
        list2.add("xbb");
        list2.add("女");
        list2.add("15");
        List<List<String>> data = new ArrayList<List<String>>();
        data.add(list1);
        data.add(list2);
        JdbcTemplate.getInstance().insert(sql, data);

    }
}
