package game;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.HttpJSONUtil;
import util.PathUtil;

import java.io.IOException;
import java.util.List;

public class Challenge {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Question{

        @JSONField(name = "uuid")
        private String uuid;

        @JSONField(name = "challengercount")
        private int challengerCount;

        @JSONField(name = "pubtimestamp")
        private long pubTimeStamp;

        @JSONField(name = "author")
        private String author;

        @JSONField(name = "records")
        private int records;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Record{

        @JSONField(name = "rank")
        private int rank;

        @JSONField(name = "owner")
        private String owner;

        @JSONField(name = "step")
        private int step;

        @JSONField(name = "timeelapsed")
        private long timeElapsed;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class DataAboutQuestion{

        @JSONField(name = "letter")
        private String letter;

        @JSONField(name = "exclude")
        private int exclude;

        @JSONField(name = "challenge")
        private int[][] challenge;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class QuestionCreator{

        @JSONField(name = "teamid")
        private String teamId;

        @JSONField(name = "data")
        private DataAboutQuestion data;

        @JSONField(name = "token")
        private String token;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class CreationResponse{

        @JSONField(name = "success")
        boolean success;

        @JSONField(name = "message")
        String message;

        @JSONField(name = "uuid")
        String uuid;

    }

    /**
     * 获取所有赛题
     * @throws IOException
     */
    public static void getList() throws IOException {
        List<Question> list = JSON.parseObject(HttpJSONUtil.readContentFromGet(PathUtil.GET_CHALLENGE_LIST), List.class);
        for(Question question:list){
            System.out.println(question);
        }
    }

    /**
     * 获取赛题的解题记录，返回所有解出这题的队伍的纪录
     * @param uuid 题目标识
     * @throws IOException
     */
    public static void getRecord(String uuid) throws IOException {
        List<Record> list = JSON.parseObject(HttpJSONUtil.readContentFromGet(PathUtil.GET_CHALLENGE_RECORD+uuid), List.class);
        for(Record record:list){
            System.out.println(record);
        }
    }

    /**
     * 创建赛题
     * @param teamId
     * @param data
     * @param token
     * @throws IOException
     */
    public static void createQuestion(String teamId,DataAboutQuestion data,String token) throws IOException {
        QuestionCreator questionCreator = new QuestionCreator(teamId,data,token);
        String jsonString = JSON.toJSONString(questionCreator);
        String response = HttpJSONUtil.readContentFromPost(PathUtil.POST_CHALLENGE_CREATE,jsonString);
        CreationResponse creationResponse = JSON.parseObject(response,CreationResponse.class);
        System.out.println(creationResponse);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class StartRequest{

        @JSONField(name = "teamid")
        String teamId;

        @JSONField(name = "token")
        String token;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class StartResponse{

        @JSONField(name = "success")
        boolean success;

        @JSONField(name = "img")
        String img;

        @JSONField(name = "expire")
        long expire;

        @JSONField(name = "chanceleft")
        int chanceLeft;

    }

    /**
     * 挑战赛题的接口
     * @param teamId
     * @param token
     * @param uuid
     * @throws IOException
     */
    public static void startChallenge(String teamId,String token,String uuid) throws IOException {
        StartRequest startRequest = new StartRequest(teamId,token);
        String jsonString = JSON.toJSONString(startRequest);
        String response = HttpJSONUtil.readContentFromPost(PathUtil.POST_CHALLENGE_START+uuid,jsonString);
        StartResponse startResponse = JSON.parseObject(response,StartResponse.class);
        System.out.println(startResponse);
    }

}
