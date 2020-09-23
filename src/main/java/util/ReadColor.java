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
        int[] rgb = new int[3];
        File file = new File(image);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int pixel = bi.getRGB(0, 0);
        rgb[2] = (pixel & 0xff);
        return rgb[2];
    }
}
