package com.sungness.core.util;

import java.util.Properties;

/**
 * 基础配置文件工具类
 *
 * Created by Chwing on 2018/9/11.
 */
public class BasicConfig {

    protected Properties properties;

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
