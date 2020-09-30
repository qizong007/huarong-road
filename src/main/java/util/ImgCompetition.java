package util;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 空间换时间
 */
public class ImgCompetition {

    public static List<byte[][]> picBytes = new ArrayList<>();;

    /**
     * 初始化：将一张图片转化为均值哈希的byte数组
     * @param target "D:/testImg/targetX"
     * @throws IOException
     */
    private static void initOnePic(String target) throws IOException {
        String[] targetPieces = new File(target).list();
        FingerPrint fp = null;
        byte[][] picByteArray = new byte[9][16];
        int cnt=0;
        for (String targetPiece : targetPieces) {
            fp = new FingerPrint(ImageIO.read(new File(target+"/"+targetPiece)));
            picByteArray[cnt++] = fp.binaryzationMatrix;
        }
        picBytes.add(picByteArray);
    }

    /**
     * 全部初始化
     * @throws IOException
     */
    public static void init() throws IOException {
        for(int i=1;i<=36;i++){
            initOnePic(PathUtil.TARGET_PIECES+i);
        }
    }

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
            fp1 = new FingerPrint(ImageIO.read(new File(src+"/"+srcPiece)));
            for(String targetPiece : targetPieces){
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
     * 两组图片的比较（在初始化的情况下，通过byte[]比较）
     * @param src "D:/testImg/src"
     * @param target "D:/testImg/target1-36"
     * @param index picBytes的索引
     */
    public static boolean compare1v1OnInit(String src,String target,int index) throws IOException {

        String[] srcPieces = new File(src).list();
        FingerPrint fp1 = null;
        byte[][] picBytesArray = picBytes.get(index);

        for (String srcPiece : srcPieces){
            float max = 0;
            fp1 = new FingerPrint(ImageIO.read(new File(src+"/"+srcPiece)));
            for(byte[] pic : picBytesArray){
                float res = FingerPrint.compare(fp1.binaryzationMatrix,pic);
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
     * 图片匹配，查找与第几张相同
     * @param src "D:/testImg/src"
     * @param target "D:/testImg/target"
     */
    public static String pickTheOne(String src,String target) throws IOException {

        float[] ans = new float[36];
        String finalFile="";

        for(int i=1;i<=36;i++){
            if(compare1v1(src,target+i)){
                System.out.println("匹配上了，第"+i+"张");
                finalFile = target+i;
                break;
            }
        }
        return finalFile;
    }

    /**
     * 图片匹配，查找与第几张相同（在初始化的情况下，通过byte[]比较）
     * @param src "D:/testImg/src"
     * @param target "D:/testImg/target"
     */
    public static String pickTheOneOnInit(String src,String target) throws IOException {

        float[] ans = new float[36];
        String finalFile="";

        for(int i=1;i<=36;i++){
            if(compare1v1OnInit(src,target+i,i-1)){
                System.out.println("匹配上了，第"+i+"张");
                finalFile = target+i;
                break;
            }
        }
        return finalFile;
    }



}

