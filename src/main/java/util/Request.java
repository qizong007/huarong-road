package util;

import com.alibaba.fastjson.JSON;
import entity.AnswerPoster;
import entity.MyScore;
import entity.Subject;

import java.io.IOException;

public class Request {

    /**
     * json请求图片
     * @param url util.PathUtil.REQUEST_URL
     */
    public static Subject requestForTheImg(String url){
        try {
            Subject subject = JSON.parseObject(HttpJSONUtil.readContentFromGet(url),Subject.class);
            if(FileTypeTranslate.generateImage(subject.getImg(),PathUtil.REQUEST_PIC)){
                System.out.println("JSON请求图片成功!!!");
                return subject;
            }
            System.out.println("JSON请求图片失败...");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 提交答案
     * @param answerPoster 答案提交
     * @return 返回成绩字符串
     * @throws IOException
     */
    private static String submitAnswer(AnswerPoster answerPoster) throws IOException {
        String jsonString = JSON.toJSONString(answerPoster);
        return HttpJSONUtil.readContentFromPost(PathUtil.POST_URL,jsonString);
    }

    /**
     * 打印成绩
     * @param answerPoster 答案提交
     * @throws IOException
     */
    public static void requestForMyScore(AnswerPoster answerPoster) throws IOException {
        MyScore myScore = JSON.parseObject(submitAnswer(answerPoster),MyScore.class);
        System.out.println(myScore);
    }
}


