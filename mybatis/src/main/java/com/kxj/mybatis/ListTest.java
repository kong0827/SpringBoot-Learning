package com.kxj.mybatis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiangjin.kong
 * @date 2020/6/16 16:22
 * @desc
 */
public class ListTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

//        for (String str : list) {
//            if ("a".equals(str)) {
//                list.remove("a");
//            }
//        }

//        Iterator<String> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            if ("a".equals(iterator.next())) {
//                iterator.remove();
//            }
//        }
        List<String> collect = list.stream().filter(str -> !"a".equals(str)).collect(Collectors.toList());
//        list.removeIf(str -> "a".equals(str));
        System.out.println(list);
    }
}
