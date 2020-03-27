package com.sungness.framework.web.support.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 模块命令注解，可标模块命令名称、英文名称
 * Created by wanghongwei on 9/2/15.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Command {
    /** 命令编码，可用于本地化时使用 */
    String code() default "";

    /** 命令名称，如果系统支持本地化，则以code字段为准 */
    String value() default "";

    /** 命令显示顺序 */
    int order() default 0;

    /** 是否启用，true－启用，false－停用，默认为启用 */
    boolean enable() default true;

    /** 是否是模块入口 */
    boolean isInlet() default false;

    /** 是否在顶部导航的模块下级菜单显示 */
    boolean showInMenu() default false;

    /** 命令别名，当showInMenu为 true 时，在导航中显示此别名（一般设置为新增xxx）*/
    String alias() default "";

    /** 图标标识,用于css控制显示图标 */
    String icon() default "";

    /** 是否记录日志， true-记录，false-不记录，默认记录 */
    boolean trace() default true;

    /** 允许通过身份验证的用户访问 默认不允许 */
    boolean allowAccessAuthenticated() default false;
}
