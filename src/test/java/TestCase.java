import game.Game;
import game.TagMaking;
import org.junit.Test;
import util.*;

import javax.imageio.ImageIO;
import java.io.File;

public class TestCase {

    /**
     * 数组游戏模拟
     */
    @Test
    public void testForPuzzle(){
        int[][] board = new int[][]{{7,2,8}, {4,5,3},{6,1,0}};
        Game game = new Game();
        int ans = game.slidingPuzzle(board);
        System.out.println(ans);
        System.out.println(game.op);
    }

    /**
     * 请求JSON图片 + 切割 + 图片识别 + 模拟游戏
     */
    @Test
    public void playWithPic() throws Exception {
        TagMaking.initAndPlay();
    }

    /**
     * 100次：请求JSON图片 + 切割 + 图片识别 + 模拟游戏
     */
    @Test
    public void playWithPic100() throws Exception {
        int cnt=0;
        int round = 10;
        for (int i = 0; i < round; i++) {
            if(TagMaking.initAndPlay()){
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

}
