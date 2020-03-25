package com.zxh.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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

    private Gson gson = new Gson();
    private HttpUtil httpUtil = new HttpUtil();
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private List<Message> unreadMsg = new ArrayList<>();
    private List<Message> myReadMsg = new ArrayList<>();
    private List<Message> mySendMsg = new ArrayList<>();

    static CountDownLatch cdl=new CountDownLatch(10);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button sendMsg = getActivity().findViewById(R.id.sendMsg);
        final ListView msgList = getActivity().findViewById(R.id.msgList);


        SharedPreferences pref = getActivity().getSharedPreferences("loginUser", 0);
        Integer id=pref.getInt("userId",0);

        //        获取当前用户所有未读信息
        httpUtil.doGet("http://10.0.2.2:7000/msg/queryByRU/"+id, new okhttp3.Callback(){
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                unreadMsg= (List<Message>) stringToArray(response.body().string(),Message[].class);
            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });
        //        获取当前用户所有已读信息
        httpUtil.doGet("http://10.0.2.2:7000/msg/queryByRR/"+id, new okhttp3.Callback(){
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                myReadMsg = (List<Message>) gson.fromJson(response.body().string(),new TypeToken<List<Message>>(){}.getRawType());
                cdl.countDown();
            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });
        //        获取当前用户所有未读信息
        httpUtil.doGet("http://10.0.2.2:7000/msg/queryByS/"+id, new okhttp3.Callback(){
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                mySendMsg = (List<Message>) gson.fromJson(response.body().string(),new TypeToken<List<Message>>(){}.getRawType());
                cdl.countDown();
            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });


        MessageAdapter adapter = new MessageAdapter(getActivity(), R.layout.message_item, unreadMsg);
        msgList.setAdapter(adapter);


        msgList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id) {
                Message message=unreadMsg.get(position);
                Intent intent=new Intent(getActivity(), MessageDetail.class);
                intent.putExtra("Message",message);
                startActivity(intent);
            }
        });


        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }
}
