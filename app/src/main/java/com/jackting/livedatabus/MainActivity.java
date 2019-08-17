package com.jackting.livedatabus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
    }

    void initViews(){
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //利用LiveDataBusBeta发送一个消息
                LiveDataBus.get().with("key_forever")
                        .setValue("hello jack");
            }
        });

        findViewById(R.id.btn_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });

        findViewById(R.id.btn_send_to_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveDataBus.get().with("key_to_second")
                        .setValue("hello second");
            }
        });

    }

    void initData(){
        //利用LiveDataBus接收数据
        LiveDataBus.get()
                .with("key_forever",String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
                    }
                });

        LiveDataBus.get()
                .with("key_msg_b",String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
                    }
                });

    }


}
