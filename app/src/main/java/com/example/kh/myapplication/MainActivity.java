package com.example.kh.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kh.myapplication.Service.MyService;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private Intent intentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        intentService = new Intent(this, MyService.class);
    }

    @OnClick(R.id.btnStartService)
    public void startSV(){
            startService(intentService);

    }
    @OnClick(R.id.btnStopService)
    public void stopSV(){
        stopService(intentService);

    }
}
