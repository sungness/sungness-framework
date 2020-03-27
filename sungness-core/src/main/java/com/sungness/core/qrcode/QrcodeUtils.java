package com.sungness.core.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码处理工具
 * Created by wanghongwei on 6/26/15.
 */
public class QrcodeUtils {
    private static final Logger log = LoggerFactory.getLogger(QrcodeUtils.class);
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    /**
     * 根据给定的内容生成指定宽度和高度的二维码
     * @param contents String 要生成二维码的内容
     * @param width int 宽度
     * @param height int 高度
     * @return BitMatrix
     * @throws WriterException
     */
    public static BitMatrix encode(String contents, int width, int height)
            throws WriterException {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        return multiFormatWriter.encode(
                contents, BarcodeFormat.QR_CODE, width, height, getEncodeHints());
    }

    /**
     * 将BitMatrix对象转换成BufferedImage对象
     * @param matrix BitMatrix
     * @return BufferedImage
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    /**
     * 将文本内容（链接）转换为二维码BufferedImage对象
     * @param contents String 链接字符串
     * @param width int 图片宽度
     * @param height int 图片高度
     * @param addLogo boolean 是否添加logo
     * @return BufferedImage
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage toBufferedImage(
            String contents, int width, int height, boolean addLogo)
            throws WriterException, IOException {
        BufferedImage image = toBufferedImage(encode(contents, width, height));
        if (addLogo) {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            File logoFile = resourceLoader.getResource(
                    "classpath:images/logo85x85.png").getFile();
            addLogo(image, logoFile, new LogoConfig());
        }
        return image;
    }

    /**
     * 将文本内容（链接）转换为二维码BufferedImage对象，带logo
     * @param contents String 链接字符串
     * @param width int 图片宽度
     * @param height int 图片高度
     * @return BufferedImage
     * @throws IOException
     * @throws WriterException
     */
    public static BufferedImage toBufferedImage(
            String contents, int width, int height)
            throws IOException, WriterException {
        return toBufferedImage(contents, width, height, true);
    }

    /**
     * 将文本内容（链接）转换为二维码byte[]对象，带logo
     * @param contents String 链接字符串
     * @param width int 图片宽度
     * @param height int 图片高度
     * @param format String 格式 gif、jpg...
     * @return byte[]
     * @throws IOException
     * @throws WriterException
     */
    public static byte[] toQrcodeBytes(
            String contents, int width, int height, String format)
            throws IOException, WriterException {
        BufferedImage image = toBufferedImage(contents, width, height);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        QrcodeWriter.writeToStream(
                image, format, outputStream);
        return outputStream.toByteArray();
    }

    /**
     * 向图片中心位置增加logo小图片
     * @param image BufferedImage 图片对象
     * @param logoPic File logo图片文件对象
     * @param logoConfig LogoConfig logo配置对象
     * @throws IOException
     */
    public static void addLogo(
            BufferedImage image, File logoPic, LogoConfig logoConfig)
            throws IOException {
        Graphics2D graphics2D = image.createGraphics();
        BufferedImage logo = ImageIO.read(logoPic);
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        // 计算图片放置位置
        int x = (image.getWidth() - logoWidth) / 2;
        int y = (image.getHeight() - logoHeight) / 2;

        //开始绘制图片
        graphics2D.drawImage(logo, x, y, logoWidth, logoHeight, null);
        graphics2D.drawRoundRect(x, y, logoWidth, logoHeight, 5, 5);
        graphics2D.setStroke(new BasicStroke(logoConfig.getBorder()));
        graphics2D.setColor(logoConfig.getBorderColor());
        graphics2D.drawRect(x, y, logoWidth, logoHeight);
        graphics2D.dispose();
    }

    /**
     * 获取二维码参数设置Map
     * @return Map<EncodeHintType, Object> 参数设置map对象
     */
    public static Map<EncodeHintType, Object> getEncodeHints() {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);
        return hints;
    }

    /**
     * 从指定的文件中解析二维码
     * @param imageFile File 二维码文件
     * @return Result 解析结果
     * @throws IOException
     * @throws NotFoundException
     */
    public static Result decode(File imageFile) throws IOException, NotFoundException {
        return decode(ImageIO.read(imageFile));
    }

    /**
     * 从指定的输入流中解析二维码
     * @param inputStream InputStream 二维码输入流
     * @return Result 解析结果
     * @throws IOException
     * @throws NotFoundException
     */
    public static Result decode(InputStream inputStream) throws IOException, NotFoundException {
        return decode(ImageIO.read(inputStream));
    }

    /**
     * 从指定的url地址中解析二维码
     * @param url String 二维码url地址
     * @return Result 解析结果
     * @throws IOException
     * @throws NotFoundException
     */
    public static Result decode(String url) throws IOException, NotFoundException {
        return decode(ImageIO.read(new URL(url)));
    }

    /**
     * 二维码解析，从图片对象中解析二维码
     * @param image BufferedImage 图片对象
     * @return Result 解析结果
     * @throws NotFoundException
     */
    public static Result decode(BufferedImage image) throws NotFoundException {
        MultiFormatReader formatReader = new MultiFormatReader();
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        return formatReader.decode(binaryBitmap, hints);
    }

    public static void main(String[] args) throws Exception {
        String content190 = "https://targetURL";
        String content24500 = "https://targetURL2";
        String logo190 = "/path/to/app_logo_85x85_qrcode.png";
        String logo24500 = "/path/to/app_logo_85x85_qrcode.png";
        int width = 325;
        int height = 325;

        BufferedImage image190 = toBufferedImage(encode(content190, width, height));
        addLogo(image190, FileUtils.getFile(logo190), new LogoConfig());
        QrcodeWriter.writeToFile(image190, "gif", "qrcode190.gif");

        BufferedImage image24500 = toBufferedImage(encode(content24500, width, height));
        addLogo(image24500, FileUtils.getFile(logo24500), new LogoConfig());
        QrcodeWriter.writeToFile(image24500, "gif", "qrcode24500.gif");

//        Result res = decode(FileUtils.getFile("qrcode.gif"));
//        log.debug(res.getText());
    }
}
