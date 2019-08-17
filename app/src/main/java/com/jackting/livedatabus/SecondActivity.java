package com.jackting.livedatabus;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

import com.jackting.lib.livedatabus.LiveDataBus;
import com.jackting.lib.livedatabus.LiveDataBusBeta;

public class SecondActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initViews();
        initData();
    }

    void initViews(){
        findViewById(R.id.btn_send_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send msg a to SecondActivity
                LiveDataBusBeta.get()
                        .with("key_msg_a")
                        .setValue("msg a");
            }
        });
        findViewById(R.id.btn_send_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send msg a to MainActivity
                LiveDataBusBeta.get()
                        .with("key_msg_b")
                        .setValue("msg b");
            }
        });
        findViewById(R.id.btn_send_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send msg from a thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //postValue子线程中可执行方法，setValue需要在UI线程中执行
                        LiveDataBusBeta.get()
                                .with("key_msg_c")
                                .postValue("msg c");
                    }
                }).start();
            }
        });
    }

    void initData(){
        LiveDataBusBeta.get()
                .with("key_msg_a",String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Toast.makeText(SecondActivity.this,s,Toast.LENGTH_LONG).show();
                    }
                });

        LiveDataBusBeta.get()
                .with("key_msg_c",String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Toast.makeText(SecondActivity.this,s,Toast.LENGTH_LONG).show();
                    }
                });

        LiveDataBus.get()
                .with("key_to_second",String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Toast.makeText(SecondActivity.this,s,Toast.LENGTH_LONG).show();
                    }
                });
    }
}
