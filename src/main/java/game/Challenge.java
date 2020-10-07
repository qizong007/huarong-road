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
        String uuid;

        @JSONField(name = "challengercount")
        int challengerCount;

        @JSONField(name = "pubtimestamp")
        long pubTimeStamp;

        @JSONField(name = "author")
        String author;

        @JSONField(name = "records")
        int records;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Record{

        @JSONField(name = "rank")
        int rank;

        @JSONField(name = "owner")
        String owner;

        @JSONField(name = "step")
        int step;

        @JSONField(name = "timeelapsed")
        long timeElapsed;

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
        List<Record> list = JSON.parseObject(HttpJSONUtil.readContentFromGet(PathUtil.GET_CHALLENGE_RECORD), List.class);
        for(Record record:list){
            System.out.println(record);
        }
    }

}
