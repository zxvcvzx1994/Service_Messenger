package com.example.kh.myapplication.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

/**
 * Created by kh on 6/24/2017.
 */

public class MyService extends Service {
    private static final int GET_COUNT = 0;
    private static final String TAG = "vo cong vinh";
    private boolean mIsRandomNumbergeneratoron;
    private int randomNumber;
    private final int MAX = 100;
    private final int MIN = 1;
    private class RandomNumberRequestHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GET_COUNT:
                    Message messageSendRandomNumber = Message.obtain(null,GET_COUNT );
                    messageSendRandomNumber.arg1= getRN();
                    try {
                        msg.replyTo.send(messageSendRandomNumber);
                    } catch (RemoteException e) {
                        Log.i(TAG, "handleMessage: "+e.getMessage());
                    }
            }
        }
    }
    private Messenger randomMessengerHandler = new Messenger(new RandomNumberRequestHandler());
    public void getRandomNumber(){
        mIsRandomNumbergeneratoron=true;
        while(mIsRandomNumbergeneratoron){
            try {
                Thread.sleep(2000);
                randomNumber  =new Random().nextInt(MAX)+MIN;
                Log.i(TAG, "getRandomNumber: "+randomNumber);
            } catch (InterruptedException e) {
                Log.i(TAG, "getRandomNumber: "+e.getMessage());
            }

        }
    }

    public  int getRN(){
        return randomNumber;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return randomMessengerHandler.getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsRandomNumbergeneratoron = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getRandomNumber();
            }
        }).start();
        return START_STICKY;
    }
}
