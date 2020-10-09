package util;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 空间换时间
 */
public class ImgCompetition {

    public static List<byte[][]> picBytes = new ArrayList<>();
    private static byte[][] srcByteArray = new byte[9][16];
    private static byte[] whiteByte = new byte[16];

    private static PickThread[] pickThreads = new PickThread[36];
    private static AtomicBoolean hasFound = new AtomicBoolean(false);
    private static String globalTarget="";
    private static String globalFinalFile="";

    static{
        for(int i=0;i<36;i++){
            pickThreads[i] = new PickThread();
            pickThreads[i].id = i;
        }
    }

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
        // 图库
        for(int i=1;i<=36;i++){
            initOnePic(PathUtil.TARGET_PIECES+i);
        }
        // 全白图
        FingerPrint fp = new FingerPrint(ImageIO.read(new File(PathUtil.WHITE_PIC)));
        whiteByte = fp.binaryzationMatrix;
    }

    /**
     * 初始化json图片
     * @param src
     * @throws IOException
     */
    public static void initSrc(String src) throws IOException {
        String[] srcPieces = new File(src).list();
        FingerPrint fp = null;
        int cnt=0;
        for (String srcPiece : srcPieces) {
            fp = new FingerPrint(ImageIO.read(new File(src+"/"+srcPiece)));
            srcByteArray[cnt++] = fp.binaryzationMatrix;
        }
    }

    /**
     * 两组图片的比较（在初始化的情况下，通过byte[]比较）
     * @param index picBytes的索引
     */
    public static boolean compare1v1OnInit(int index) throws IOException {

        byte[][] picBytesArray = picBytes.get(index);

        for (byte[] srcByte : srcByteArray){
            float max = 0;
            for(byte[] pic : picBytesArray){
                float res = FingerPrint.compare(srcByte,pic);
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
     * 图片匹配，查找与第几张相同（在初始化的情况下，通过byte[]比较）
     * @param target "D:/testImg/target"
     */
    public static String pickTheOneOnInit(String target) throws IOException {
        String finalFile="";
        for(int i=1;i<=36;i++){
            if(compare1v1OnInit(i-1)){
                System.out.println("匹配上了，第"+i+"张");
                finalFile = target+i;
                break;
            }
        }
        return finalFile;
    }


    public static String pickTheOneOnThread(String target){
        globalTarget = target;
        for (int i = 0; i < 36; i++) {
            if(pickThreads[i].isAlive()){
                pickThreads[i].interrupt();
            }
            pickThreads[i].start();
        }
        try {
            synchronized (hasFound) {
                hasFound.wait();//主线程等待
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("找到了");
        return globalFinalFile;
    }

    static class PickThread extends Thread{

        // 0-35
        int id;

        @Override
        public void run() {
            //System.out.println("俺来了 "+id);
            try {
                if(compare1v1OnInit(id)){
                    globalFinalFile = globalTarget+(id+1);
                    synchronized (hasFound) {//获取对象锁
                        hasFound.notify();//子线程唤醒
                    }
                    System.out.println("匹配上了，第"+(id+1)+"张");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 找到白色图片
     * @param src
     * @return
     * @throws Exception
     */
    public static int findTheWhite(String src) throws Exception {
        String[] srcPieces = new File(src).list();
        float max = 0;
        int pos = 0;
        for (int i = 0; i < 9; i++) {
            float res = FingerPrint.compare(whiteByte,srcByteArray[i]);
            if(max<res && ReadColor.getImagePixel(src+"/"+srcPieces[i])==ReadColor.getImagePixel(PathUtil.WHITE_PIC)){
                max = res;
                pos = i;
            }
        }
        return pos;
    }

    /**
     * 图片数组化
     * @param src "D:/testImg/src"
     * @param target
     * @return
     * @throws Exception
     */
    public static int[][] imgToNumArray(String src,String target) throws Exception {

        String[] srcPieces = new File(src).list();
        String[] targetPieces = new File(target).list();
        int pos = Integer.parseInt(target.substring(17));
        int[][] board = new int[3][3];

        int j=0;
        for(String srcPiece : srcPieces){
            for(int i=0;i<9;i++){
                float res = FingerPrint.compare(srcByteArray[j],picBytes.get(pos-1)[i]);
                if(res == 1){
                    int x = srcPiece.charAt(0)-'0';
                    int y = srcPiece.charAt(2)-'0';
                    if(ReadColor.getImagePixel(src+"/"+srcPiece) == ReadColor.getImagePixel(target+"/"+targetPieces[i])){
                        board[x][y] = i;
                        break;
                    }else{
                        board[x][y] = -1;
                    }
                }
            }
            j++;
        }
        return board;
    }

}

