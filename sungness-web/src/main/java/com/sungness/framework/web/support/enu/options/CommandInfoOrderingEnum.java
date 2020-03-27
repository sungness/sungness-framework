package com.sungness.framework.web.support.enu.options;

import org.apache.commons.lang3.StringUtils;

/**
* API命令信息 列表排序枚举类
*
* Created by wanghongwei on 10/19/17.
*/
public enum CommandInfoOrderingEnum {
    ID_ASC(1, "a.id ASC", "commandInfo.id.asc", "条目id 升序"),
    ID_DESC(2, "a.id DESC", "commandInfo.id.desc", "条目id 降序"),
    PARENT_ID_ASC(3, "a.parent_id ASC", "commandInfo.parent_id.asc", "上级id 升序"),
    PARENT_ID_DESC(4, "a.parent_id DESC", "commandInfo.parent_id.desc", "上级id 降序"),
    CODE_ASC(5, "a.code ASC", "commandInfo.code.asc", "条目编码 升序"),
    CODE_DESC(6, "a.code DESC", "commandInfo.code.desc", "条目编码 降序"),
    VALUE_ASC(7, "a.value ASC", "commandInfo.value.asc", "条目名称 升序"),
    VALUE_DESC(8, "a.value DESC", "commandInfo.value.desc", "条目名称 降序"),
    ORDER_NUMBER_ASC(9, "a.order_number ASC", "commandInfo.order_number.asc", "同级显示顺序 升序"),
    ORDER_NUMBER_DESC(10, "a.order_number DESC", "commandInfo.order_number.desc", "同级显示顺序 降序"),
    PKG_NAME_ASC(11, "a.pkg_name ASC", "commandInfo.pkg_name.asc", "包名和方法名 升序"),
    PKG_NAME_DESC(12, "a.pkg_name DESC", "commandInfo.pkg_name.desc", "包名和方法名 降序"),
    PATH_ASC(13, "a.path ASC", "commandInfo.path.asc", "访问路径 升序"),
    PATH_DESC(14, "a.path DESC", "commandInfo.path.desc", "访问路径 降序"),
    ENABLE_ASC(15, "a.enable ASC", "commandInfo.enable.asc", "是否启用 升序"),
    ENABLE_DESC(16, "a.enable DESC", "commandInfo.enable.desc", "是否启用 降序"),
    INLET_ASC(17, "a.inlet ASC", "commandInfo.inlet.asc", "是否是模块入口 升序"),
    INLET_DESC(18, "a.inlet DESC", "commandInfo.inlet.desc", "是否是模块入口 降序"),
    DISCARD_ASC(19, "a.discard ASC", "commandInfo.discard.asc", "是否舍弃 升序"),
    DISCARD_DESC(20, "a.discard DESC", "commandInfo.discard.desc", "是否舍弃 降序");

    /** id */
    private Integer id;
    /** option选项值 */
    private String value;
    /** 本地化标识 */
    private String textCode;
    /** 描述 */
    private String description;

    CommandInfoOrderingEnum(
            Integer id, String value, String textCode, String description) {
        this.id = id;
        this.value = value;
        this.textCode = textCode;
        this.description = description;
    }

    /**
    * 根据id值获取对应的枚举值
    * @param id String id值
    * @return CommandInfoOrderingEnum 枚举值，如果不存在返回 null
    */
    public static CommandInfoOrderingEnum valueOf(Integer id) {
        if (id != null) {
            for (CommandInfoOrderingEnum orderingEnum: values()) {
                if (orderingEnum.getId().intValue() == id.intValue()) {
                    return orderingEnum;
                }
            }
        }
        return null;
    }

    /**
    * 根据value获取对应的枚举值
    * @param value String 值字符串
    * @return CommandInfoOrderingEnum 排序枚举值，如果不存在，返回 null
    */
    public static CommandInfoOrderingEnum enumOfValue(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (CommandInfoOrderingEnum orderingEnum: values()) {
                if (orderingEnum.getValue().equals(value)) {
                    return orderingEnum;
                }
            }
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getTextCode() {
        return textCode;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据id 直接获取描述信息
     * @param id Integer 数字编号
     * @return String 对应枚举的描述，如果不是有效枚举返回null
     */
    public static String getDescription(Integer id) {
        CommandInfoOrderingEnum orderingEnum = valueOf(id);
        return orderingEnum != null ? orderingEnum.getDescription() : null;
    }

    /**
     * 根据value 直接获取描述信息
     * @param value String 值字符串
     * @return String 对应枚举的描述，如果不是有效枚举返回null
     */
    public static String getDescriptionOfValue(String value) {
        CommandInfoOrderingEnum orderingEnum = enumOfValue(value);
        return orderingEnum != null ? orderingEnum.getDescription() : null;
    }

    /**
     * 返回默认枚举值
     * @return CommandInfoOrderingEnum 默认枚举值
     */
    public static CommandInfoOrderingEnum getDefaultEnum() {
        return PARENT_ID_ASC;
    }
}