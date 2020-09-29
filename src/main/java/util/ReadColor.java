package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ReadColor {

    /**
     * 获得第一个像素点的RGB
     * @param image 图片路径
     * @return 返回RGB值
     * @throws Exception
     */
    public static int getImagePixel(String image) throws Exception {
        int rgb = 0;
        File file = new File(image);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //int h = bi.getHeight();
        //int w = bi.getWidth();
        int pixel;
        for (int i = 0; i < 10; i++) {
            pixel = bi.getRGB(i, i);
            rgb += (pixel & 0xff);
        }
        return rgb;
    }
}
