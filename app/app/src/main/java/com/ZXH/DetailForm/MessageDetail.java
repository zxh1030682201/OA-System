package com.zxh.DetailForm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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
        final User user=(User) intent.getSerializableExtra("User");
        final int mark=(int)intent.getSerializableExtra("mark");
        final TextView senderName=(TextView)findViewById(R.id.senderName);
        final EditText msgTheme=(EditText) findViewById(R.id.msgTheme);
        final EditText msgContent=(EditText)findViewById(R.id.msgContent);
        final TextView sendTime=(TextView)findViewById(R.id.sendTime);
        final Button close=(Button)findViewById(R.id.msgDetailClose);
        final Button finish=(Button)findViewById(R.id.msgDetailFinish);

        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记

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
                }
                if(finish.getText().equals("删除消息")){
                    httpUtil.doDelete("http://10.0.2.2:7000/msg/delete/"+message.getMsgId(),"1");
                    Toast.makeText(MessageDetail.this,"删除消息成功",Toast.LENGTH_SHORT).show();
                }else{
                    System.out.println("发送新消息");
                }
                MessageDetail.this.finish();
            }
        });
    }
}
