import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * operations：你的操作序列
 * swap：你自由调换操作的图片编号
 */
public class Answer {

    private String operations;
    private int[] swap;

}
