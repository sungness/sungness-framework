package com.sungness.core.service;

/**
 * Service数据层泛型，指定主键为String型
 * @author wanghongwei
 * @since v2.0 2017-01-09
 *
 * @param <E> 要操作的数据表对应的实体类
 */
public abstract class StringPKBaseService<E> extends GenericService<E, String>{
}