package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 九宫格切割
 */
public class NineZoneDiv {

    /**
     * 切割单张图片
     * @param scrImageFile 原图片路径
     * @param targetDir 目标路径
     */
    public static void split(String scrImageFile,String targetDir){

        File targetDec = new File(targetDir);
        targetDec.mkdir();
        // BufferedImage创建一个实际的图像缓冲区,可以直接操作像素
        BufferedImage img = null;
        BufferedImage scrImage = null;
        try {
            scrImage = ImageIO.read(new File(scrImageFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int scrHeight = scrImage.getHeight();
        int divHeight = scrHeight / 3;
        int scrWidth = scrImage.getWidth();
        int divWidth = scrWidth / 3;

        String fileName = null;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                // getSbuImage返回由指定矩形区域定义的子图像
                img = scrImage.getSubimage(j * divWidth, i * divHeight, divWidth, divHeight);
                fileName = targetDir + i + "_" + j + ".jpg";   //文件名
                File file = new File(fileName);
                try {
                    ImageIO.write(img, "jpg", file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //System.out.println("九宫格切割完成");
    }

    /**
     * 切割文件夹下全部图片
     * @param scrImageDec "D:/testImg/org"
     * @param targetDir "D:/testImg/target"
     */
    public static void spiltAll(String scrImageDec,String targetDir){
        File f = new File(scrImageDec);
        String[] files = f.list();
        int cnt = 1;
        for(String file : files){
            split(scrImageDec+"/"+file,targetDir+cnt+"/");
            cnt++;
        }
    }

}
