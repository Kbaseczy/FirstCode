package com.example.net_networktest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/*
    todo: 两种封装方式，OKHttp 封装了Callback-->回调接口onFailure(),onResponse()
    todo: 使用HttpUrlConnection 需要自己定义接口HttpCallbackListener
 */
public class HttpUtil {
    public static void sendHttpRequestWithUrlConnection(String address,
                                                        final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://www.baidu.com");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
//                    connection.setRequestMethod("POST");
//                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//                    out.writeBytes("username=123&password=123");
                    connection.setReadTimeout(5000);
                    connection.setReadTimeout(5000);
                    InputStream inputStream = connection.getInputStream();// todo 获取输入流
                    //TODO 对获取到输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if(listener != null){
                        listener.onFinish(response.toString());
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    if(listener != null){
                        listener.onError(e);
                    }
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();

    }

    public static void sendHttpRequestWithOkHttp(String address, Callback call){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(call);
    }


}
