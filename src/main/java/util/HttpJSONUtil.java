package util;

import java.io.*;
import java.net.*;

public class HttpJSONUtil {

    /**
     * Get请求
     *
     * */
    public static String readContentFromGet(String GET_URL) throws IOException{
        URL getUrl = new URL(GET_URL);
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));//设置编码,否则中文乱码
        StringBuffer lines=new StringBuffer();
        String line=null;
        while ((line = reader.readLine()) != null){
            lines.append(line);
        }
        reader.close();
        // 断开连接
        connection.disconnect();
        return lines.toString();
    }

    /**
     * post请求
     *
     * */
    public static String readContentFromPost(String url, String param) throws IOException{
        String result = "";
        BufferedReader in = null;
        OutputStreamWriter out = null;
        try {
            //创建URL对象、
            URL urlPost = new URL(url);
            //打开URL连接
            URLConnection connection = urlPost.openConnection();
            HttpURLConnection conn = (HttpURLConnection) connection;
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (MalformedURLException e) {
            System.out.println("error");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}

