package entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartResponse {

    @JSONField(name = "chanceleft")
    int chanceLeft;

    @JSONField(name = "data")
    StartResponseData data;

    @JSONField(name = "success")
    boolean success;

    @JSONField(name = "uuid")
    String uuid;

    @JSONField(name = "expire")
    long expire;

}
