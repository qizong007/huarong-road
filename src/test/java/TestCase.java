import game.Game;
import game.Play;
import game.Rank;
import org.junit.Assert;
import org.junit.Test;
import util.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestCase {

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
        System.out.println(game.isSolvable(board1, target));
        System.out.println(game.isSolvable(board2, target));
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

}
