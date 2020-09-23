package entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * img：经过base64编码的图片
 * step：第几步进行强制调换
 * swap：调换的图片编号
 * uuid：此题目的标识，提交答案时使用
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @JSONField(name = "img")
    private String img;

    @JSONField(name = "step")
    private int step;

    @JSONField(name = "swap")
    private int[] swap;

    @JSONField(name = "uuid")
    private String uuid;

}
