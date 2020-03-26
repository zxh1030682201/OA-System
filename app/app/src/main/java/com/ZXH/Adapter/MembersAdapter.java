package com.zxh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zxh.Entity.User;
import com.zxh.R;

import java.util.List;

public class MembersAdapter extends ArrayAdapter<User> {

    private int resourceId;

    public MembersAdapter(Context context, int textViewResourceId, List<User> obj) {
        super(context, textViewResourceId,obj);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        User user=getItem(position);
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
        TextView tel=(TextView)view.findViewById(R.id.user_tel);
        tel.setText(user.getTel());

        return view;
    }
}
