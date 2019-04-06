package com.zjm.round;

import com.zjm.ServerIps;

/**
 * @Description 轮询算法
 * @Author ZJM
 * @Date 2019/2/4 0:38
 */
public class RoundBalance {

    // 当前轮询的位置
    public static Integer pos = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 20 ; i++) {
            System.out.println(getServer());
        }
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
        synchronized (RoundBalance.class) {
            if (pos >= ServerIps.LIST.size()) {
                pos = 0;
            }
            ip = ServerIps.LIST.get(pos);
            pos ++ ;
        }
        return ip;
    }

}
