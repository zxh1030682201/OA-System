package com.ZXH;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Looper;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ZXH.Entity.User;
import com.ZXH.Util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {

    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_login=(Button)findViewById(R.id.login_button);
        final EditText username=(EditText)findViewById(R.id.username);
        final EditText password=(EditText)findViewById(R.id.password);

        final HttpUtil httpUtil=new HttpUtil();

        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String json="{\"username\":\""+username.getText()+"\",\"password\":\""+password.getText()+"\"}";
                System.out.println(json);
                httpUtil.doPost("http://10.0.2.2:7000/user/login",json,new okhttp3.Callback(){
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result=response.body().string();
                        if(result.equals("密码错误")){
                            Looper.prepare();
                            Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                        if(result.equals("用户不存在")){
                            Looper.prepare();
                            Toast.makeText(LoginActivity.this,"用户名不存在",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }else {
                            Looper.prepare();
                            Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                            User user=gson.fromJson(result,User.class);
                            SharedPreferences.Editor editor = getSharedPreferences("loginUser",MODE_PRIVATE).edit();
                            editor.putString("name",user.getName());
                            editor.putString("username",user.getUsername());
                            editor.putString("password",user.getPassword());
                            editor.putInt("deptId",user.getDeptId());
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            Looper.loop();
                        }

                    }
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                });

            }
        });

    }

}
