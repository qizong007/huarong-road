package entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartResponseData {

    @JSONField(name = "img")
    String img;

    @JSONField(name = "step")
    int step;

    @JSONField(name = "swap")
    int[] swap;

}
