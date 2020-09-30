package util;

import com.alibaba.fastjson.JSON;
import entity.AnswerPoster;
import entity.MyScore;
import entity.Subject;

import java.io.IOException;

public class Request {

    /**
     * 获得json请求来的图片
     * @param url util.PathUtil.REQUEST_URL
     * @return 返回题目对象
     */
    public static Subject requestForTheImg(String url){
        try {
            Subject subject = JSON.parseObject(HttpJSONUtil.readContentFromGet(url),Subject.class);
            if(FileTypeTranslate.generateImage(subject.getImg(),PathUtil.REQUEST_PIC)){
                return subject;
            }
            System.err.println("JSON请求图片失败...");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过POST请求提交答案
     * @param answerPoster 答案提交
     * @return 返回成绩字符串
     * @throws IOException
     */
    private static String submitAnswer(AnswerPoster answerPoster) throws IOException {
        String jsonString = JSON.toJSONString(answerPoster);
        return HttpJSONUtil.readContentFromPost(PathUtil.POST_URL,jsonString);
    }

    /**
     * 获取成绩对象，并打印
     * @param answerPoster 答案提交
     * @throws IOException
     */
    public static boolean requestForMyScore(AnswerPoster answerPoster) throws IOException {
        MyScore myScore = JSON.parseObject(submitAnswer(answerPoster),MyScore.class);
        if(myScore.isScore()){
            System.out.printf("挑战成功！用时：%.2fs\n",myScore.getTime());
            return true;
        } else {
            System.err.println("挑战失败...");
            return false;
        }
    }
}


