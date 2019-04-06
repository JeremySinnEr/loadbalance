package com.zjm.round;

import com.zjm.ServerIps;

import java.util.Map;

/**
 * @Description 加权轮询算法
 * @Author ZJM
 * @Date 2019/2/4 0:38
 */
public class WeightRoundBalance {

    // 轮询算法-当前轮询的位置
    public static Integer pos = 0;

    public static void main(String[] args) {
        // 权重之和
        int totalWeight = 0;
        // 判断权重是否全部相等，如果全部相等，采用轮询算法就好
        boolean sameWeight = true;

        Object[] weights = ServerIps.WEIGHT_LIST.values().toArray();
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
     * @Description 加权轮询获取服务器
     * @Param [ipList]
     * @Return java.lang.String
     * @Auther ZJM
     * @Date 2019/2/4 0:17
     */
    public static String getServer(int totalWeight, boolean sameWeight) {
        if (!sameWeight) {
            int sequenceNum = Sequence.getAndIncrement();
            int offset = sequenceNum % totalWeight;
            offset = offset == 0 ? totalWeight : offset;

            for (Map.Entry<String, Integer> entry : ServerIps.WEIGHT_LIST.entrySet()) {
                if (offset <= entry.getValue()) {
                    return entry.getKey();
                }
                offset = offset - entry.getValue();
            }
        }
        return getServer();
    }


    /**
     * @Description 轮询获取服务器
     * @Param []
     * @Return java.lang.String
     * @Auther ZJM
     * @Date 2019/2/4 0:47
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

    // 模拟请求序列
    static class Sequence {

        public static Integer num = 0;

        public static Integer getAndIncrement() {
            return ++num;
        }

    }

}
