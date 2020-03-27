package com.zxh.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zxh.Entity.Apply;
import com.zxh.Entity.Attendance;
import com.zxh.R;
import com.zxh.Util.HttpUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import lombok.SneakyThrows;

public class AttendanceFragment extends Fragment {
    private HttpUtil httpUtil;
    private Attendance atd =new Attendance();
    private List<Attendance> monthAtd=new ArrayList<>();
    private Gson gson;
    private int earlyNum=0;
    private int lateNum=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    @SneakyThrows
    @Override
    public void onStart() {
        super.onStart();

        final SharedPreferences pref = getActivity().getSharedPreferences("loginUser", 0);
        final Integer id=pref.getInt("userId",0);
        final Button checkInBtn=(Button)getActivity().findViewById(R.id.checkInBtn);
        final Button checkOutBtn=(Button)getActivity().findViewById(R.id.checkOutBtn);
        final TextView checkTotal=(TextView)getActivity().findViewById(R.id.checkTotal);
        final TextView lateTotal=(TextView)getActivity().findViewById(R.id.lateTotal);
        final TextView earlyTotal=(TextView)getActivity().findViewById(R.id.earlyTotal);
        final TextView checkInTip=(TextView)getActivity().findViewById(R.id.checkInTip);
        final TextView checkOutTip=(TextView)getActivity().findViewById(R.id.checkOutTip);


        SimpleDateFormat sdf = new SimpleDateFormat();
        SimpleDateFormat sdf1 = new SimpleDateFormat();
        SimpleDateFormat sdf2 = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        sdf1.applyPattern("yyyy-MM");
        sdf2.applyPattern("HH:mm");
        Date date = new Date();
        final String today=sdf.format(date);
        final String month=sdf1.format(date);
        final String time=sdf2.format(date);

        List<Attendance> atds = (List<Attendance>)stringToArray(httpUtil.doGet("http://10.0.2.2:7000/atd/queryByUD?userId="+id+"&date="+today),Attendance[].class);

        monthAtd = (List<Attendance>)stringToArray(httpUtil.doGet("http://10.0.2.2:7000/atd/queryByUD?userId="+id+"&date="+month),Attendance[].class);

        checkTotal.setText("本月签到次数："+monthAtd.size());

        for(int i=0;i<monthAtd.size();i++){
            if(monthAtd.get(i).getCheckInTime().compareTo("09:00") > 0){
                lateNum++;
            }
            if(monthAtd.get(i).getCheckOutTime().compareTo("18:00") < 0){
                earlyNum++;
            }
        }

        lateTotal.setText("本月迟到次数："+lateNum);
        earlyTotal.setText("本月早退次数："+earlyNum);

        if(atds.size() != 0 ){
            atd = atds.get(0);
            if(atd.getCheckOutTime().isEmpty()){
                checkInTip.setText("已签到，签到时间为:"+atd.getCheckInTime());
                checkOutTip.setText("今日尚未签退");
            }else{
                checkInTip.setText("已签到，签到时间为:"+atd.getCheckInTime());
                checkOutTip.setText("已签退，签退时间为:"+atd.getCheckOutTime());
            }
        }else{
            checkInTip.setText("今日尚未签到");
            checkOutTip.setText("今日尚未签退");
        }


        checkInBtn.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {
                String result=httpUtil.doGet("http://10.0.2.2:7000/atd/checkIn?userId="+id+"&date="+today+"&checkInTime="+time);
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                if(result.equals("签到成功")){
                    atd=((List<Attendance>)stringToArray(httpUtil.doGet("http://10.0.2.2:7000/atd/queryByUD?userId="+id+"&date="+today),Attendance[].class)).get(0);
                    checkInTip.setText("已签到，签到时间为:"+atd.getCheckInTime());
                }
            }
        });

        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {
                String result=httpUtil.doGet("http://10.0.2.2:7000/atd/checkOut?userId="+id+"&date="+today+"&checkOutTime="+time);
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                if(result.equals("签退成功")){
                    atd=((List<Attendance>)stringToArray(httpUtil.doGet("http://10.0.2.2:7000/atd/queryByUD?userId="+id+"&date="+today),Attendance[].class)).get(0);
                    checkOutTip.setText("已签退，签退时间为:"+atd.getCheckOutTime());
                }
            }
        });
    }


    //  json转化方法
    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr);
    }
}
