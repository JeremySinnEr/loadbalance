package com.zjm.round;

import com.zjm.ServerIps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 平滑加权轮询算法
 * @Author ZJM
 * @Date 2019/2/4 0:38
 */
public class WeightRoundBalanceV2 {

    // 轮询算法-当前轮询的位置
    public static Integer pos = 0;

//    A:5
//    B:1
//    C:1
//
//    currentWeight   //当前权重，动态的,初始0
//    weight          //固定的权重 5 1 1
//    sum(weight)     //7
//
//    currentWeight += weight    max(currentWeight)   ip    max(currentWeight) -= sum(weight)
//            [5, 1, 1]                  5                    A     [-2, 1, 1]
//            [3, 2, 2]                  3                    A     [-4, 2, 2]
//            [1, 3, 3]                  3                    B     [1, -4, 3]
//            [6, -3, 4]                 6                    A     [-1, -3, 4]
//            [4, -2, 5]                 5                    C     [4, -2, -2]
//            [9, -1, -1]                9                    A     [2, -1, -1]
//            [7, 0, 0]                  7                    A     [0, 0, 0]
//            [5, 1, 1]                  5                    A     [-2, 1, 1]
//            ......

    // 权重列表
    private static List<Weight> weightList = new ArrayList<>();

    public static void main(String[] args) {
        // 权重之和
        int totalWeight = 0;
        // 判断权重是否全部相等，如果全部相等，采用轮询算法就好
        boolean sameWeight = true;

        Object[] weights = ServerIps.WEIGHT_LIST_1.values().toArray();
        for (int i = 0; i < weights.length; i ++) {
            Integer weight = (Integer) weights[i];
            totalWeight += weight;
            if (sameWeight && i > 0 && !weight.equals(weights[i - 1])) {
                sameWeight = false;
            }
        }

        for (int i = 0; i < 20 ; i++) {
            System.out.println(getServer(totalWeight, sameWeight));
        }
    }

    /**
     * @Description 平滑加权轮询获取服务器
     * @Param [totalWeight, sameWeight]
     * @Return java.lang.String
     * @Auther ZJM
     * @Date 2019/2/5 22:57
     */
    public static String getServer(Integer totalWeight, boolean sameWeight) {
        // 初始化weightList
        if (weightList.isEmpty()) {
            for (Map.Entry<String, Integer> entry : ServerIps.WEIGHT_LIST_1.entrySet()) {
                weightList.add(new Weight(entry.getKey(), entry.getValue(), 0));
            }
        }

        // currentWeight += weight
        // 取出max(currentWeight)
        Weight maxCurrentWeight = null;
        for (Weight weight : weightList) {
            weight.setCurrentWeight(weight.getCurrentWeight() + weight.getWeight());

            if (maxCurrentWeight == null || weight.getCurrentWeight() > maxCurrentWeight.getCurrentWeight()) {
                maxCurrentWeight = weight;
            }
        }

        //max(currentWeight) -= sum(weight)
        maxCurrentWeight.setCurrentWeight(maxCurrentWeight.getCurrentWeight() - totalWeight);

        return maxCurrentWeight.getIp();
    }

    /**
     * @Description 轮询获取服务器
     * @Param []
     * @Return java.lang.String
     * @Auther ZJM
     * @Date 2019/2/5 22:58
     */
    public static String getServer() {
        String ip = null;
        synchronized (WeightRoundBalance.class) {
            if (pos >= ServerIps.WEIGHT_LIST.size()) {
                pos = 0;
            }
            ip = ServerIps.LIST.get(pos);
            pos ++ ;
        }
        return ip;
    }

}
