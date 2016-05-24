package com.example.lenovo.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

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
                        get("https://publicobject.com/helloworld.txt");
                    }
                }).start();

            }
        });
    }

    private void get(String url){

        Request request=new Request.Builder().url(url).build();
        try {
            Response response=client.newCall(request).execute();
            if(response.isSuccessful()){
                Log.e("Get","ok");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
