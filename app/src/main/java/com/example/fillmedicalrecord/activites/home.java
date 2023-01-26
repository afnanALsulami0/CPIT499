package com.example.fillmedicalrecord.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.fillmedicalrecord.fragments.LocationFragment;
import com.example.fillmedicalrecord.R;
import com.example.fillmedicalrecord.fragments.attendanceFragment;
import com.example.fillmedicalrecord.fragments.homeFragment;
import com.example.fillmedicalrecord.fragments.settingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    com.example.fillmedicalrecord.fragments.homeFragment homeFragment = new homeFragment();
    com.example.fillmedicalrecord.fragments.settingFragment settingFragment=new settingFragment();
    LocationFragment locationFragment=new LocationFragment();
    com.example.fillmedicalrecord.fragments.attendanceFragment attendanceFragment=new attendanceFragment();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);

        bottomNavigationView = findViewById(R.id.bottom);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;

                    case R.id.location:        getSupportFragmentManager().beginTransaction().replace(R.id.container,locationFragment).commit();
                        return true;
                    case R.id.attendance:        getSupportFragmentManager().beginTransaction().replace(R.id.container,attendanceFragment).commit();
                        return true;
                    case R.id.settings:        getSupportFragmentManager().beginTransaction().replace(R.id.container,settingFragment).commit();
                        return true;

                }
                return false;
            }
        });
    }


}