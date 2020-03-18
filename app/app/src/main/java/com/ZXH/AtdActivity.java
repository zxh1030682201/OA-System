package com.ZXH;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AtdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atd);
        getSupportActionBar().hide();
    }
}
