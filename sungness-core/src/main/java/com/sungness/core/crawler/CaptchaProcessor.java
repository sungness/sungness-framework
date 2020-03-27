package com.sungness.core.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片验证码处理器
 * Created by wanghongwei on 3/22/16.
 */
public class CaptchaProcessor {
    private static final Logger log = LoggerFactory.getLogger(CaptchaProcessor.class);

    private static final String INPUT = "/path/to/image/captchas.jpg";
    private static final String OUTPUT = "/path/to/image/captchas_out.jpg";
    private static final String TESSERACT_BIN = "/usr/local/bin/tesseract";
    private static final String TESSERACT_OUTPUT = "/path/to/image/tesseract_out";

    private static final int WHITE = 0x00FFFFFF;
    private static final int BLACK = 0x00000000;

    public static void main(String[] args) throws Exception {
        BufferedImage image = ImageIO.read(new FileInputStream(INPUT));

        //init, determine the average color intensity of the image
        int average = 0;
        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                int color = image.getRGB(column, row) & 0x000000FF;  //only need the last 8 bits
                average += color;
            }
        }
        average /= image.getWidth() * image.getHeight();

        //first pass, mark all pixels as WHITE or BLACK
        boolean darkRegion = false;
        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                int color = image.getRGB(column, row) & 0x000000FF;  //only need the last 8 bits
                if (color <= average * .70 ) {
                    image.setRGB(column, row, BLACK);
                    darkRegion = true;
                }
                else if (color < .85 * average && darkRegion && row < image.getHeight() - 1
                        && (image.getRGB(column, row + 1) & 0x000000FF) < .85 * average) {
                    image.setRGB(column, row, BLACK);
                }
                else if (color < .85 * average && ! darkRegion && row < image.getHeight() - 1 && column > 0
                        && column < image.getWidth() - 1
                        &&  (((image.getRGB(column, row + 1) & 0x000000FF) < color)
                        || ((image.getRGB(column + 1, row) & 0x000000FF) < color)
                        || ((image.getRGB(column - 1, row) & 0x000000FF) < color))) {
                    image.setRGB(column, row, BLACK);
                    darkRegion = true;
                }
                else {
                    image.setRGB(column, row, WHITE);
                    darkRegion = false;
                }
            }
        }

        //second pass, eliminate horizontal gaps
        int consecutiveWhite = 0;
        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                int color = image.getRGB(column, row) & 0x000000FF;  //only need the last 8 bits
                if (color == 255) {
                    consecutiveWhite++;
                }
                else {
                    if (consecutiveWhite < 3 && column > consecutiveWhite) {
                        for (int col = column - consecutiveWhite; col < column; col++) {
                            image.setRGB(col, row, BLACK);
                        }
                    }
                    consecutiveWhite = 0;
                }
            }
        }
        consecutiveWhite = 0;

        //third pass, eliminate vertical gaps
        for (int column = 0; column < image.getWidth(); column++) {
            for (int row = 0; row < image.getHeight(); row++) {
                int color = image.getRGB(column, row) & 0x000000FF;  //only need the last 8 bits
                if (color == 255) {
                    consecutiveWhite++;
                }
                else {
                    if (consecutiveWhite < 2 && row > consecutiveWhite) {
                        for (int r = row - consecutiveWhite; r < row; r++) {
                            image.setRGB(column, r, BLACK);
                        }
                    }
                    consecutiveWhite = 0;
                }
            }
        }

        //fourth pass, attempt to fill regions
        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                if ((image.getRGB(column, row) & WHITE) == WHITE) {
                    int height = countVerticalWhite(image, column, row);
                    int width = countHorizontalWhite(image, column, row);
                    int area = width * height;
                    if ((area <= 12) || (width == 1) || (height == 1)){
                        image.setRGB(column, row, BLACK);
                    }
                }
            }
        }

        //fifth pass repeats the fourth
        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                if ((image.getRGB(column, row) & WHITE) == WHITE) {
                    int height = countVerticalWhite(image, column, row);
                    int width = countHorizontalWhite(image, column, row);
                    int area = width * height;
                    if ((area <= 12) || (width == 1) || (height == 1)){
                        image.setRGB(column, row, BLACK);
                    }
                }
            }
        }

        //sixth pass, clear any false-positive
        for (int row = 0; row < image.getHeight(); row++) {
            for (int column = 0; column < image.getWidth(); column++) {
                if ((image.getRGB(column, row) & WHITE) != WHITE) {
                    if (countBlackNeighbors(image, column, row) <= 3) {
                        image.setRGB(column, row, WHITE);
                    }
                }
            }
        }

        //now find the characters
        List<CharacterBox> characters = new ArrayList<CharacterBox>();
        int totalCharWidth = 10;
        int maxCharHeight = 0;
        for (int column = 0; column < image.getWidth(); column++) {
            int highestBlack = countVerticalWhite(image, column, 0);
            if (highestBlack < image.getHeight()) {
                totalCharWidth += 5; //5 px spacing in between chars
                CharacterBox box = new CharacterBox();
                box.setX(column);
                while (column < image.getWidth() && countVerticalWhite(image, column, 0) < image.getHeight()) {
                    int currentBlack = countVerticalWhite(image, column, 0);
                    if (currentBlack < highestBlack) {
                        highestBlack = currentBlack;
                    }
                    column++;
                }
                box.setWidth(column - box.getX());
                box.setY(highestBlack - 5);
                box.setHeight(image.getHeight() - highestBlack + 5); //can trim this later
                if (box.getHeight() > maxCharHeight) {
                    maxCharHeight = box.getHeight();
                }
                totalCharWidth += box.getWidth();
                characters.add(box);
            }
        }

        //output a new image with aligned characters
        BufferedImage dst = new BufferedImage (totalCharWidth, maxCharHeight,
                BufferedImage.TYPE_INT_BGR);
        for (int column = 0; column < dst.getWidth(); column++) {
            for (int row = 0; row < dst.getHeight(); row++) {
                dst.setRGB(column, row, WHITE);
            }
        }
        int xPos = 5;
        int yPos = 0;
        for (CharacterBox box : characters) {
            for (int oldY = box.getY(); oldY < box.getY() + box.getHeight(); oldY++) {
                for (int oldX = box.getX(); oldX < box.getX() + box.getWidth(); oldX++) {
                    dst.setRGB(xPos + (oldX - box.getX()), yPos + (oldY - box.getY()), image.getRGB(oldX, oldY));
                }
            }
            xPos += box.getWidth() + 5;
        }

        ImageIO.write(dst, "jpeg", new File(OUTPUT));

        Process tesseractProc = Runtime.getRuntime().exec(TESSERACT_BIN + " " + OUTPUT + " " + TESSERACT_OUTPUT);
        tesseractProc.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(TESSERACT_OUTPUT + ".txt")));
        System.out.println("Decoded CAPTCHA text:  " + reader.readLine());
        reader.close();
    }

    private static int countVerticalWhite(BufferedImage image, int x, int y) {
        int length = (countAboveWhite(image, x, y) + countBelowWhite(image, x, y));
        return length + 1;
    }

    private static int countAboveWhite(BufferedImage image, int x, int y) {
        int aboveWhite = 0;
        y--;
        while (y > 0) {
            if ((image.getRGB(x, y) & WHITE) == WHITE){
                aboveWhite++;
            }
            else {
                break;
            }
            y--;
        }
        return aboveWhite;
    }

    private static int countBelowWhite(BufferedImage image, int x, int y) {
        int belowWhite = 0;
        y++;
        while (y < image.getHeight()) {
            if ((image.getRGB(x, y) & WHITE) == WHITE){
                belowWhite++;
            }
            else {
                break;
            }
            y++;
        }

        return belowWhite;
    }

    private static int countHorizontalWhite(BufferedImage image, int x, int y) {
        int length = (countLeftWhite(image, x, y) + countRightWhite(image, x, y));
        return length + 1;
    }

    private static int countLeftWhite(BufferedImage image, int x, int y) {
        int leftWhite = 0;
        x--;
        while (x > 0) {
            if ((image.getRGB(x, y) & WHITE) == WHITE){
                leftWhite++;
            }
            else {
                break;
            }
            x--;
        }

        return leftWhite;
    }

    private static int countRightWhite(BufferedImage image, int x, int y) {
        int rightWhite = 0;
        x++;
        while (x < image.getWidth()) {
            if ((image.getRGB(x, y) & WHITE) == WHITE){
                rightWhite++;
            }
            else {
                break;
            }
            x++;
        }

        return rightWhite;
    }

    private static int countBlackNeighbors(BufferedImage image, int x, int y) {
        int numBlacks = 0;

        if (pixelColor(image, x - 1, y) != WHITE) {
            numBlacks++;
        }
        if (pixelColor(image, x - 1, y + 1) != WHITE) {
            numBlacks++;
        }
        if (pixelColor(image, x - 1, y - 1) != WHITE) {
            numBlacks++;
        }
        if (pixelColor(image, x, y + 1) != WHITE) {
            numBlacks++;
        }
        if (pixelColor(image, x, y - 1) != WHITE) {
            numBlacks++;
        }
        if (pixelColor(image, x + 1, y) != WHITE) {
            numBlacks++;
        }
        if (pixelColor(image, x + 1, y + 1) != WHITE) {
            numBlacks++;
        }
        if (pixelColor(image, x + 1, y - 1) != WHITE) {
            numBlacks++;
        }

        return numBlacks;
    }

    private static int pixelColor(BufferedImage image, int x, int y) {
        if (x >= image.getWidth() || x < 0 || y < 0 || y >= image.getHeight()) {
            //out of bounds counts as white
            return WHITE;
        }
        return image.getRGB(x, y) & WHITE;
    }

    private static class CharacterBox {
        private int x;
        private int y;
        private int width;
        private int height;

        public CharacterBox() {
            width = 0;
            height = 0;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public String toString() {
            return width + "x" + height + " @ (" + x + ", " + y + ")";
        }
    }
}
