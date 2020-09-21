import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerPoster {

    @JSONField(name = "uuid")
    private String uuid;

    @JSONField(name = "answer")
    private Answer answer;
}
