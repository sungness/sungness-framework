package com.sungness.core.poi;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 表格工具类
 * Created by wanghongwei on 12/1/15.
 */
public class HSSFUtils {
    public static final String DEFAULT_FONT_NAME = "微软雅黑";
    /**
     * 创建表头样式
     * @param workbook Workbook
     * @return CellStyle 单元格样式对象
     */
    public static CellStyle createHeadStyle(Workbook workbook) {
        Font headerFont = workbook.createFont();
        headerFont.setFontName(DEFAULT_FONT_NAME);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerFont.setColor(HSSFColor.BLACK.index);

        CellStyle headCellStyle = workbook.createCellStyle();
        headCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headCellStyle.setFont(headerFont);
        return headCellStyle;
    }

    /**
     * 创建左对齐样式
     * @param workbook Workbook
     * @return CellStyle 单元格样式对象
     */
    public static CellStyle createLeftStyle(Workbook workbook) {
        return createCellStyle(workbook, CellStyle.ALIGN_LEFT);
    }

    /**
     * 创建居中样式
     * @param workbook Workbook
     * @return CellStyle 单元格样式对象
     */
    public static CellStyle createCenterStyle(Workbook workbook) {
        return createCellStyle(workbook, CellStyle.ALIGN_CENTER);
    }

    /**
     * 创建右对齐样式
     * @param workbook Workbook
     * @return CellStyle 单元格样式对象
     */
    public static CellStyle createRightStyle(Workbook workbook) {
        return createCellStyle(workbook, CellStyle.ALIGN_RIGHT);
    }

    /**
     * 创建单元格样式
     * @param workbook Workbook
     * @return CellStyle 单元格样式对象
     */
    public static CellStyle createCellStyle(
            Workbook workbook, short alignment) {
        Font dataFont = workbook.createFont();
        dataFont.setFontName(DEFAULT_FONT_NAME);
        dataFont.setFontHeightInPoints((short) 12);
        dataFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        dataFont.setColor(HSSFColor.BLACK.index);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(alignment);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle.setFont(dataFont);
        return cellStyle;
    }
}
