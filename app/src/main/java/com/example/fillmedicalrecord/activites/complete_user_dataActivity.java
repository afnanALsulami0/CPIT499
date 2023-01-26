package com.example.fillmedicalrecord.activites;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fillmedicalrecord.R;
import com.example.fillmedicalrecord.models.StudentModel;
import com.example.fillmedicalrecord.database.ApiClient;
import com.example.fillmedicalrecord.database.ApiInterface;
import com.example.fillmedicalrecord.models.user_model;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class complete_user_dataActivity extends AppCompatActivity implements View.OnClickListener {

    EditText phone,blood_type,height,wight,health_conditions,allergies;
    Button btn;
    SharedPreferences sharedPreferences;
    StudentModel user;
    StudentModel muser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complet_user_data);
        sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        setTitle("Complete information Screen");
        Gson gson = new Gson();
        String json = sharedPreferences.getString("user", "");
        user = gson.fromJson(json, StudentModel.class);
        get_user();
        setUpToolBar();
        phone = findViewById(R.id.phone_number_ed);
        blood_type = findViewById(R.id.blood_type_ed);
        height = findViewById(R.id.height_ed);
        wight = findViewById(R.id.wight_ed);
        health_conditions = findViewById(R.id.health_conditions_ed);
        allergies = findViewById(R.id.allergies_ed);
        btn = findViewById(R.id.next_btn);
        btn.setOnClickListener(this);

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
    private void complete_user_data(){

        String mphone = phone.getText().toString();
        String mblood_type = blood_type.getText().toString();
        String mheight = height.getText().toString();
        String mhealth_conditions = health_conditions.getText().toString();
        String mallergies = allergies.getText().toString();
        String mwight = wight.getText().toString();

        if (mphone.equals("")){
            Toast.makeText(this, "please enter phone", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mblood_type.equals("")){
            Toast.makeText(this, "please enter blood type", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mheight.equals("")){
            Toast.makeText(this, "please enter height", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mhealth_conditions.equals("")){
            Toast.makeText(this, "please enter health conditions", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mallergies.equals("")){
            Toast.makeText(this, "please enter allergies", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mwight.equals("")){
            Toast.makeText(this, "please enter wight", Toast.LENGTH_SHORT).show();
            return;
        }


        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        Log.i("loginnnn",user.getId()+"   test");
        Call<user_model> call = services.complete_user_data(user.getId(),mblood_type,mphone,mheight,mwight,mallergies,mhealth_conditions);

        call.enqueue(new Callback<user_model>() {
            @Override
            public void onResponse(Call<user_model> call, Response<user_model> response) {

                if (response.body().getSuccess().equals("1")){
                    Toast.makeText(complete_user_dataActivity.this,"updated successfully", LENGTH_SHORT).show();

                }else {
                    Toast.makeText(complete_user_dataActivity.this, response.body().getStatus(), LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<user_model> call, Throwable t) {
                Toast.makeText(complete_user_dataActivity.this, t.getMessage(), LENGTH_SHORT).show();
            }
        });

    }
    private void setdata(){

        phone.setText(muser.getPhone_number());
        blood_type.setText(muser.getBlood_type());
        height.setText(muser.getHight());
        wight.setText(muser.getWight());
        health_conditions.setText(muser.getHealth_conditions());
        allergies.setText(muser.getAllergies());
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
                    setdata();

                }else {
                    Toast.makeText(complete_user_dataActivity.this, response.body().getStatus(), LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<user_model> call, Throwable t) {
                Toast.makeText(complete_user_dataActivity.this, t.getMessage(), LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.next_btn) {
            complete_user_data();
        }
    }
}