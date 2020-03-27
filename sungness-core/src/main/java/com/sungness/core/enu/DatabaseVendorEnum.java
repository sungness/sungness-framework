package com.sungness.core.enu;

import org.apache.commons.lang3.StringUtils;

/**
 * 页面操作任务类型枚举类
 * Created by wanghongwei on 3/29/16.
 */
public enum DatabaseVendorEnum {
    MYSQL("mysql", "MySQL"),
    ORACLE("oracle", "Oracle"),
    SQLServer("sqlserver", "SQL Server"),
    DB2("db2", "DB2"),
    UNKNOWN("unknown", "Unknown");

    /** id */
    private String id;
    /** 标识 */
    private String key;

    private DatabaseVendorEnum(String id, String key) {
        this.id = id;
        this.key = key;
    }

    /**
     * 根据 id 值获取对应的枚举值
     * @param id String id值
     * @return DatabaseVendorEnum 枚举值，如果不存在返回 UNKNOWN
     */
    public static DatabaseVendorEnum valueOfId(String id) {
        if (StringUtils.isNotBlank(id)) {
            for (DatabaseVendorEnum taskEnum: values()) {
                if (taskEnum.getValue().equals(id)) {
                    return taskEnum;
                }
            }
        }
        return UNKNOWN;
    }

    /**
     * 根据key值获取对应的枚举值
     * @param key String key值
     * @return DatabaseVendorEnum 枚举值，如果不存在返回 UNKNOWN
     */
    public static DatabaseVendorEnum valueOfKey(String key) {
        if (StringUtils.isNotBlank(key)) {
            for (DatabaseVendorEnum taskEnum: values()) {
                if (taskEnum.getKey().equals(key)) {
                    return taskEnum;
                }
            }
        }
        return UNKNOWN;
    }

    public String getValue() {
        return id;
    }

    public String getKey() {
        return key;
    }
}
