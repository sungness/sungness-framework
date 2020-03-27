package com.sungness.framework.web.support.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 模块注解，可标模块编码、名称、序号
 * Created by wanghongwei on 9/2/15.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Module {
    /** 模块编码，可用于本地化时使用 */
    String code() default "";

    /** 模块名称，如果系统支持本地化，则以code字段为准 */
    String value() default "";

    /** 图标标识,用于css控制显示图标 */
    String icon() default "";

    /** 模块显示顺序 */
    int order() default 0;

    /** 是否启用，true－启用，false－停用，默认为启用，如果停用，则下级递归停用 */
    boolean enable() default true;

    /** 是否在顶部导航的菜单、侧边栏菜单显示，隐藏的模块只在某主模块内部使用，不显示在自动生成的导航中 */
    boolean hideInMenu() default false;

    /** 允许通过身份验证的用户访问 默认不允许 */
    boolean allowAccessAuthenticated() default false;
}
