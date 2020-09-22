import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestCase {

    @Test
    public void testForGET(){
        Request.requestForTheImg("http://47.102.118.1:8089/api/problem?stuid=111800827");
    }

    @Test
    public void testForPuzzle(){
        int[][] board = new int[][]{{8,6,5}, {7,3,2},{0,4,1}};
        int ans = PlayWithPuzzle.slidingPuzzle(board);
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
    public void playWithPic() throws IOException {
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

}
