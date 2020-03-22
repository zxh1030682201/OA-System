package com.ZXH;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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
                User user=new User();
                user.setUsername("admin");
                user.setPassword("123456");
                final Gson gson = new Gson();
                String json = gson.toJson(user);

                httpUtil.sendPostRequest("http://10.0.2.2:7000/user/login",json,new okhttp3.Callback(){
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
//                      Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                      startActivity(intent);
                        System.out.println(response.body().string());
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                });

            }
        });

    }

}
