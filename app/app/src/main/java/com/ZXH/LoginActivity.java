package com.zxh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zxh.Entity.User;
import com.google.gson.Gson;
import com.zxh.Util.HttpUtil;
import lombok.SneakyThrows;



public class LoginActivity extends AppCompatActivity {

    private Gson gson = new Gson();
    private HttpUtil httpUtil = new HttpUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //主线程使用网络请求解决
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button btn_login=(Button)findViewById(R.id.login_button);
        final EditText username=(EditText)findViewById(R.id.username);
        final EditText password=(EditText)findViewById(R.id.password);
        final CheckBox remember=(CheckBox)findViewById(R.id.rememberPwd);



        SharedPreferences pref = getSharedPreferences("loginUser",MODE_PRIVATE);
        String un=pref.getString("username","");
        String pwd=pref.getString("password","");
        Boolean rb=pref.getBoolean("rememberPwd",false);

        username.setText(un);
        if(rb){
            password.setText(pwd);
            remember.setChecked(true);
        }

        btn_login.setOnClickListener(new View.OnClickListener(){
            @SneakyThrows
            @Override
            public void onClick(View v){
                String json="{\"username\":\""+username.getText()+"\",\"password\":\""+password.getText()+"\"}";
                String result=httpUtil.doPost("http://10.0.2.2:7000/user/login",json);

                if(result.equals("密码错误")){
                    Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
                if(result.equals("用户不存在")){
                    Toast.makeText(LoginActivity.this,"用户名不存在",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                    User user=gson.fromJson(result,User.class);

                    //登陆成功后存入登陆者信息
                    SharedPreferences.Editor editor = getSharedPreferences("loginUser",MODE_PRIVATE).edit();
                    editor.putBoolean("rememberPwd",remember.isChecked());
                    editor.putInt("userId",user.getUserId());
                    editor.putString("name",user.getName());
                    editor.putString("email",user.getEmail());
                    editor.putString("username",user.getUsername());
                    editor.putString("password",user.getPassword());
                    editor.putInt("deptId",user.getDeptId());
                    editor.putString("deptName",user.getDeptName());
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

}
