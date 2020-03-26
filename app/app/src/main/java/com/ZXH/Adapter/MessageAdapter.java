package com.zxh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zxh.Entity.Message;
import com.zxh.R;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {
    private int resourceId;

    //将上下文、ListView子项布局的id、数据 传递进来
    public MessageAdapter(Context context, int textViewResourceId, List<Message> obj){
        super(context, textViewResourceId, obj);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message message = getItem(position);
        //LayoutInflater的inflate()方法接收3个参数：需要实例化布局资源的id、ViewGroup类型视图组对象、false
        //false表示只让父布局中声明的layout属性生效，但不会为这个view添加父布局
        View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        }else{
            view=convertView;
        }

        TextView msgTheme = (TextView) view.findViewById(R.id.msg_msgTheme);
        msgTheme.setText(message.getMsgTheme());

        TextView senderName = (TextView) view.findViewById(R.id.msg_senderName);
        senderName.setText(message.getSenderName());

        TextView sendTime = (TextView) view.findViewById(R.id.msg_sendTime);
        sendTime.setText(message.getSendTime());

        return view;
    }

}
