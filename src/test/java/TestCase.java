import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TestCase {

    /**
     * JSON请求测试
     */
    @Test
    public void testForGET(){
        Request.requestForTheImg(PathUtil.REQUEST_URL);
    }

    /**
     * 数组游戏模拟
     */
    @Test
    public void testForPuzzle(){
        int[][] board = new int[][]{{1,2,3}, {6,8,0},{5,4,7}};
        int ans = PlayWithPuzzle.slidingPuzzle(board,new int[][]{{1,2,3}, {4,5,6},{7,8,0}});
        System.out.println(ans);
        System.out.println(PlayWithPuzzle.op);
    }

    /**
     * 切割全部文件
     */
    @Test
    public void testForSplitAll() {
        NineZoneDiv.spiltAll(PathUtil.ORG_PICS,PathUtil.TARGET_PIECES);
    }

    @Test
    public void testForPickTheOne(){
        try {
            ImgCompetition.pickTheOne(PathUtil.SRC_PIECES,PathUtil.TARGET_PIECES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求JSON图片 + 切割 + 图片识别 + 模拟游戏（无swap）
     */
    @Test
    public void playWithPic() throws Exception {
        TagMaking.initAndPlay();
    }

    /**
     * 测试文件夹内文件名
     */
    @Test
    public void testForDirFileName(){
        String[] srcPieces = new File(PathUtil.SRC_PIECES).list();
        for(String str : srcPieces){
            System.out.println(str);
        }
    }

    /**
     * 寻找白色图片
     */
    @Test
    public void testForFindTheWhite() throws Exception {
        try {
            System.out.println(TagMaking.findTheWhite(PathUtil.SRC_PIECES));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 比较原算法纯黑和纯白
     * @throws Exception
     */
    @Test
    public void testForBlackAndWhite() throws Exception {
        FingerPrint fp1 = new FingerPrint(ImageIO.read(new File("D:/testImg/white.jpg")));
        FingerPrint fp2 = new FingerPrint(ImageIO.read(new File("D:/testImg/src/0_0.jpg")));
        System.out.println(fp1.compare(fp2));
        System.out.println(ReadColor.getImagePixel("D:/testImg/white.jpg"));
    }

}
