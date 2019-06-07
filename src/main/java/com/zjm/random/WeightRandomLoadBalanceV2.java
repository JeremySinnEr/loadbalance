package com.zjm.random;

import com.zjm.ServerIps;

import java.util.Map;
import java.util.Random;

/**
 * @Description 权重随机算法（二）
 * 实现思路：将权重值平铺在一维坐标值上，根据权重总和生成随机数，看落在哪个权重的区间
 * @Author ZJM
 * @Date 2019/2/3 23:16
 */
public class WeightRandomLoadBalanceV2 {

    public static void main(String[] args) {
        // 权重之和
        int totalWeight = 0;
        // 判断权重是否全部相等，如果全部相等，直接随机一个就好
        boolean sameWeight = true;

        Object[] weights = ServerIps.WEIGHT_LIST.values().toArray();
        for (int i = 0; i < weights.length; i ++) {
            Integer weight = (Integer) weights[i];
            totalWeight += weight;
            if (sameWeight && i > 0 && !weight.equals(weights[i - 1])) {
                sameWeight = false;
            }
        }

        for (int i = 0; i < 100 ; i++) {
            System.out.println(getServer(totalWeight, sameWeight));
        }
    }

    /**
     * @Description 随机获取服务器
     * @Param [ipList]
     * @Return java.lang.String
     * @Auther ZJM
     * @Date 2019/2/4 0:17
     */
    public static String getServer(int totalWeight, boolean sameWeight) {
        Random random = new Random();
        int randomPos = random.nextInt(totalWeight);
        if (!sameWeight) {
            for (Map.Entry<String, Integer> entry : ServerIps.WEIGHT_LIST.entrySet()) {
                if (randomPos <= entry.getValue()) {
                    return entry.getKey();
                }
                randomPos = randomPos - entry.getValue();
            }
        }
        return (String) ServerIps.WEIGHT_LIST.keySet().toArray()[new Random().nextInt(ServerIps.WEIGHT_LIST.size())];
    }

}
