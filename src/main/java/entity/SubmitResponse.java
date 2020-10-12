package entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitResponse {

    @JSONField(name = "owner")
    int owner;

    @JSONField(name = "rank")
    int rank;

    @JSONField(name = "step")
    int step;

    @JSONField(name = "success")
    boolean success;

    @JSONField(name = "timeelapsed")
    long timeElapsed;

}
