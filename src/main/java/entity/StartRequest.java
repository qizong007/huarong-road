package entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartRequest {
    @JSONField(name = "teamid")
    String teamId;

    @JSONField(name = "token")
    String token;
}
