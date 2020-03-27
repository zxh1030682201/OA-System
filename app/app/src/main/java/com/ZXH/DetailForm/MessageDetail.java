package com.zxh.DetailForm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zxh.Entity.Message;
import com.zxh.Entity.User;
import com.zxh.LoginActivity;
import com.zxh.R;
import com.zxh.Util.HttpUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import lombok.SneakyThrows;

public class MessageDetail extends Activity {

    private List<User> users;
    private HttpUtil httpUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_detail);

        Intent intent=getIntent();
        // 获取上一个界面传过来的message或User
        final Message message=(Message) intent.getSerializableExtra("Message");
        final Integer receiver=intent.getIntExtra("receiver",0);
        final int mark=(int)intent.getIntExtra("mark",0);
        final TextView senderName=(TextView)findViewById(R.id.senderName);
        final EditText msgTheme=(EditText) findViewById(R.id.msgTheme);
        final EditText msgContent=(EditText)findViewById(R.id.msgContent);
        final TextView sendTime=(TextView)findViewById(R.id.sendTime);
        final Button close=(Button)findViewById(R.id.msgDetailClose);
        final Button finish=(Button)findViewById(R.id.msgDetailFinish);

        final SharedPreferences pref = getSharedPreferences("loginUser", 0);
        final Integer userId=pref.getInt("userId",0);
        final String name=pref.getString("name","");


        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");

        if(message != null){
            sendTime.setText(message.getSendTime());
            msgTheme.setText(message.getMsgTheme());
            msgContent.setText(message.getMsgContent());
            senderName.setText(message.getSenderName());
            System.out.println(message);
            if(message.getReaded() == 0 && mark != 1){
                finish.setText("标记已读");
            }else{
                finish.setText("删除消息");
            }
        }else{
            senderName.setText(name);
            Date date = new Date();// 获取当前时间
            sendTime.setText(sdf.format(date));
            finish.setText("发送消息");
        }
//      返回按钮
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDetail.this.finish();
            }
        });

        //      完成按钮
        finish.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {
                if(finish.getText().equals("标记已读")){
                    httpUtil.doGet("http://10.0.2.2:7000/msg/msgRead/"+message.getMsgId());
                    Toast.makeText(MessageDetail.this,"标记已读成功",Toast.LENGTH_SHORT).show();
                    MessageDetail.this.finish();
                }
                if(finish.getText().equals("删除消息")){
                    httpUtil.doDelete("http://10.0.2.2:7000/msg/delete/"+message.getMsgId(),"1");
                    Toast.makeText(MessageDetail.this,"删除消息成功",Toast.LENGTH_SHORT).show();
                    MessageDetail.this.finish();
                }else{
                    if(msgTheme.getText().toString().isEmpty()){
                        Toast.makeText(MessageDetail.this,"请填写主题",Toast.LENGTH_SHORT).show();
                    }else if(msgContent.getText().toString().isEmpty()){
                        Toast.makeText(MessageDetail.this,"请填写内容",Toast.LENGTH_SHORT).show();
                    }else {
                        Message newMsg=new Message(0,userId,null,receiver,null,sendTime.getText().toString(),msgTheme.getText().toString(),msgContent.getText().toString(),0);
                        String json=new Gson().toJson(newMsg);
                        String result=httpUtil.doPost("http://10.0.2.2:7000/msg/add",json);
                        if(result.equals("添加消息成功")){
                            MessageDetail.this.finish();
                        }
                        Toast.makeText(MessageDetail.this,result,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
