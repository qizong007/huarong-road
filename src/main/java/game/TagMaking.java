package game;

import entity.Answer;
import entity.AnswerPoster;
import entity.Subject;
import util.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TagMaking {

    /**
     * 初始化 + 模拟
     * @throws Exception
     */
    public static void initAndPlay() throws Exception {

        //初始化
        ImgCompetition.init();

        // 请求图片
        Subject requestJSON = Request.requestForTheImg(PathUtil.GET_URL);

        // 切割请求来的图片
        NineZoneDiv.split(PathUtil.REQUEST_PIC,PathUtil.SRC_PIECES+"/");
        String finalDir = ImgCompetition.pickTheOneOnInit(PathUtil.SRC_PIECES,PathUtil.TARGET_PIECES);

        // 图片识别与对比
        if(finalDir != null){
            int[][] board = compare(PathUtil.SRC_PIECES,finalDir);
            int[][] target = new int[][]{{0,1,2}, {3,4,5},{6,7,8}};
            int whitePos = findTheWhite(PathUtil.SRC_PIECES);
            //System.out.println("whitePos = " + whitePos);

            // 0+1+2+..+8 = 36 36-1=35
            int sum = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    sum += board[i][j];
                }
            }
            board[whitePos/3][whitePos%3] = 35 - sum;
            //System.out.println("挖掉的是原图的第"+board[whitePos/3][whitePos%3]+"张");
            int white = board[whitePos/3][whitePos%3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(board[i][j] < white){
                        board[i][j]++;
                    }
                    else if(board[i][j] == white){
                        board[i][j] = 0;
                    }
                    if(target[i][j] < white){
                        target[i][j]++;
                    }
                    else if(target[i][j] == white){
                        target[i][j] = 0;
                    }
                }
            }
            Game game = new Game();
            int ans = game.slidingPuzzle(board,target);
            if(ans != -1){
                System.out.println(game.op+",共"+ans+"步");
                String uuid = requestJSON.getUuid();
                Answer answer = new Answer(game.op,requestJSON.getSwap());
                AnswerPoster answerPoster = new AnswerPoster(uuid,answer);
                Request.requestForMyScore(answerPoster);
            }else{
                System.err.println("算法GG了！");
            }
        }
    }

    /**
     *
     * @param src "D:/testImg/src"
     * @param target "D:/testImg/target1-36"
     */
    private static int[][] compare(String src,String target) throws Exception {

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
                    if(ReadColor.getImagePixel(src+"/"+srcPiece) == ReadColor.getImagePixel(target+"/"+targetPieces[i])){
                        board[x][y] = i;
                        //System.out.printf("(%d,%d)设为%d\n",x,y,i);
                    }else{
                        board[x][y] = -1;
                        //System.out.printf("(%d,%d)设为-1\n",x,y);
                    }
                }
            }
        }
        return board;
    }

    /**
     * 找到空白图片
     * @param src src文件夹路径 "D:/testImg/src"
     * @return src中的第几张图片
     * @throws IOException
     */
    public static int findTheWhite(String src) throws Exception {
        String whitePath = PathUtil.WHITE_PIC;
        String[] srcPieces = new File(src).list();
        FingerPrint fp1 = new FingerPrint(ImageIO.read(new File(whitePath)));
        FingerPrint fp2 = null;
        float max = 0;
        int pos = 0;
        for (int i = 0; i < 9; i++) {
            fp2 = new FingerPrint(ImageIO.read(new File(src+"/"+srcPieces[i])));
            float res = fp1.compare(fp2);
            if(max<res && ReadColor.getImagePixel(src+"/"+srcPieces[i])==255){
                max = res;
                pos = i;
            }
        }
        return pos;
    }

}
