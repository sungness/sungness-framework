package com.sungness.core.poi;

import org.apache.poi.ss.usermodel.CellStyle;

import java.io.Serializable;

/**
 * 表格元数据类，定义表格的列名、样式等
 * Created by wanghongwei on 12/1/15.
 */
public class SheetColumnMeta implements Serializable {
    private static final long serialVersionUID = -7626160375439044891L;
    /** 列名 */
    private String name;
    /** 属性字段 */
    private String property;
    /** 单元格样式 */
    private short alignment;
    /** 是否是数值类型 */
    private boolean isNumeric;

    public SheetColumnMeta(String name, String property, short alignment, boolean isNumeric) {
        this.name = name;
        this.property = property;
        this.alignment = alignment;
        this.isNumeric = isNumeric;
    }

    public SheetColumnMeta(String name, String property, short alignment) {
        this.name = name;
        this.property = property;
        this.alignment = alignment;
    }

    public SheetColumnMeta(String name, String property) {
        this.name = name;
        this.property = property;
        this.alignment = CellStyle.ALIGN_CENTER;
    }

    public SheetColumnMeta(String name, String property, boolean isNumeric) {
        this.name = name;
        this.property = property;
        this.alignment = CellStyle.ALIGN_CENTER;
        this.isNumeric = isNumeric;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public short getAlignment() {
        return alignment;
    }

    public void setAlignment(short alignment) {
        this.alignment = alignment;
    }

    public boolean isNumeric() {
        return isNumeric;
    }

    public void setNumeric(boolean numeric) {
        isNumeric = numeric;
    }
}
