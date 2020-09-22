import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImgCompetition {

    /**
     *
     * @param src "D:/testImg/src"
     * @param target "D:/testImg/target1-36"
     */
    public static float compare1v1(String src,String target) throws IOException {

        String[] srcPieces = new File(src).list();
        String[] targetPieces = new File(target).list();
        FingerPrint fp1 = null;
        FingerPrint fp2 = null;
        float[] ans = new float[9];
        int cnt=0;

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
            ans[cnt++] = max;
        }

        float sum = 0;
        for(int i=0;i<9;i++){
             sum += ans[i];
        }

        return sum/9;

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
            ans[i-1] = compare1v1(src,target+i);
            if(ans[i-1] == 1){
                System.out.println(target+i);
                finalFile = target+i;
                break;
            }
        }
        return finalFile;
    }

}

