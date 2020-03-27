package com.zxh.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zxh.DetailForm.MessageDetail;
import com.zxh.Entity.User;
import com.zxh.R;
import com.zxh.ui.MessageFragment;

import java.util.List;

public class MembersAdapter extends ArrayAdapter<User> {

    private int resourceId;
    private Context mContext;

    public MembersAdapter(Context context, int textViewResourceId, List<User> obj) {
        super(context, textViewResourceId,obj);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        final User user=getItem(position);
        View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        }else{
            view=convertView;
        }
        TextView name=(TextView)view.findViewById(R.id.user_name);
        name.setText(user.getName());
        TextView username=(TextView)view.findViewById(R.id.user_username);
        username.setText(user.getUsername());
        TextView deptName=(TextView)view.findViewById(R.id.user_deptName);
        deptName.setText(user.getDeptName());

        Button sendMsg=view.findViewById(R.id.user_sendMsg);
        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity CurrentActivity = (Activity)v.getContext();
                Intent intent= new Intent(CurrentActivity,MessageDetail.class);
                intent.putExtra("receiver",user.getUserId());
                CurrentActivity.startActivity(intent);
            }
        });

        return view;
    }
}
