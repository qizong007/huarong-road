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

    public static List<TeamInRank> getRank() throws IOException {
        return JSON.parseObject(HttpJSONUtil.readContentFromGet(PathUtil.GET_RANK), List.class);
    }

}
