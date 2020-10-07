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
    class TeamInRank{

        @JSONField(name = "rank")
        int rank;

        @JSONField(name = "teamid")
        String teamId;

        @JSONField(name = "score")
        double score;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Solve{

        @JSONField(name = "challengeid")
        String challengeId;

        @JSONField(name = "rank")
        int rank;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class TeamInDetail{

        @JSONField(name = "rank")
        int rank;

        @JSONField(name = "score")
        double score;

        @JSONField(name = "solves")
        List<Solve> solves;

    }

    /**
     * 从高到低返回排行榜
     * @throws IOException
     */
    public static void getRank() throws IOException {
        List<TeamInRank> teams = JSON.parseObject(HttpJSONUtil.readContentFromGet(PathUtil.GET_RANK), List.class);
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
        System.out.println(team);
    }

}
