package com.sungness.framework.web.support.enu;

/**
 * 条目类型枚举类
 * Created by wanghongwei on 3/15/16.
 */
public enum ItemTypeEnum {
    MENU(0, "菜单"),
    MODULE(1, "模块"),
    COMMAND(2, "命令"),
    UNKNOWN(-1, "未知");

    private Integer code;
    private String description;

    private ItemTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据code值获取对应的枚举值
     * @param code String code值
     * @return ItemTypeEnum 枚举值，如果不存在返回 UNKNOWN
     */
    public static ItemTypeEnum valueOf(Integer code) {
        if (code != null) {
            for (ItemTypeEnum itemTypeEnum: values()) {
                if (itemTypeEnum.getValue().intValue() == code.intValue()) {
                    return itemTypeEnum;
                }
            }
        }
        return UNKNOWN;
    }

    public Integer getValue() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据code 直接获取描述信息
     * @param code Integer 数字编号
     * @return String 对应枚举的描述，如果不是有效枚举返回“未知”
     */
    public static String getDescription(Integer code){
        return valueOf(code).getDescription();
    }
}
