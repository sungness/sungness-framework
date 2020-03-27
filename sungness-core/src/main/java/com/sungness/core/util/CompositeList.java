package com.sungness.core.util;

import org.apache.commons.collections4.collection.CompositeCollection;

import java.util.List;

/**
 * List<E>组合工具类，将List列表组合成一个List
 * Created by wanghongwei on 09/05/2017.
 */
public class CompositeList<E> extends CompositeCollection<E> {
    public CompositeList(List<List<E>> compositeLists) {
        if (compositeLists != null) {
            for (List<E> list: compositeLists) {
                addComposited(list);
            }
        }
    }
}
