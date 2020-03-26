package com.zxh.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxh.Adapter.MessageAdapter;
import com.zxh.DetailForm.MessageDetail;
import com.zxh.Entity.Message;
import com.zxh.MainActivity;
import com.zxh.R;
import com.zxh.Util.HttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import lombok.SneakyThrows;
import okhttp3.Call;
import okhttp3.Response;

public class MessageFragment extends Fragment {

    private HttpUtil httpUtil = new HttpUtil();
    private List<Message> unreadMsg = new ArrayList<>();
    private List<Message> myReadMsg = new ArrayList<>();
    private List<Message> mySendMsg = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Button myRead = getActivity().findViewById(R.id.myRead);
        Button mySend = getActivity().findViewById(R.id.mySend);
        final ListView msgList = getActivity().findViewById(R.id.msgList);

        SharedPreferences pref = getActivity().getSharedPreferences("loginUser", 0);
        Integer id=pref.getInt("userId",0);

        try {
            unreadMsg= (List<Message>) stringToArray(httpUtil.doGet("http://10.0.2.2:7000/msg/queryByRU/"+id),Message[].class);
            myReadMsg= (List<Message>) stringToArray(httpUtil.doGet("http://10.0.2.2:7000/msg/queryByRR/"+id),Message[].class);
            mySendMsg= (List<Message>) stringToArray(httpUtil.doGet("http://10.0.2.2:7000/msg/queryByS/"+id),Message[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        MessageAdapter adapter = new MessageAdapter(getActivity(), R.layout.message_item, unreadMsg);
        msgList.setAdapter(adapter);


//      列表点击事件
        msgList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id) {
                Message message=unreadMsg.get(position);
                Intent intent=new Intent(getActivity(), MessageDetail.class);
                intent.putExtra("Message",message);
                startActivity(intent);
            }
        });

//      按钮点击事件
        myRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myRead.getText().equals("已读消息")){
                    myRead.setText("未读消息");
                    MessageAdapter adapter = new MessageAdapter(getActivity(), R.layout.message_item, myReadMsg);
                    msgList.setAdapter(adapter);
                }else{
                    myRead.setText("已读消息");
                    MessageAdapter adapter = new MessageAdapter(getActivity(), R.layout.message_item, unreadMsg);
                    msgList.setAdapter(adapter);
                }

            }
        });

//      按钮点击事件
        mySend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

//  json转化方法
    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr);
    }
}
