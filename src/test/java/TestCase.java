import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.io.IOException;

public class TestCase {

    @Test
    public void testForGET(){
        String url = "http://47.102.118.1:8089/api/problem?stuid=031802230";
        try {
            Subject subject = JSON.parseObject(HttpJSONUtil.readContentFromGet(url),Subject.class);
            System.out.println(subject.getImg());
            System.out.println(subject.getStep());
            System.out.println(subject.getSwap().length);
            System.out.println(subject.getSwap()[0]+" "+subject.getSwap()[1]);
            System.out.println(subject.getUuid());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
