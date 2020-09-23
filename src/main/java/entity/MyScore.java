package entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyScore {

    @JSONField(name = "score")
    private boolean score;

    @JSONField(name = "time")
    private double time;
}
