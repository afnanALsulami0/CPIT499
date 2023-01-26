package com.example.fillmedicalrecord.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.fillmedicalrecord.R;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);

       // sharedPreferences.edit().clear().apply();
        Boolean is_login = sharedPreferences.getBoolean("is_login",false);
        Boolean is_admin = sharedPreferences.getBoolean("is_admin",false);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
              if (is_login){
                  if (is_admin){
                      startActivity(new Intent(SplashActivity.this,adminHome.class));
                      finish();
                      Log.i("tesssst","entred2");

                  }else {

                      startActivity(new Intent(SplashActivity.this,home.class));
                      finish();
                      Log.i("tesssst","entred");
                  }
              }else {
                  startActivity(new Intent(SplashActivity.this,login.class));
                  finish();
              }
            }
        }, 3000);
    }
}