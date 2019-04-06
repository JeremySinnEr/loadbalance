package com.zjm.hash;

import com.zjm.ServerIps;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Description 一致性Hash算法
 * @Author ZJM
 * @Date 2019/2/5 23:14
 */
public class ConsistentHashLoadBalance {

    // 虚拟节点TreeMap
    public static SortedMap<Integer, String> virtualNodes = new TreeMap<>();
    // 每个IP对应的虚拟节点的个数
    private static final int VIRTUAL_NODES = 160;

    static {
        // 对每个真实节点添加虚拟节点，虚拟节点根据哈希算法进行散列
        for (String ip : ServerIps.LIST) {
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                virtualNodes.put(getHash(ip + "VN" + i), ip);
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20 ; i++) {
            System.out.println(getServer("client:" + i));
        }
    }

    /**
     * @Description 一致性Hash算法获取服务器
     * @Param [client]
     * @Return java.lang.String
     * @Auther ZJM
     * @Date 2019/2/5 23:42
     */
    public static String getServer(String client) {
        // 对客户端进行Hash
        Integer hash = getHash(client);
        // 得到大于该Hash值的排好序的Map
        SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);
        // 为空，取第一个
        if (subMap.isEmpty()) {
            return virtualNodes.get(virtualNodes.firstKey());
        }
        return subMap.get(subMap.firstKey());
    }

    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        // 如果算出来的值为负数，则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }

}
