package game;

import entity.Answer;
import entity.AnswerPoster;
import entity.Subject;
import util.*;

import java.io.IOException;

public class Play {

    /**
     * 初始化 + 模拟
     * @throws Exception
     */
    public static boolean play(boolean onThread) throws Exception {

        // 请求图片
        Subject requestJSON = Request.requestForTheImg(PathUtil.GET_URL);

        // 切割请求来的图片
        NineZoneDiv.split(PathUtil.REQUEST_PIC,PathUtil.SRC_PIECES+"/");
        ImgCompetition.initSrc(PathUtil.SRC_PIECES);
        String finalDir;
        if(!onThread){
            finalDir = ImgCompetition.pickTheOneOnInit(PathUtil.TARGET_PIECES);
        }else{
            finalDir = ImgCompetition.pickTheOneOnThread(PathUtil.TARGET_PIECES);
        }

        // 图片识别与对比
        if(finalDir != null){
            int[][] board = ImgCompetition.imgToNumArray(PathUtil.SRC_PIECES,finalDir);
            int[][] target = new int[][]{{0,1,2}, {3,4,5},{6,7,8}};

            matrixAdjust(board,target,ImgCompetition.findTheWhite(PathUtil.SRC_PIECES));

            Game game = new Game();
            int ans = game.slidingPuzzle(board,target,requestJSON.getStep(),requestJSON.getSwap());
            boolean f;
            if(ans != -1){
                f = processAnswer(game,ans,requestJSON);
            }else{
                throw new Exception("检查一下game里的矩阵是不是有2个0？？");
            }
            if(!f){ return false; }
            return true;
        }
        return false;
    }

    /**
     * 处理答案
     * @param game
     * @param ans
     * @param requestJSON
     * @return
     * @throws IOException
     */
    private static boolean processAnswer(Game game,int ans,Subject requestJSON) throws IOException {
        System.out.println(game.op+",共"+ans+"步");
        String uuid = requestJSON.getUuid();
        Answer answer = new Answer(game.op,game.swap);
        AnswerPoster answerPoster = new AnswerPoster(uuid,answer);
        return Request.requestForMyScore(answerPoster);
    }

    /**
     * 矩阵调整：空白-1转0，其他小于全白的上调1
     * @param board
     * @param target
     * @param whitePos
     */
    private static void matrixAdjust(int[][] board,int[][] target,int whitePos){
        // 0+1+2+..+8 = 36 36-1=35
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sum += board[i][j];
            }
        }
        board[whitePos/3][whitePos%3] = 35 - sum;
        int white = board[whitePos/3][whitePos%3];
        //System.out.println("挖掉的是原图的第"+white+"张");
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
    }

}
