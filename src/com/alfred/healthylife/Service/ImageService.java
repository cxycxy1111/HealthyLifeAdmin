package com.alfred.healthylife.Service;

import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageService extends Service {

    public ImageService() {

    }

    /**
     * base64字符串转为InputStream
     *
     * @param base64string
     * @return
     */
    private static InputStream BaseToInputStream(String base64string) {
        ByteArrayInputStream stream = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes1 = decoder.decodeBuffer(base64string);
            stream = new ByteArrayInputStream(bytes1);
        } catch (Exception e) {
        }
        return stream;
    }

    /**
     * 获取BufferedImage流对象
     *
     * @param base64string
     * @return
     */
    public static BufferedImage getBufferedImage(String base64string) {
        BufferedImage image = null;
        try {
            InputStream stream = BaseToInputStream(base64string);
            image = ImageIO.read(stream);
            //System.out.println(">>>"+image.getWidth()+","+image.getHeight()+"<<<");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 自制图片格式转换
     *
     * @param base64string
     */
    private void convertImageFormat(String base64string) {
        ByteArrayInputStream stream = null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64string);
            stream = new ByteArrayInputStream(bytes1);
            BufferedImage bim = ImageIO.read(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片格式转换
     *
     * @param inputFormat  输入格式
     * @param outputFormat 输出格式
     * @param src
     */
    private void Conversion(String inputFormat, String outputFormat, String src) {
        try {
            File input = new File(src + inputFormat);
            BufferedImage bim = ImageIO.read(input);
            File output = new File(src + outputFormat);
            ImageIO.write(bim, outputFormat, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
