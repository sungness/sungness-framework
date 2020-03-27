package com.sungness.core.qrcode;

import java.awt.*;

/**
 * 二维码中心logo图片的配置
 * Created by wanghongwei on 6/26/15.
 */
public class LogoConfig {
    // logo默认边框颜色
    public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
    // logo默认边框宽度
    public static final int DEFAULT_BORDER = 2;
    // logo大小默认为照片的1/5
    public static final int DEFAULT_LOGOPART = 5;

    private final Color borderColor;
    private final int logoPart;
    private final int border;

    /**
     * Creates a default config with on color and off color,
     * generating normal black-on-white barcodes.
     */
    public LogoConfig() {
        this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART, DEFAULT_BORDER);
    }

    public LogoConfig(Color borderColor, int logoPart, int border) {
        this.borderColor = borderColor;
        this.logoPart = logoPart;
        this.border = border;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public int getBorder() {
        return border;
    }

    public int getLogoPart() {
        return logoPart;
    }
}
