package com.zxh.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zxh.Adapter.ApplyAdapter;
import com.zxh.DetailForm.ApplyDetail;
import com.zxh.Entity.Apply;
import com.zxh.Entity.Department;
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
        final Integer userId=pref.getInt("userId",0);
        final Integer deptId=pref.getInt("deptId",0);

        applys=(List<Apply>)stringToArray(httpUtil.doGet("http://10.0.2.2:7000/apply/queryByFrom/"+userId), Apply[].class);
        final Department department= (Department)new Gson().fromJson(httpUtil.doGet("http://10.0.2.2:7000/dept/queryById/"+deptId),Department.class);
        ApplyAdapter adapter = new ApplyAdapter(getActivity(),R.layout.apply_item,applys);
        lists.setAdapter(adapter);



        //      列表点击事件
        lists.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), ApplyDetail.class);
                Apply apply=applys.get(position);
                intent.putExtra("Apply",apply);
                startActivity(intent);
            }
        });

        //按钮点击事件
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ApplyDetail.class);
                intent.putExtra("userId",userId);
                intent.putExtra("deptMId",department.getDeptMId());
                startActivity(intent);
            }
        });


    }

    //  json转化方法
    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr);
    }
}
