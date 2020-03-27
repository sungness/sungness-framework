package com.sungness.core.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * gson序列化过滤策略实现类
 * @author wanghongwei
 * @since 11.15.2013
 */
public class SpecificClassExclusionStrategy implements ExclusionStrategy {
    private final Class<?> excludedThisClass;

    public SpecificClassExclusionStrategy(Class<?> excludedThisClass) {
        this.excludedThisClass = excludedThisClass;
    }

    /* (non-Javadoc)
     * @see com.google.gson.ExclusionStrategy#shouldSkipClass(java.lang.Class)
     */
    @Override
    public boolean shouldSkipClass(Class<?> arg0) {
        return excludedThisClass.equals(arg0);
    }

    /* (non-Javadoc)
     * @see com.google.gson.ExclusionStrategy#shouldSkipField(com.google.gson.FieldAttributes)
     */
    @Override
    public boolean shouldSkipField(FieldAttributes arg0) {
        return excludedThisClass.equals(arg0.getDeclaredClass());
    }

}
