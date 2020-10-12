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

public class Rank {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class TeamInRank{

        @JSONField(name = "rank")
        private int rank;

        @JSONField(name = "teamid")
        private String teamId;

        @JSONField(name = "score")
        private double score;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Success{

        @JSONField(name = "challengeid")
        private String challengeId;

        @JSONField(name = "problemid")
        private String problemId;

        @JSONField(name = "rank")
        private int rank;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Fail{

        @JSONField(name = "challengeid")
        private String challengeId;

        @JSONField(name = "problemid")
        private String problemId;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Unsolved{

        @JSONField(name = "challengeid")
        private String challengeId;

        @JSONField(name = "problemid")
        private String problemId;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class TeamInDetail{

        @JSONField(name = "rank")
        private int rank;

        @JSONField(name = "score")
        private double score;

        @JSONField(name = "success")
        List<Success> success;

        @JSONField(name = "fail")
        List<Fail> fail;

        @JSONField(name = "unsolved")
        List<Unsolved> unsolved;

    }

    /**
     * 从高到低返回排行榜
     * @throws IOException
     */
    public static void getRank() throws IOException {
        List<TeamInRank> teams = JSON.parseArray(HttpJSONUtil.readContentFromGet(PathUtil.GET_RANK), TeamInRank.class);
        for(TeamInRank team:teams){
           System.out.println(team);
        }
    }

    /**
     * 获取指定队伍的信息
     * @param teamId
     */
    public static void getTeamDetail(String teamId) throws IOException {
        TeamInDetail team = JSON.parseObject(HttpJSONUtil.readContentFromGet(PathUtil.GET_TEAM_DETAIL+teamId), TeamInDetail.class);
        System.out.println("当前排名："+team.rank);
        System.out.println("当前分数："+team.score);
        System.out.println("成功的题目如下：");
        for(Success success : team.success){
            System.out.println(success);
        }
        System.out.println("失败的题目如下：");
        for(Fail fail : team.fail){
            System.out.println(fail);
        }
        System.out.println("未解决的题目如下：");
        for(Unsolved unsolved : team.unsolved){
            System.out.println(unsolved);
        }
    }

}
