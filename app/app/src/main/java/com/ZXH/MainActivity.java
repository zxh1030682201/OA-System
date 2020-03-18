package com.ZXH;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();
        tabHost.setup();

        tabHost.addTab(tabHost.newTabSpec("msgTab").setIndicator("我的消息").setContent(new Intent(this,MsgActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("atdTab").setIndicator("我的考勤").setContent(new Intent(this,AtdActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("applyTab").setIndicator("我的申请").setContent(new Intent(this,ApplyActivity.class)));

    }
}
