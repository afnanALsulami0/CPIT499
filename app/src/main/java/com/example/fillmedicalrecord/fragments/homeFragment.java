package com.example.fillmedicalrecord.fragments;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fillmedicalrecord.R;
import com.example.fillmedicalrecord.models.StudentModel;
import com.example.fillmedicalrecord.database.ApiClient;
import com.example.fillmedicalrecord.database.ApiInterface;
import com.example.fillmedicalrecord.databinding.FragmentHomeBinding;
import com.example.fillmedicalrecord.models.user_data;
import com.example.fillmedicalrecord.models.user_model;
import com.example.fillmedicalrecord.models.users_data_model;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class homeFragment extends Fragment {
    SharedPreferences sharedPreferences;
    StudentModel user;
    StudentModel muser;
    user_data user_data;
    FragmentHomeBinding _binding = null;
    TextView first_nm,last_nm,address,mclass,section,phone,height,wight,allergies,email,device_id,add_date,health,heart_rate,body_temp,blood_type,age;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Home Screen");
        sharedPreferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("user", "");
        user = gson.fromJson(json, StudentModel.class);
        user_data = new user_data();
        get_user();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    private void get_user(){

        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        Log.i("loginnnn",user.getId()+"   test");
        Call<user_model> call = services.get_users();

        call.enqueue(new Callback<user_model>() {
            @Override
            public void onResponse(Call<user_model> call, Response<user_model> response) {

                if (response.body().getSuccess().equals("1")){

                    ArrayList<StudentModel> users =new ArrayList<>();
                    users = response.body().getUsers();
                    for (int i = 0; i <users.size(); i++ ){
                        if (Objects.equals(user.getId(), users.get(i).getId())){
                            muser = users.get(i);
                        }
                    }
                    get_users_data();

                }else {
                    Toast.makeText(requireActivity(), response.body().getStatus(), LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<user_model> call, Throwable t) {
                Toast.makeText(requireActivity(), t.getMessage(), LENGTH_SHORT).show();
            }
        });
    }

    private void get_users_data(){

        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        Log.i("loginnnn",user.getId()+"   test");
        Call<users_data_model> call = services.get_user_data(muser.getDevice_id());

        call.enqueue(new Callback<users_data_model>() {
            @Override
            public void onResponse(Call<users_data_model> call, Response<users_data_model> response) {

                if (response.body().getSuccess().equals("1")){

                    ArrayList<user_data> data =new ArrayList<>();
                     data = response.body().getData();
                        if (data.size() > 0){
                            user_data = data.get(0);
                        }

                    set_data();

                }else {
                    Toast.makeText(requireActivity(), response.body().getSuccess(), LENGTH_SHORT).show();
                    set_data();
                }
            }

            @Override
            public void onFailure(Call<users_data_model> call, Throwable t) {
                Toast.makeText(requireActivity(), t.getMessage(), LENGTH_SHORT).show();
                set_data();
            }
        });
    }

    private void set_data(){


        first_nm = requireActivity().findViewById(R.id.first_name);
        last_nm = requireActivity().findViewById(R.id.last_name);
        mclass = requireActivity().findViewById(R.id.mmclass);
        section = requireActivity().findViewById(R.id.msection);
        phone = requireActivity().findViewById(R.id.mphone_number);
        height = requireActivity().findViewById(R.id.mhight);
        wight = requireActivity().findViewById(R.id.mwight);
        allergies = requireActivity().findViewById(R.id.mallergies);
        email = requireActivity().findViewById(R.id.muser_email);
        device_id = requireActivity().findViewById(R.id.mdevice_id);
        add_date = requireActivity().findViewById(R.id.madd_date);
        health = requireActivity().findViewById(R.id.mhealth_conditions);
        heart_rate = requireActivity().findViewById(R.id.mheart_rate);
        body_temp = requireActivity().findViewById(R.id.mbody_temp);
        address = requireActivity().findViewById(R.id.maddress);
        blood_type = requireActivity().findViewById(R.id.blood_type);
        age = requireActivity().findViewById(R.id.age);


        first_nm.setText(muser.getFirst_name());
        last_nm.setText(muser.getLast_name());
        address.setText(muser.getAddress());
        email.setText(muser.getUser_email());
        device_id.setText(muser.getDevice_id());
        health.setText(muser.getHealth_conditions());
        height.setText(muser.getHight());
        heart_rate.setText(user_data.getHeart_rate());
        body_temp.setText(user_data.getBody_temp());
        allergies.setText(muser.getAllergies());
        wight.setText(muser.getWight());
        mclass.setText(muser.getMclass());
        section.setText(muser.getSection());
        phone.setText(muser.getPhone_number());
        add_date.setText(muser.getAdd_Date());
        blood_type.setText(muser.getBlood_type());
        age.setText(muser.getAge());

    }
}