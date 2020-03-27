package com.sungness.core.qrcode;

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 将BitMatrix对象输出到图片文件或流
 * Created by wanghongwei on 6/26/15.
 */
public class QrcodeWriter {

    /**
     * 将BufferedImage写入指定的文件
     * @param image BufferedImage
     * @param format String 文件格式
     * @param fileName String 目标文件名
     * @throws IOException
     */
    public static void writeToFile(
            BufferedImage image, String format, String fileName)
            throws IOException {
        writeToFile(image, format, FileUtils.getFile(fileName));
    }

    /**
     * 将BufferedImage写入指定的文件
     * @param image BufferedImage
     * @param format String 文件格式
     * @param file File 目标文件
     * @throws IOException
     */
    public static void writeToFile(BufferedImage image, String format, File file)
            throws IOException {
        if (!ImageIO.write(image, format, file)) {
            throw new IOException(
                    "Could not write an image of format "
                            + format + " to " + file.getName());
        }
    }

    /**
     * 将BufferedImage写入指定的输出流
     * @param image BufferedImage
     * @param format String 文件格式
     * @param stream OutputStream 输出流
     * @throws IOException
     */
    public static void writeToStream(
            BufferedImage image, String format, OutputStream stream)
            throws IOException {
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }
}
