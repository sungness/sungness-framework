package com.sungness.core.enu;

/**
 * 是、否枚举类
 * 1-是，0-否
 * Created by wanghongwei on 3/15/16.
 */
public enum BoolEnum {
    NO(0, "否"),
    YES(1, "是"),
    UNKNOWN(-1, "未知");

    private Integer code;
    private String description;

    private BoolEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据code值获取对应的枚举值
     * @param code String code值
     * @return BoolEnum 枚举值，如果不存在返回 UNKNOWN
     */
    public static BoolEnum valueOf(Integer code) {
        if (code != null) {
            for (BoolEnum itemEnum: values()) {
                if (itemEnum.getValue().intValue() == code.intValue()) {
                    return itemEnum;
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
