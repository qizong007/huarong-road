package game;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import entity.Answer;
import entity.StartRequest;
import entity.StartResponse;
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
    public static class Question{

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
    public static class Record{

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
    static class DataAboutQuestion{

        @JSONField(name = "letter")
        private String letter;

        @JSONField(name = "exclude")
        private int exclude;

        @JSONField(name = "challenge")
        private int[][] challenge;

        @JSONField(name = "step")
        private int step;

        @JSONField(name = "swap")
        private int[] swap;
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
    static class CreationResponse{

        @JSONField(name = "success")
        boolean success;

        @JSONField(name = "message")
        String message;

        @JSONField(name = "uuid")
        String uuid;

    }

    /**
     * 获取还没写过的赛题
     * @throws IOException
     */
    public static List<Question> getList() throws IOException {
        List<Question> list = JSON.parseArray(HttpJSONUtil.readContentFromGet(PathUtil.GET_CHALLENGE_LIST_NO_REPEAT+PathUtil.TEAM_ID), Question.class);
        for(Question question:list){
            System.out.println(question);
        }
        return list;
    }

    /**
     * 获取所有赛题
     * @throws IOException
     */
    public static List<Question> getAllList() throws IOException {
        List<Question> list = JSON.parseArray(HttpJSONUtil.readContentFromGet(PathUtil.GET_CHALLENGE_LIST), Question.class);
        for(Question question:list){
            System.out.println(question);
        }
        return list;
    }

    /**
     * 获取赛题的解题记录，返回所有解出这题的队伍的纪录
     * @param uuid 题目标识
     * @throws IOException
     */
    public static List<Record> getRecord(String uuid) throws IOException {
        List<Record> list = JSON.parseArray(HttpJSONUtil.readContentFromGet(PathUtil.GET_CHALLENGE_RECORD+uuid), Record.class);
        for(Record record:list){
            System.out.println(record);
        }
        return list;
    }

    /**
     * 创建赛题
     * @param teamId
     * @param letter
     * @param token
     * @throws IOException
     */
    public static void createQuestion(String teamId,String letter,int exclude,String token) throws IOException {
        DataAboutQuestion data = new DataAboutQuestion();
        data.setLetter(letter);
        data.setExclude(exclude);
        int[][] board = BoardCreator.generateBoard();
        for (int i = 0; i < 9; i++) {
            if(board[i/3][i%3] == exclude){
                board[i/3][i%3] = 0;
                break;
            }
        }
        data.setChallenge(board);
        data.setStep(3);
        data.setSwap(new int[]{4,5});
        QuestionCreator questionCreator = new QuestionCreator(teamId,data,token);
        String jsonString = JSON.toJSONString(questionCreator);
        System.out.println(jsonString);
        String response = HttpJSONUtil.readContentFromPost(PathUtil.POST_CHALLENGE_CREATE,jsonString);
        CreationResponse creationResponse = JSON.parseObject(response,CreationResponse.class);
        System.out.println(creationResponse);
    }


    /**
     * 挑战赛题的接口
     * @param teamId
     * @param token
     * @param uuid
     * @throws IOException
     */
    public static StartResponse startChallenge(String teamId, String token, String uuid) throws IOException {
        StartRequest startRequest = new StartRequest(teamId,token);
        String jsonString = JSON.toJSONString(startRequest);
        String response = HttpJSONUtil.readContentFromPost(PathUtil.POST_CHALLENGE_START+uuid,jsonString);
        StartResponse startResponse = JSON.parseObject(response,StartResponse.class);
        return startResponse;
    }

}
