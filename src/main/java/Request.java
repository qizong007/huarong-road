import com.alibaba.fastjson.JSON;

import java.io.IOException;

public class Request {

    public static String imgBase64;
    public static int step;
    public static int[] swap;
    public static String uuid;

    /**
     * json请求图片
     * @param url "http://47.102.118.1:8089/api/problem?stuid=111800827"
     */
    public static void requestForTheImg(String url){
        try {
            Subject subject = JSON.parseObject(HttpJSONUtil.readContentFromGet(url),Subject.class);
            imgBase64 = subject.getImg();
            step = subject.getStep();
            swap = subject.getSwap();
            uuid = subject.getUuid();
            if(FileTypeTranslate.generateImage(subject.getImg(),"D:/testImg/test.jpg")){
                System.out.println("JSON请求图片成功!!!");
                return;
            }
            System.out.println("JSON请求图片失败...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}


