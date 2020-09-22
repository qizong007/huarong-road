import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TagMaking {

    public static void initAndPlay() throws IOException {
        Request.requestForTheImg("http://47.102.118.1:8089/api/problem?stuid=111800827");
        NineZoneDiv.split("D:/testImg/test.jpg","D:/testImg/src/");
        String finalDir = ImgCompetition.pickTheOne("D:/testImg/src","D:/testImg/target");
        if(finalDir != null){
            int[][] board = compare("D:/testImg/src",finalDir);
            int ans = PlayWithPuzzle.slidingPuzzle(board);
            System.out.println(ans);
            System.out.println(PlayWithPuzzle.op);
        }
    }

    /**
     *
     * @param src "D:/testImg/src"
     * @param target "D:/testImg/target1-36"
     */
    private static int[][] compare(String src,String target) throws IOException {

        String[] srcPieces = new File(src).list();
        String[] targetPieces = new File(target).list();
        FingerPrint fp1 = null;
        FingerPrint fp2 = null;
        int[][] board = new int[3][3];

        for(String srcPiece : srcPieces){
            for(int i=0;i<9;i++){
                fp1 = new FingerPrint(ImageIO.read(new File(src+"/"+srcPiece)));
                fp2 = new FingerPrint(ImageIO.read(new File(target+"/"+targetPieces[i])));
                float res = fp1.compare(fp2);
                if(res == 1){
                    int x = srcPiece.charAt(0)-'0';
                    int y = srcPiece.charAt(2)-'0';
                    board[x][y] = i;
                    System.out.printf("(%d,%d)设为%d\n",x,y,i);
                }
            }
        }
        return board;
    }

}
