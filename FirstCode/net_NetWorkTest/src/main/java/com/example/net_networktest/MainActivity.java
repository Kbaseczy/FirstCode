package com.example.net_networktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
    1.http协议访问网络，httpURLConnection & OkHttp
    2.解析XML --> Pull  SAX
         JSON --> JSONObject , GSON
    3.调用HttpUtil 的  sendHttpRequestWithOkHttp方法
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button sendRequest = findViewById(R.id.send_request);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendRequestWithHttpUrlConnection();
                sendRequestWithOkHttp();
            }
        });

        //todo: 调用HttpUtil类方法、
        HttpUtil.sendHttpRequestWithOkHttp("http://www.baicu.com", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            //TODO 在这里对异常进行处理
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();//todo 得到服务器返回的具体内容
            }
        });


    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
//                        .url("http://www.baidu.com")
//                        .url("http://192.168.56.1/get_data.xml") todo 解析 xml
                        .url("http://192.168.56.1/get_data.json") //todo 解析json
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
//                    showResponse(responseData);
//                    parseXMLWithPull(responseData); todo: Pull方式解析XML
//                      parseXMLWithSAX(responseData);todo: SAX 方式解析XML
//                      parseJSONWithJsonObject(responseData); todo : JSONObject 解析JSON
                     parseJSONWithGSON(responseData);  //todo: GSON解析JSON。另外还有Jackson , FastJSON
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        List<App> appList = gson.fromJson(jsonData,new TypeToken<List<App>>(){}.getType());
        if(appList != null) {
            for (App app : appList) {
                Log.d(TAG, "id is " + app.getId());
                Log.d(TAG, "version is " + app.getVersion());
                Log.d(TAG, "name is " + app.getName());
            }
        }
    }

    private void parseJSONWithJsonObject(String jsonData) {
        try{
            JSONArray jsonArray = new JSONArray(jsonData);
                for(int i =0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    String name = jsonObject.getString("name");
                    String version = jsonObject.getString("version");
                    Log.d(TAG,"id is "+ id);
                    Log.d(TAG,"name is "+name);
                    Log.d(TAG,"version is "+version);
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseXMLWithSAX(String xmlData) {
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance() ;
            XMLReader reader = factory.newSAXParser().getXMLReader() ;
            ContentHandler handler = new ContentHandler();
            reader.setContentHandler(handler);  //todo 将contentHandler 实例设置到xmlReader中
            reader.parse(new InputSource(new StringReader(xmlData)));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    private void parseXMLWithPull(String xmlData) {
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            String id=null,name=null,version=null;
            while(eventType != XmlPullParser.END_DOCUMENT){
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if("id".equals(nodeName)){
                            id = xmlPullParser.nextText();
                        }else if("name".equals(nodeName)){
                            name = xmlPullParser.nextText();
                        }else if("version".equals(nodeName)){
                            version = xmlPullParser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if("app".equals(nodeName)){
                            Log.d("MainActivity","id is "+ id);
                            Log.d("MainActivity","name is "+ name);
                            Log.d("MainActivity","version is "+ version);
                        }
                        break;
                        default:
                            break;
                }
                eventType = xmlPullParser.next();
            }
        }catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequestWithHttpUrlConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL("http://www.baidu.com");
                    connection = (HttpURLConnection)url.openConnection();
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
                    while((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(reader != null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void showResponse(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //todo UI操作，将响应结果显示到界面
                //TODO 通过runOnUiThread 切换到主线程进行UI操作
                textView.setText(s);
            }
        });
    }
}
