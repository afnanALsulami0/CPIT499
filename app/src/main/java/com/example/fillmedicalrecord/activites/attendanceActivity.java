package com.example.fillmedicalrecord.activites;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fillmedicalrecord.models.EventDecorator;
import com.example.fillmedicalrecord.R;
import com.example.fillmedicalrecord.models.StudentModel;
import com.example.fillmedicalrecord.database.ApiClient;
import com.example.fillmedicalrecord.database.ApiInterface;
import com.example.fillmedicalrecord.databinding.ActivityAttendanceBinding;
import com.example.fillmedicalrecord.models.attendance_model;
import com.example.fillmedicalrecord.models.user_attendance;
import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class attendanceActivity extends AppCompatActivity {
    ArrayList<user_attendance> data;
    SharedPreferences sharedPreferences;
    StudentModel user;
    String device_id;
    ActivityAttendanceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_attendance);
        setUpToolBar();
        if (getIntent().hasExtra("id")){
            device_id = getIntent().getStringExtra("id");
        }
        setTitle("Attendance Screen");
        List<CalendarDay> days = new ArrayList<>();
        sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("user", "");
        user = gson.fromJson(json, StudentModel.class);
        get_user_attendance();

        binding.calendarView.state().edit()
                .setFirstDayOfWeek(DayOfWeek.of(Calendar.WEDNESDAY))
                .setMinimumDate(CalendarDay.from(2023, 1, 1))
                .setMaximumDate(CalendarDay.from(2024, 12, 30))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
    }

    private void get_user_attendance() {

        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        Call<attendance_model> call = services.get_users_attendance(device_id);

        call.enqueue(new Callback<attendance_model>() {
            @Override
            public void onResponse(Call<attendance_model> call, Response<attendance_model> response) {

                if (response.body().getSuccess().equals("1")) {

                    data = new ArrayList<>();
                    data = response.body().getData();

                    if (data.size() > 0){

                        for (int i = 0; i<data.size(); i++){
                            List<CalendarDay> days = new ArrayList<>();
                            String date = data.get(i).getDate();
                            String[] dt = date.split(" ");
                            String[] dtsplit = dt[0].split("-");

                            CalendarDay day = CalendarDay.from(Integer.parseInt(dtsplit[0]), Integer.parseInt(dtsplit[1]),  Integer.parseInt(dtsplit[2]));
                            days.add(day);
                            EventDecorator dates = new EventDecorator(getResources().getColor(R.color.blue),days,attendanceActivity.this.getBaseContext());
                            binding.calendarView.addDecorator(dates);

                            Log.i("tessst","test");

                        }
                    }

                } else {
                    Toast.makeText(attendanceActivity.this, response.body().getSuccess(), LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<attendance_model> call, Throwable t) {
                Toast.makeText(attendanceActivity.this, t.getMessage(), LENGTH_SHORT).show();

            }
        });
    }
    private void setUpToolBar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                onBackPressed();

            }

        });

    }

}