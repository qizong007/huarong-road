import game.*;
import org.junit.Assert;
import org.junit.Test;
import util.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestCase {

    @Test
    public void battle() throws Exception {
        // 初始化，把图片转成内存中的byte[]
        ImgCompetition.init();
        List<Challenge.Question> questions = Challenge.getList();
        for(Challenge.Question question : questions){
            Play.battle(PathUtil.TEAM_ID,PathUtil.TEAM_TOKEN,question.getUuid(),false);
        }
        Rank.getTeamDetail(PathUtil.TEAM_ID);
    }


    @Test
    public void battleForSingle() throws Exception {
        // 初始化，把图片转成内存中的byte[]
        ImgCompetition.init();
        Play.battle(PathUtil.TEAM_ID,PathUtil.TEAM_TOKEN,"bed7baa0-d7b8-4aaa-b488-eb3ca7be46dc",true);
        //Rank.getTeamDetail(PathUtil.TEAM_ID);
    }

    @Test
    public void battleSpecially() throws Exception {
        String op = "sawddsawwassddwwas";
        int[] swap = {3,6};
        Play.specialBattle("08c03088-e35f-4f82-9295-c4505d8b20bd",op,swap);
        Rank.getTeamDetail(PathUtil.TEAM_ID);
    }


    /**
     * 数组游戏模拟
     */
    @Test
    public void testForPuzzle(){
        int[][] board = new int[][]{{3,1,8}, {2,4,6},{7,5,0}};
        Game game = new Game();
        int ans = game.slidingPuzzle(board);
        System.out.println(ans);
        System.out.println(game.op);
    }

    /**
     * 有无解测试
     */
    @Test
    public void testForIsSolvable(){
        int[][] board1 = new int[][]{{1,3,8}, {2,4,6},{7,5,0}};
        int[][] board2 = new int[][]{{3,1,8}, {2,4,6},{7,5,0}};
        int[][] target = new int[][]{{1,2,3}, {4,5,6},{7,8,0}};
        Game game = new Game();
        Assert.assertTrue(game.isSolvable(board1, target));
        Assert.assertFalse(game.isSolvable(board2, target));
    }

    /**
     * 请求JSON图片 + 切割 + 图片识别 + 模拟游戏
     */
    @Test
    public void playWithPic() throws Exception {
        // 初始化，把图片转成内存中的byte[]
        ImgCompetition.init();
        Assert.assertTrue(Play.play(false));
    }

    /**
     * 100次：请求JSON图片 + 切割 + 图片识别 + 模拟游戏
     */
    @Test
    public void playWithPicBunch() throws Exception {
        int cnt=0;
        int round = 30;
        ImgCompetition.init();
        for (int i = 0; i < round; i++) {
            if(Play.play(false)){
                cnt++;
            }
        }
        Assert.assertTrue(cnt == round);
        System.out.println("成功率:"+(float)cnt/round);
    }

    /**
     * 比较原算法纯黑和纯白
     * @throws Exception
     */
    @Test
    public void testForBlackAndWhite() throws Exception {
        FingerPrint fp1 = new FingerPrint(ImageIO.read(new File(PathUtil.WHITE_PIC)));
        FingerPrint fp2 = new FingerPrint(ImageIO.read(new File("D:/testImg/src/0_0.jpg")));
        System.out.println(fp1.compare(fp2));
        System.out.println(ReadColor.getImagePixel(PathUtil.WHITE_PIC));
    }

    /**
     * 获取rank
     * @throws IOException
     */
    @Test
    public void testForRank() throws IOException {
        Rank.getRank();
    }

    /**
     * 查看队伍详情
     * @throws IOException
     */
    @Test
    public void testForTeamDetail() throws IOException {
        System.out.println("我的：");
        Rank.getTeamDetail(PathUtil.TEAM_ID);
    }

    /**
     * 看题目
     * @throws IOException
     */
    @Test
    public void testForChallengeList() throws IOException, InterruptedException {
        List<Challenge.Question> questions = Challenge.getAllList();
        for(Challenge.Question question : questions){
            String uuid = question.getUuid();
            List<Challenge.Record> records = Challenge.getRecord(uuid);
            for(Challenge.Record record : records){
                if(record.getRank() < 11){
                    System.out.println(record);
                }
                if(record.getOwner().equals(PathUtil.TEAM_ID)){
                    System.out.println(record);
                }
            }
            System.out.println(question.getUuid());
            Thread.sleep(1000);
        }
    }

    /**
     *
     * @throws IOException
     */
    @Test
    public void testForSingleChallenge() throws IOException {
        Challenge.getRecord("faffa1cf-b298-452b-b469-d48f8ddf57a0");
    }

    @Test
    public void testForQuestionCreation() throws IOException {
        Challenge.createQuestion(PathUtil.TEAM_ID,"o",6,PathUtil.TEAM_TOKEN);
    }

    /**
     * 测试随机数组生成
     * @throws IOException
     */
    @Test
    public void testForRandBoard() throws IOException {
        int[][] board = BoardCreator.generateBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]+" ");
            }
        }
    }

}
