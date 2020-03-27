package com.zxh.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zxh.Adapter.MembersAdapter;
import com.zxh.Entity.User;
import com.zxh.R;
import com.zxh.Util.HttpUtil;

import java.util.Arrays;
import java.util.List;

import lombok.SneakyThrows;

public class MembersFragment extends Fragment {

    private List<User> users;
    private HttpUtil httpUtil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_members, container, false);
    }

    @SneakyThrows
    @Override
    public void onStart() {
        super.onStart();
        users= (List<User>) stringToArray(httpUtil.doGet("http://10.0.2.2:7000/user/queryAll/"),User[].class);
        ListView userList=getActivity().findViewById(R.id.memberList);
        MembersAdapter adapter=new MembersAdapter(getActivity(),R.layout.members_item,users);
        userList.setAdapter(adapter);


//        View memberItem = View.inflate(getActivity(),R.layout.members_item, null);





    }


    //  json转化方法
    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr);
    }
}
