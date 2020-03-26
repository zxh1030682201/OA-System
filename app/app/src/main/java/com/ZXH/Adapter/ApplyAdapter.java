package com.zxh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zxh.Entity.Apply;
import com.zxh.R;

import java.util.List;

public class ApplyAdapter extends ArrayAdapter<Apply> {
    private int resourceId;

    //将上下文、ListView子项布局的id、数据 传递进来
    public ApplyAdapter(Context context, int textViewResourceId, List<Apply> obj){
        super(context, textViewResourceId, obj);
        resourceId = textViewResourceId;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Apply apply = getItem(position);
        //LayoutInflater的inflate()方法接收3个参数：需要实例化布局资源的id、ViewGroup类型视图组对象、false
        //false表示只让父布局中声明的layout属性生效，但不会为这个view添加父布局
        View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        }else{
            view=convertView;
        }

        TextView applyType=(TextView)view.findViewById(R.id.apply_type);
        TextView applyStatus=(TextView)view.findViewById(R.id.apply_applyStatus);

        TextView startTime=(TextView)view.findViewById(R.id.apply_startTime);
        startTime.setText(apply.getStartTime());
        TextView endTime=(TextView)view.findViewById(R.id.apply_endTime);
        endTime.setText(apply.getEndTime());

        switch (apply.getApplyType()){
            case 31:
                applyType.setText("病假");
                break;
            case 32:
                applyType.setText("事假");
                break;
            case 33:
                applyType.setText("其他");
        }

        switch (apply.getApplyStatus()){
            case 11:
                applyStatus.setText("未审批");
                break;
            case 12:
                applyStatus.setText("审批通过");
                break;
            case 13:
                applyStatus.setText("审批驳回");
        }
        return view;
    }
}
