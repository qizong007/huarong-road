package util;

import com.alibaba.fastjson.JSON;
import entity.*;
import game.Challenge;

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
     * 获得json请求来的图片(battle)
     * @param teamId
     * @param token
     * @param uuid
     * @return
     */
    public static StartResponse requestForTheImg(String teamId, String token, String uuid){
        try {
            StartResponse response = Challenge.startChallenge(teamId, token, uuid);
            if(response.getChanceLeft() == 0){
                System.err.println("没机会了，不玩了");
                return null;
            }
            System.out.println("今日剩余："+response.getChanceLeft()+"次");
            if(FileTypeTranslate.generateImage(response.getData().getImg(),PathUtil.REQUEST_PIC)){
                return response;
            }else{
                System.err.println("JSON请求图片失败...");
                return null;
            }
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
     * 通过POST请求提交答案(battle)
     * @param submitRequest
     * @return
     * @throws IOException
     */
    private static String submitAnswer(SubmitRequest submitRequest) throws IOException {
        String jsonString = JSON.toJSONString(submitRequest);
        return HttpJSONUtil.readContentFromPost(PathUtil.POST_CHALLENGE_SUBMIT,jsonString);
    }

    /**
     * 获取成绩对象，并打印
     * @param answerPoster 答案提交
     * @throws IOException
     */
    public static boolean requestForMyScore(AnswerPoster answerPoster) throws IOException {
        MyScore myScore = JSON.parseObject(submitAnswer(answerPoster),MyScore.class);
        if(myScore.isScore()){
            System.out.printf("挑战成功！用时：%.3fs\n",myScore.getTime());
            return true;
        } else {
            System.err.println("挑战失败...");
            return false;
        }
    }

    /**
     * 获取成绩对象，并打印(battle)
     * @param submitRequest
     * @return
     * @throws IOException
     */
    public static boolean requestForMyScore(SubmitRequest submitRequest) throws IOException {
        SubmitResponse myScore = JSON.parseObject(submitAnswer(submitRequest),SubmitResponse.class);
        if(myScore.isSuccess()){
            System.out.printf("挑战成功！用时：%.2f秒\n",(double)myScore.getTimeElapsed()/1000);
            return true;
        } else {
            System.err.println("挑战失败...");
            return false;
        }
    }
}


