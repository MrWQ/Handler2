package com.example.administrator.handler;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView=(TextView)findViewById(R.id.textview);
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                textView.setText(msg.arg1+"/100");
                if(msg.arg1==100){
                    textView.setText("加载完毕");
                }
            }
        };

        final Runnable myworker =new Runnable() {
            @Override
            public void run() {
                int progress =0;
                while (progress<100){
                    Message msg= new Message();
                    msg.arg1=progress;
                    handler.sendMessage(msg);
                    progress+=10;
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                Message msg = handler.obtainMessage();
                msg.arg1=100;
                handler.sendMessage(msg);
//                Toast.makeText(MainActivity.this,"加载完毕",Toast.LENGTH_LONG).show();
            }
        };
        Button start =(Button)findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread workThread =new Thread(null,myworker,"workThread");
                workThread.start();
            }
        });

    }
}
