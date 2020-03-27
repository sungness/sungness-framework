package com.sungness.core.dao;

import com.sungness.core.enu.DatabaseVendorEnum;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Properties;

/**
 * MyBatis 物理翻页拦截器插件实现类
 * 修改列表查询语句,将RowBounds转换成物理翻页,避免大数据集时全部取出会内存溢出,
 * 必能能提高查询相应速度。
 * Created by wanghongwei on 6/1/16.
 */
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class})})
public class PaginationInterceptorPlugin implements Interceptor {
    private static final Logger log =
            LoggerFactory.getLogger(PaginationInterceptorPlugin.class);
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        MetaObject statementHandlerMeta = MetaObject.forObject(
                statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        RowBounds rowBounds = (RowBounds)statementHandlerMeta.getValue("delegate.rowBounds");
        if(rowBounds !=null
                && rowBounds != RowBounds.DEFAULT
                && hasNoLimit(boundSql.getSql())){
            MappedStatement mappedStatement =
                    (MappedStatement)statementHandlerMeta.getValue("delegate.mappedStatement");
            Configuration configuration =
                    (Configuration)statementHandlerMeta.getValue("delegate.configuration");
            switch (DatabaseVendorEnum.valueOfId(configuration.getDatabaseId())) {
                case MYSQL:
                    //处理mysql翻页
                    appendMySQLLimit(statementHandlerMeta, rowBounds);
                    break;
                default:
                    break;
            }
            log.debug("RowBounds SQL:" + (String)statementHandlerMeta.getValue("delegate.boundSql.sql"));
        }
        return invocation.proceed();
    }

    /**
     * 判断sql中是否已有LIMIT
     * @param sql String 查询sql语句
     * @return boolean如果没有返回true,否则返回false
     */
    private boolean hasNoLimit(String sql) {
        return !sql.toUpperCase().contains(" LIMIT ");
    }

    /**
     * 在sql语句后增加 LIMIT
     * @param metaObject MetaObject
     * @param rowBounds RowBounds
     */
    private void appendMySQLLimit(
            MetaObject metaObject, RowBounds rowBounds) {
        String originalSql = (String)metaObject.getValue("delegate.boundSql.sql");
        String newSql = originalSql + " LIMIT " + rowBounds.getOffset() + "," + rowBounds.getLimit();
        metaObject.setValue("delegate.boundSql.sql", newSql);
        metaObject.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
        metaObject.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
    }

    @Override
    public Object plugin(Object targetObj) {
        if (targetObj instanceof StatementHandler) {
            return Plugin.wrap(targetObj, this);
        } else {
            return targetObj;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
