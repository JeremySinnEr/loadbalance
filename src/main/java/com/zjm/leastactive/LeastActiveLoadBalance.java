package com.zjm.leastactive;

import com.zjm.ServerIps;

import java.util.*;

/**
 * @Description 最小活跃数算法
 * @Author ZJM
 * @Date 2019/2/6 0:11
 */
public class LeastActiveLoadBalance {

    public static void main(String[] args) {

    }

    /**
     * @Description 随机获取服务器
     * @Param [ipList]
     * @Return java.lang.String
     * @Auther ZJM
     * @Date 2019/2/4 0:17
     */
    public static String getServer() {
        // 找出当前活跃数最小的服务器
        Optional<Integer> min = ServerIps.ACTIVITY_LIST.values().stream().min(Comparator.naturalOrder());
        if (min.isPresent()) {
            // 最小活跃数的IP
            List<String> minActivityIps = new ArrayList<>();
            ServerIps.ACTIVITY_LIST.forEach((ip, activity) -> {
                if (activity.equals(min.get())) {
                    minActivityIps.add(ip);
                }
            });
            if (minActivityIps.size() > 1) { // 最小活跃数的IP有多个，根据权重来选，权重大的优先
                // 过滤出对应的IP和权重
                Map<String, Integer> weightMap = new LinkedHashMap<>();
                ServerIps.WEIGHT_LIST.forEach((ip, weight) -> {
                    if (minActivityIps.contains(ip)) {
                        weightMap.put(ip, weight);
                    }
                });

                // 权重之和
                int totalWeight = 0;
                // 判断权重是否全部相等，如果全部相等，直接随机一个就好
                boolean sameWeight = true;

                Object[] weights = weightMap.values().toArray();
                for (int i = 0; i < weights.length; i ++) {
                    Integer weight = (Integer) weights[i];
                    totalWeight += weight;
                    if (sameWeight && i > 0 && !weight.equals(weights[i - 1])) {
                        sameWeight = false;
                    }
                }

                Random random = new Random();
                int randomPos = random.nextInt(totalWeight);
                if (!sameWeight) {
                    for (Map.Entry<String, Integer> entry : weightMap.entrySet()) {
                        if (randomPos <= entry.getValue()) {
                            return entry.getKey();
                        }
                        randomPos = randomPos - entry.getValue();
                    }
                }
                return (String) weightMap.keySet().toArray()[new Random().nextInt(weightMap.size())];

            } else { // 只有一个，直接获取
                 return minActivityIps.get(0);
            }
        } else {
            return (String) ServerIps.ACTIVITY_LIST.keySet().toArray()[new Random().nextInt(ServerIps.ACTIVITY_LIST.size())];
        }
    }

}
