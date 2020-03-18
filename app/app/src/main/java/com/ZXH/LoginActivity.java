package com.ZXH;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_login=(Button)findViewById(R.id.login_button);
        final EditText username=(EditText)findViewById(R.id.username);
        final EditText password=(EditText)findViewById(R.id.password);
        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                if(username.getText().length() == 0 || password.getText().length() == 0){
                    Toast.makeText(LoginActivity.this,"请输入账号密码",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this,"username"+username.getText()+"  password"+password.getText(),Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });

    }
}
