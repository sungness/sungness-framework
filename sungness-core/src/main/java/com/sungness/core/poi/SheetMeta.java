package com.sungness.core.poi;

import java.io.Serializable;
import java.util.List;

/**
 * 表格元数据类，定义表格的sheet名称、列等
 * Created by wanghongwei on 12/1/15.
 */
public class SheetMeta implements Serializable {
    private static final long serialVersionUID = 3819636601345559118L;
    /** sheet 名称 */
    private String sheetName;
    /** 列元数据列表 */
    private List<SheetColumnMeta> columns;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<SheetColumnMeta> getColumns() {
        return columns;
    }

    public void setColumns(List<SheetColumnMeta> columns) {
        this.columns = columns;
    }
}
