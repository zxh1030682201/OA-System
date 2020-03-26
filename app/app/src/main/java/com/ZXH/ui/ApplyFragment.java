package com.zxh.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zxh.Adapter.ApplyAdapter;
import com.zxh.Entity.Apply;
import com.zxh.Entity.User;
import com.zxh.R;
import com.zxh.Util.HttpUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.SneakyThrows;

public class ApplyFragment extends Fragment {
    private HttpUtil httpUtil;
    private List<Apply> applys=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apply, container, false);
    }

    @SneakyThrows
    @Override
    public void onStart() {
        super.onStart();
        final Button addBtn=getActivity().findViewById(R.id.addApply);
        final ListView lists=getActivity().findViewById(R.id.applyList);
        final SharedPreferences pref = getActivity().getSharedPreferences("loginUser", 0);
        final Integer id=pref.getInt("userId",0);


        applys=(List<Apply>)stringToArray(httpUtil.doGet("http://10.0.2.2:7000/apply/queryByFrom/"+id), Apply[].class);
        ApplyAdapter adapter = new ApplyAdapter(getActivity(),R.layout.apply_item,applys);
        lists.setAdapter(adapter);

        //按钮点击事件
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"新建申请",Toast.LENGTH_SHORT).show();
            }
        });


    }

    //  json转化方法
    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr);
    }
}
