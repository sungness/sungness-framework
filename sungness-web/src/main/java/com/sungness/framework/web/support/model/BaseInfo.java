package com.sungness.framework.web.support.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * 模块信息基类
 * Created by wanghongwei on 3/14/16.
 */
public class BaseInfo implements Serializable, Comparable<BaseInfo> {
    private static final long serialVersionUID = 2769119901465240085L;
    /** 条目id，数据库或程序自动生成 */
    protected Long id;
    /** 上级id */
    protected Long parentId;
    /** 条目编码，可用于本地化时使用 */
    protected String code;
    /** 条目名称，如果系统支持本地化，则以code字段为准 */
    protected String value;
    /** 同级显示顺序 */
    protected Integer orderNumber;
    /** 包名 */
    protected String pkgName;
    /** 访问路径 */
    protected String path;
    /** 是否启用，true－启用，false－停用，默认为启用，如果停用，则下级递归停用 */
    protected boolean enable;
    /** 是否舍弃，true－舍弃，false－未舍弃，如果舍弃，可在管理后台删除 */
    protected boolean discard;
    /** 图标样式标识 */
    protected String icon;

    /** 允许通过身份验证的用户访问 默认不允许 */
    protected boolean allowAccessAuthenticated;

    public boolean equals(BaseInfo o) {
        return o != null && Objects.equals(this.id, o.id)
                && Objects.equals(this.parentId, o.parentId)
                && Objects.equals(this.code, o.code)
                && Objects.equals(this.value, o.value)
                && Objects.equals(this.orderNumber, orderNumber)
                && Objects.equals(this.pkgName, o.pkgName)
                && Objects.equals(this.path, o.path)
                && Objects.equals(this.enable, o.enable)
                && Objects.equals(this.discard, o.discard)
                && Objects.equals(this.icon, o.icon)
                && Objects.equals(this.allowAccessAuthenticated, o.allowAccessAuthenticated);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isDiscard() {
        return discard;
    }

    public void setDiscard(boolean discard) {
        this.discard = discard;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public boolean isAllowAccessAuthenticated() {
        return allowAccessAuthenticated;
    }

    public void setAllowAccessAuthenticated(boolean allowAccessAuthenticated) {
        this.allowAccessAuthenticated = allowAccessAuthenticated;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p/>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p/>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p/>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p/>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p/>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(BaseInfo o) {
        return this.orderNumber.compareTo(o.getOrderNumber());
    }
}
