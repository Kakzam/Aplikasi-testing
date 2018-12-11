package com.jibril.malaikat.layoutsatu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread = new Thread(){
            public  void run (){
                try {
                    sleep(3000);
                }catch (InterruptedException masuk){
                    masuk.printStackTrace();
                }finally {
                    String action;
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}
