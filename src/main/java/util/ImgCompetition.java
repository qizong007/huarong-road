package util;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImgCompetition {

    /**
     * 两组图片的比较
     * @param src "D:/testImg/src"
     * @param target "D:/testImg/target1-36"
     */
    public static boolean compare1v1(String src,String target) throws IOException {

        String[] srcPieces = new File(src).list();
        String[] targetPieces = new File(target).list();
        FingerPrint fp1 = null;
        FingerPrint fp2 = null;

        for (String srcPiece : srcPieces){
            float max = 0;
            for(String targetPiece : targetPieces){
                fp1 = new FingerPrint(ImageIO.read(new File(src+"/"+srcPiece)));
                fp2 = new FingerPrint(ImageIO.read(new File(target+"/"+targetPiece)));
                float res = fp1.compare(fp2);
                if(max < res){
                    max = res;
                }
            }
            if(max < 1){
                return false;
            }
        }

        return true;

    }

    /**
     *
     * @param src "D:/testImg/src"
     * @param target "D:/testImg/target"
     */
    public static String pickTheOne(String src,String target) throws IOException {

        float[] ans = new float[36];
        String finalFile="";

        for(int i=1;i<=36;i++){
            if(compare1v1(src,target+i)){
                System.out.println(target+i);
                finalFile = target+i;
                break;
            }
        }
        return finalFile;
    }

}

