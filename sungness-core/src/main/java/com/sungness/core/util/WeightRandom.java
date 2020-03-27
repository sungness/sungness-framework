package com.sungness.core.util;

/**
 * 权重随机
 * Created by Chwing on 2017/9/15.
 */
public class WeightRandom<V> {
    // 权重
    private Integer w;
    //值
    private V v;

    public WeightRandom(){

    }

    public WeightRandom(Integer w, V v) {
        this.w = w;
        this.v = v;
    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public V getV() {
        return v;
    }

    public void setV(V v) {
        this.v = v;
    }
}
