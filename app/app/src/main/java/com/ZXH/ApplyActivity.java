package com.ZXH;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ZXH.Entity.User;
import com.google.gson.Gson;

public class ApplyActivity extends AppCompatActivity {

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        getSupportActionBar().hide();

        Button btn1 = (Button)findViewById(R.id.apply_botton);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SharedPreferences pref = getSharedPreferences("loginUser",MODE_PRIVATE);
                String username=pref.getString("username","");
                String password=pref.getString("password","");
                System.out.println(username+"  "+password);
                Toast.makeText(ApplyActivity.this,"123",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
