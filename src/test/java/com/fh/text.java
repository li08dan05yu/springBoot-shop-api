package com.fh;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class text {

    public static void main(String[] args) {

        //1.打开浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //2.声明get请求
        HttpGet httpGet = new HttpGet("http://localhost:8080/typology/queryTypology");
        //3.发送请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);

        //4.判断状态码
        if(response.getStatusLine().getStatusCode()==200){
            HttpEntity entity = response.getEntity();
            //使用工具类EntityUtils，从响应中取出实体表示的内容并转换成字符串

            String string = EntityUtils.toString(entity, "utf-8");
            System.out.println(string);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (response!=null){
                //5.关闭资源
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
