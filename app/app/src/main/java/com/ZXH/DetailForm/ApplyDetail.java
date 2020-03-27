package com.zxh.DetailForm;

import android.app.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.zxh.Entity.Apply;
import com.zxh.R;
import com.zxh.Util.HttpUtil;

import java.util.Calendar;
import java.util.Date;

import lombok.SneakyThrows;


public class ApplyDetail extends Activity {

    private Apply apply=new Apply();
    private int userId;
    private int deptMId;
    private String sString;
    private String eString;
    private int typeValue;
    private HttpUtil httpUtil;
    private Gson gson=new Gson();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_detail);

        //绑定控件
        final Button cancel=(Button)findViewById(R.id.applyCancel);
        final Button confirm=(Button)findViewById(R.id.applyConfirm);
        final Button startTime=(Button)findViewById(R.id.applyST);
        final Button endTime=(Button)findViewById(R.id.applyET);
        final Spinner applyType=(Spinner)findViewById(R.id.applyType);
        final EditText applyReason = (EditText)findViewById(R.id.applyReason);
        final TextView applyStatus=(TextView)findViewById(R.id.applyStatus);
        final TextView approvalReason=(TextView)findViewById(R.id.approvalReason);


        Intent intent=getIntent();
        apply=(Apply) intent.getSerializableExtra("Apply");
        userId=intent.getIntExtra("userId",0);
        deptMId=intent.getIntExtra("deptMId",0);

        System.out.println("applyDetail:  "+apply+"==="+userId+"==="+deptMId);

        //设置选择器选项
        final String[] status={"病假","事假","其他"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,status);
        applyType.setAdapter(adapter);


        if(apply == null){
            //新建apply
        }else{
            //显示apply
            confirm.setVisibility(View.INVISIBLE);
            applyReason.setText(apply.getApplyReason());
            approvalReason.setText(apply.getApprovalReason());
            startTime.setText("开始时间："+apply.getStartTime());
            endTime.setText("结束时间："+apply.getEndTime());
            switch (apply.getApplyStatus()){
                case 11:
                    applyStatus.setText("未审批");
                    break;
                case 12:
                    applyStatus.setText("审批通过");
                    break;
                case 13:
                    applyStatus.setText("审批驳回");
                    break;
            }
            switch (apply.getApplyType()){
                case 31:
                    applyType.setSelection(0);
                    break;
                case 32:
                    applyType.setSelection(1);
                    break;
                case 33:
                    applyType.setSelection(2);
                    break;
            }
        }


        //设置开始时间
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cd= Calendar.getInstance();
                Date date=new Date();
                cd.setTime(date);
                DatePickerDialog dp= new DatePickerDialog(ApplyDetail.this, new DatePickerDialog.OnDateSetListener(){
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        sString=year+"-"+String.format("%02d",monthOfYear)+"-"+String.format("%02d",dayOfMonth);
                        startTime.setText("开始时间:\n"+sString);
                    }
                },
                cd.get(Calendar.YEAR),
                cd.get(Calendar.MONTH),
                cd.get(Calendar.DAY_OF_MONTH));
                dp.show();
            }
        });

        //设置结束时间
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cd= Calendar.getInstance();
                Date date=new Date();
                cd.setTime(date);
                DatePickerDialog dp= new DatePickerDialog(ApplyDetail.this, new DatePickerDialog.OnDateSetListener(){
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        eString=year+"-"+String.format("%02d",monthOfYear)+"-"+String.format("%02d",dayOfMonth);
                        endTime.setText("结束时间:\n"+eString);
                    }
                },
                        cd.get(Calendar.YEAR),
                        cd.get(Calendar.MONTH),
                        cd.get(Calendar.DAY_OF_MONTH));
                dp.show();
            }
        });


        //确认按钮
        confirm.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {
                typeValue=(applyType.getSelectedItemPosition()==0)?31:((applyType.getSelectedItemPosition()==1)?32:33);
                if(sString == null){
                    Toast.makeText(ApplyDetail.this,"请选择开始日期",Toast.LENGTH_SHORT).show();
                }else if(eString == null){
                    Toast.makeText(ApplyDetail.this,"请选择结束日期",Toast.LENGTH_SHORT).show();
                }else if(applyReason.getText().toString().isEmpty()){
                    Toast.makeText(ApplyDetail.this,"请填写申请理由",Toast.LENGTH_SHORT).show();
                }else{
                    Apply apply1=new Apply(0,userId,null,deptMId,null,typeValue,sString,eString,applyReason.getText().toString(),"",11);
                    String json=gson.toJson(apply1);
                    String result=httpUtil.doPost("http://10.0.2.2:7000/apply/add",json);
                    Toast.makeText(ApplyDetail.this,result,Toast.LENGTH_SHORT).show();
                    if(result.equals("新增申请成功")) {
                        ApplyDetail.this.finish();
                    }
                }

            }
        });
        //取消按钮
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyDetail.this.finish();
            }
        });
    }
}
