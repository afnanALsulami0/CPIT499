package com.example.fillmedicalrecord.fragments;


import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.threeten.bp.DayOfWeek;

import com.example.fillmedicalrecord.models.EventDecorator;
import com.example.fillmedicalrecord.R;
import com.example.fillmedicalrecord.models.StudentModel;
import com.example.fillmedicalrecord.database.ApiClient;
import com.example.fillmedicalrecord.database.ApiInterface;
import com.example.fillmedicalrecord.databinding.FragmentAttendanceBinding;
import com.example.fillmedicalrecord.models.attendance_model;
import com.example.fillmedicalrecord.models.user_attendance;
import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class attendanceFragment extends Fragment {
    FragmentAttendanceBinding binding;
    ArrayList<user_attendance> data;
    SharedPreferences sharedPreferences;
    StudentModel user;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAttendanceBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getActivity().setTitle("Attendance Screen");
        List<CalendarDay> days = new ArrayList<>();
        sharedPreferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
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

        return view;
    }



    private void get_user_attendance() {

        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        Call<attendance_model> call = services.get_users_attendance(user.getDevice_id());

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
                            EventDecorator dates = new EventDecorator(getResources().getColor(R.color.blue),days,attendanceFragment.this.getContext());
                            binding.calendarView.addDecorator(dates);

                            Log.i("tessst","test");

                        }
                    }

                } else {
                    Toast.makeText(requireActivity(), response.body().getSuccess(), LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<attendance_model> call, Throwable t) {
                Toast.makeText(requireActivity(), t.getMessage(), LENGTH_SHORT).show();

            }
        });
    }


}