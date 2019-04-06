package com.zjm.random;

import com.zjm.ServerIps;

import java.util.Random;

/**
 * @Description 简单随机算法
 * @Author ZJM
 * @Date 2019/2/3 23:16
 */
public class RandomLoadBalance {

    public static void main(String[] args) {
        for (int i = 0; i < 10 ; i++) {
            System.out.println(getServer());
        }
    }

    /**
     * @Description 随机获取服务器
     * @Param []
     * @Return java.lang.String
     * @Auther ZJM
     * @Date 2019/2/3 23:17
     */
    public static String getServer() {
        Random random = new Random();
        int randomPos = random.nextInt(ServerIps.LIST.size());
        return ServerIps.LIST.get(randomPos);
    }

}
