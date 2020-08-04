package com.fh;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GostTest {

    public static void main(String[] args) {
        //1.打开浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //2.声明get请求
        HttpPost httpPost = new HttpPost("http://localhost:8080/typology/queryTypology");
        //3.开源中国为了安全，防止恶意攻击，在post请求中都限制了浏览器才能访问
        httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        //4.判断状态码
        List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
        parameters.add(new BasicNameValuePair("scope", "project"));
        parameters.add(new BasicNameValuePair("q", "java"));

        UrlEncodedFormEntity formEntity = null;
        CloseableHttpResponse response = null;
        try {
            formEntity = new UrlEncodedFormEntity(parameters,"UTF-8");


            httpPost.setEntity(formEntity);

            //5.发送请求
            response = httpClient.execute(httpPost);



            if(response.getStatusLine().getStatusCode()==200){
                HttpEntity entity = response.getEntity();
                String string = EntityUtils.toString(entity, "utf-8");
                System.out.println(string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(response !=null){
                //6.关闭资源
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
