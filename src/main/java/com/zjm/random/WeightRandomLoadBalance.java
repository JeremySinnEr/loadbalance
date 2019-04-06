package com.zjm.random;

import com.zjm.ServerIps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Description 权重随机算法（一）
 * 简单的思路实现，将每个服务器按照它的权重进行复制，得到服务器列表，然后进行随机
 * @Author ZJM
 * @Date 2019/2/3 23:16
 */
public class WeightRandomLoadBalance {

    public static void main(String[] args) {
        // 按权重生成的服务器IP列表
        List<String> ipList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : ServerIps.WEIGHT_LIST.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                ipList.add(entry.getKey());
            }
        }

        for (int i = 0; i < 100 ; i++) {
            System.out.println(getServer(ipList));
        }
    }

    /**
     * @Description 从根据权重生成的服务器IP列表随机获取服务器
     * @Param []
     * @Return java.lang.String
     * @Auther ZJM
     * @Date 2019/2/3 23:17
     */
    public static String getServer(List<String> ipList) {
        Random random = new Random();
        int randomPos = random.nextInt(ipList.size());

        return ipList.get(randomPos);
    }

}
