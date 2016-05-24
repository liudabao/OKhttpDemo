package com.example.lenovo.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.spdy.Header;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Button bt1;
    Button bt2;
    Button bt3;
    Button bt4;
    Button bt5;
    Button bt6;
    TextView tv;
    OkHttpClient client=new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private  void initView(){
        bt1=(Button)findViewById(R.id.button);
        bt2=(Button)findViewById(R.id.button2);
        bt3=(Button)findViewById(R.id.button3);
        bt4=(Button)findViewById(R.id.button4);
        tv=(TextView) findViewById(R.id.textView);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getSync("https://publicobject.com/helloworld.txt");
                    }
                }).start();

            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getAsync("https://publicobject.com/helloworld.txt");
                    }
                }).start();

            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        postSync("https://www.baidu.com");
                    }
                }).start();

            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        postAsync("https://www.baidu.com");
                    }
                }).start();

            }
        });
    }

    private void getSync(String url){
        client.setConnectTimeout(5, TimeUnit.SECONDS);
        client.setReadTimeout(5, TimeUnit.SECONDS);
        client.setWriteTimeout(5, TimeUnit.SECONDS);
        Request request=new Request.Builder().url(url).build();
        try {
            Response response=client.newCall(request).execute();
            if(response.isSuccessful()){
                Log.e("Get","sync ok "+response.body().string());
                Headers header=response.headers();
                for(int i=0;i<header.size();i++){
                    Log.e("Header",header.name(i)+" "+header.value(i));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getAsync(String url){
        Request request=new Request.Builder().url(url).build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("Get","async failed");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful()){
                    Log.e("Get","async succeed "+response.body().string());
                }
                else {
                    Log.e("Get","async response failed");
                }

            }
        });
       // call.cancel();

    }

    private void postSync(String url){
        RequestBody body=new FormEncodingBuilder()
                .add("name","test")
                .add("psd","123456")
                .build();
        Request request=new Request.Builder().url(url).post(body).build();
        try {
            Response response=client.newCall(request).execute();
            if(response.isSuccessful()){
                Log.e("Post","sync ok ");
            }
            else {
                Log.e("Post","sync failed ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void postAsync(String url){
        MediaType JSON=MediaType.parse("application/json;charset=utf-8");
        Person person=new Person();
        person.setId(1);
        person.setName("test");
        Gson gson=new Gson();
        String json=gson.toJson(person);
        Log.e("JSON", json);
        RequestBody body=RequestBody.create(JSON,json);
        Request request=new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("Post","async ok ");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.e("Post","async failed ");
            }
        });

    }




}
