package entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitRequest {

    @JSONField(name = "uuid")
    String uuid;

    @JSONField(name = "teamid")
    String teamId;

    @JSONField(name = "token")
    String token;

    @JSONField(name = "answer")
    Answer answer;

}
