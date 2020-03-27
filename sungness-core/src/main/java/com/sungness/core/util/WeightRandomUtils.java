package com.sungness.core.util;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据权重随机获取值
 * Created by Chwing on 2017/9/15.
 */
public class WeightRandomUtils {

    /**
     * 根据权重随机获取对应值
     * @param jsonData 权重随机对象列表json
     * @return String 值
     */
    public static String nextString(String jsonData){
        List<WeightRandom<String>> weightRandomList =
                GsonUtils.fromJson(jsonData,
                        new TypeToken<List<WeightRandom<String>>>(){}.getType());
        return next(weightRandomList);
    }

    /**
     * 根据权重随机获取对应值
     * @param jsonData 权重随机对象列表json
     * @return Integer 值
     */
    public static Integer nextInt(String jsonData){
        List<WeightRandom<Integer>> weightRandomList =
                GsonUtils.fromJson(jsonData,
                        new TypeToken<List<WeightRandom<Integer>>>(){}.getType());
        return next(weightRandomList);
    }

    /**
     * 根据权重随机获取对应值
     * @param jsonData 权重随机对象列表json
     * @return T 值
     */
    public static <T> T next(String jsonData){
        List<WeightRandom<T>> weightRandomList =
                GsonUtils.fromJson(jsonData,
                        new TypeToken<List<WeightRandom<T>>>(){}.getType());
        return next(weightRandomList);
    }

    /**
     * 根据权重随机对象列表获取随机值
     * @param weightRandomList 权重随机对象列表
     * @return V 值
     */
    public static <V> V next(List<WeightRandom<V>> weightRandomList) {
        int sum = getWeightSum(weightRandomList);    // 权重总和
        // 如果设置的数落在随机数内，则返回，否则减去本次的数
        for (WeightRandom<V> weightRandom : weightRandomList) {
            if (weightRandom.getW() >= Math.floor(Math.random() * sum + 1)) {
                return weightRandom.getV();
            } else {
                sum -= weightRandom.getW();
            }
        }
        return null;
    }


    /**
     * 权重总和
     * @param weightRandomList 权重随机对象列表
     * @param <V> 值类型
     * @return int
     */
    private static <V> int getWeightSum(List<WeightRandom<V>> weightRandomList){
        int sum = 0;
        for (WeightRandom weightRandom : weightRandomList) {
            sum += weightRandom.getW();
        }
        return sum;
    }

    public static void main(String [] args) {
        // A : B : C = 1 : 3 : 6
        String s = "[{\"w\":10,\"v\": 0},{\"w\":30,\"v\": 1},{\"w\":60,\"v\": 2}]";
        Integer [] randomSum = {0, 0, 0};

        for(int i = 0; i < 10000; i ++){
            randomSum[nextInt(s)] ++;
        }


        System.out.println(randomSum[0] + ";" + randomSum[1] + ";" + randomSum[2]);
    }
}
