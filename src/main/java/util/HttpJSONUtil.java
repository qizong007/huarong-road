package util;

import java.io.*;
import java.net.*;

public class HttpJSONUtil {

    /**
     * GET请求
     * @param GET_URL 请求的url
     * @return json串
     * @throws IOException
     */
    public static String readContentFromGet(String GET_URL) throws IOException{
        // URL请求，建立连接
        URL getUrl = new URL(GET_URL);
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
        connection.connect();
        // 将返回内容读入内存中（设置编码,否则中文乱码）
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
        StringBuffer lines=new StringBuffer();
        String line=null;
        while ((line = reader.readLine()) != null){
            lines.append(line);
        }
        // 释放资源，断开连接
        reader.close();
        connection.disconnect();
        return lines.toString();
    }

    /**
     * POST请求
     * @param POST_URL POST请求的url
     * @param params json串形式的POST表单
     * @return json
     * @throws IOException
     */
    public static String readContentFromPost(String POST_URL, String params) throws IOException{
        // URL请求，建立连接
        URL postUrl = new URL(POST_URL);
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        // Post 请求不能使用缓存
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        // 注意：一定要设置json格式
        connection.setRequestProperty("Content-Type","application/json");
        // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
        // 要注意的是connection.getOutputStream会隐含的进行connect。
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
        out.writeBytes(params);
        out.flush();
        out.close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));//设置编码,否则中文乱码
        String line=null;
        StringBuffer lines=new StringBuffer();
        while ((line = reader.readLine()) != null){
            lines.append(line);
        }
        // 释放资源，断开连接
        reader.close();
        connection.disconnect();
        return lines.toString();
    }

}

