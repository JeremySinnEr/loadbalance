package com.zjm;

import java.util.*;

/**
 * @Description
 * @Author ZJM
 * @Date 2019/2/3 23:09
 */
public class ServerIps {

    // IP列表
    public static final List<String> LIST = new ArrayList<>();
    static {
        LIST.add("192.168.0.1");
        LIST.add("192.168.0.2");
        LIST.add("192.168.0.3");
        LIST.add("192.168.0.4");
        LIST.add("192.168.0.5");
        LIST.add("192.168.0.6");
        LIST.add("192.168.0.7");
        LIST.add("192.168.0.8");
        LIST.add("192.168.0.9");
        LIST.add("192.168.0.10");
    }

    // 权重IP列表
    public static final Map<String, Integer> WEIGHT_LIST =  new LinkedHashMap<>();
    static {
        // 权重之和为50
        WEIGHT_LIST.put("192.168.0.1", 1);
        WEIGHT_LIST.put("192.168.0.2", 8);
        WEIGHT_LIST.put("192.168.0.3", 3);
        WEIGHT_LIST.put("192.168.0.4", 6);
        WEIGHT_LIST.put("192.168.0.5", 5);
        WEIGHT_LIST.put("192.168.0.6", 5);
        WEIGHT_LIST.put("192.168.0.7", 4);
        WEIGHT_LIST.put("192.168.0.8", 7);
        WEIGHT_LIST.put("192.168.0.9", 2);
        WEIGHT_LIST.put("192.168.0.10", 9);
    }

    // 权重IP列表（平滑加权轮询测试）
    public static final Map<String, Integer> WEIGHT_LIST_1 =  new LinkedHashMap<>();
    static {
        // 权重之和为50
        WEIGHT_LIST_1.put("A", 5);
        WEIGHT_LIST_1.put("B", 1);
        WEIGHT_LIST_1.put("C", 1);
    }

    // 服务器当前的活跃数
    public static final Map<String, Integer> ACTIVITY_LIST = new LinkedHashMap<>();
    static {
        ACTIVITY_LIST.put("192.168.0.1", 2);
        ACTIVITY_LIST.put("192.168.0.2", 0);
        ACTIVITY_LIST.put("192.168.0.3", 1);
        ACTIVITY_LIST.put("192.168.0.4", 3);
        ACTIVITY_LIST.put("192.168.0.5", 0);
        ACTIVITY_LIST.put("192.168.0.6", 1);
        ACTIVITY_LIST.put("192.168.0.7", 4);
        ACTIVITY_LIST.put("192.168.0.8", 2);
        ACTIVITY_LIST.put("192.168.0.9", 7);
        ACTIVITY_LIST.put("192.168.0.10", 3);
    }
}
