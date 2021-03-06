package com.mubarak.agromobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.mubarak.agromobile.authentication.Login;

public class SplashScreen extends AppCompatActivity {
    private Handler mhandler;
    private Runnable mrunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.deep_green));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.deep_green));
        }
            mrunnable = new Runnable() {
                @Override
                public void run() {
//                    if (condition == 0){
//                        startActivity(new Intent(getApplicationContext(), PreAuthentication.class));
//                        finish();
//                    }else {
//                        startActivity(new Intent(getApplicationContext(), AdminLogin.class));
//                        finish();
//                    }

                    startActivity(new Intent(getApplicationContext(), Login.class));
                      finish();


                }
            };
            mhandler = new Handler();
            mhandler.postDelayed(mrunnable, 4000);

        }


        @Override
        protected void onDestroy(){
            super.onDestroy();
            if(mhandler!=null && mrunnable!=null)
                mhandler.removeCallbacks(mrunnable);
        }
    }