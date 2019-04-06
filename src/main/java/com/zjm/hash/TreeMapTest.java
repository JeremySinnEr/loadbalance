package com.zjm.hash;

import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Description TreeMap测试
 * @Author ZJM
 * @Date 2019/2/5 23:21
 */
public class TreeMapTest {

    public static void main(String[] args) {
        SortedMap<String, Integer> sortedMap = new TreeMap<>();
        sortedMap.put("1", 1);
        sortedMap.put("2", 2);
        sortedMap.put("3", 3);
        sortedMap.put("4", 4);
        sortedMap.put("5", 5);
        sortedMap.put("6", 6);
        sortedMap.put("7", 7);
        // 取子树
        SortedMap subMap = sortedMap.tailMap("4");
        System.out.println(subMap.firstKey()); //4

        SortedMap subMap1 = sortedMap.tailMap("8");
        System.out.println(subMap1.isEmpty());
        String ss = (String) subMap1.firstKey();
        System.out.println(subMap1.firstKey());

    }

}
