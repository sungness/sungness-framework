package com.sungness.core.util;

import org.bson.types.ObjectId;

/**
 * 唯一id生成器，调用mongodb接口生成ObjectId
 * Created by wanghongwei on 27/12/2016.
 */
public class UuidGenerator {

    /** 机器标识符 */
    private static final int machineIdentifier = ObjectId.getGeneratedMachineIdentifier();

    /** 用户id计数器 */
    private static int userInc = 0;

    /** 订单id计数器 */
    private static int orderInc = 0;

    /** 商品（计费点）id计数器 */
    private static int goodsInc = 0;

    /**
     * 获取下一个用户id
     * @return String 用户id字符串，24个字符
     */
    public static String nextUid() {
        return ObjectId.createFromLegacyFormat(
                DateUtil.getTimestamp().intValue(),
                machineIdentifier, ++userInc).toString();
    }

    /**
     * 获取下一个订单id
     * @return String 订单id字符串，24个字符
     */
    public static String nextOid() {
        return ObjectId.createFromLegacyFormat(
                DateUtil.getTimestamp().intValue(),
                machineIdentifier, ++orderInc).toString();
    }

    /**
     * 获取下一个商品id
     * @return String 商品id字符串，24个字符
     */
    public static String nextGid() {
        return ObjectId.createFromLegacyFormat(
                DateUtil.getTimestamp().intValue(),
                machineIdentifier, ++goodsInc).toString();
    }

    public static void main(String[] args) {
        System.out.println(nextUid());
        System.out.println(nextOid());
//        for (int i = 0; i < 100; i++) {
//            nextUid();
//            nextOid();
//        }
        System.out.println(nextGid());
        System.out.println(nextGid());
    }
}
