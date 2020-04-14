package com.example.msghandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private ProgressBar progressBar;
    private TextView textView;

    private int progress=0;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            textView.setText(msg.arg1+"%");
            progressBar.setProgress(msg.arg1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.button);
        progressBar=findViewById(R.id.progressBar);
        textView=findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"测试中",Toast.LENGTH_SHORT).show();
            }
        });

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    progress++;

                    Message msg=handler.obtainMessage();
                    msg.arg1=progress;
                    handler.sendMessage(msg);

                    if(progress>100){
                        progress=0;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }

}
