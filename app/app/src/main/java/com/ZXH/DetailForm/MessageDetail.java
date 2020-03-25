package com.zxh.DetailForm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.zxh.Entity.Message;
import com.zxh.R;

public class MessageDetail extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_detail);

        Intent intent=getIntent();
        Message message=(Message) intent.getSerializableExtra("Message");
        System.out.println(message);

    }
}
