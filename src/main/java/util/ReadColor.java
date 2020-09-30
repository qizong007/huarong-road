package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ReadColor {

    /**
     * 获得(0,0)到(9,9)像素点的RGB
     * @param image 图片路径
     * @return 返回RGB值的和
     * @throws Exception
     */
    public static int getImagePixel(String image){
        int rgb = 0;
        File file = new File(image);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int pixel;
        for (int i = 0; i < 10; i++) {
            pixel = bi.getRGB(i, i);
            rgb += (pixel & 0xff);
        }
        return rgb;
    }

}
