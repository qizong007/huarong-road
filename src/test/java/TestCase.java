import com.alibaba.fastjson.JSON;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestCase {

    @Test
    public void testForGET(){
        Request.requestForTheImg("http://47.102.118.1:8089/api/problem?stuid=111800827");
    }

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
        NineZoneDiv.spiltAll("D:/testImg/org","D:/testImg/target");
    }

    @Test
    public void testForPickTheOne(){
        try {
            ImgCompetition.pickTheOne("D:/testImg/src","D:/testImg/target");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * JSON图片版
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
        String[] srcPieces = new File("D:/testImg/src").list();
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
            System.out.println(TagMaking.findTheWhite("D:/testImg/src"));
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
