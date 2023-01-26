package com.example.fillmedicalrecord.activites;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fillmedicalrecord.R;
import com.example.fillmedicalrecord.models.StudentModel;
import com.example.fillmedicalrecord.adapters.admin_home_adapter;
import com.example.fillmedicalrecord.database.ApiClient;
import com.example.fillmedicalrecord.database.ApiInterface;
import com.example.fillmedicalrecord.databinding.AdminHomeBinding;
import com.example.fillmedicalrecord.interfaces.ItemHomeListener;
import com.example.fillmedicalrecord.models.user_data;
import com.example.fillmedicalrecord.models.user_model;
import com.example.fillmedicalrecord.models.users_data_model;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adminHome extends AppCompatActivity implements ItemHomeListener {
    AdminHomeBinding binding;

    Spinner spinnerClass, spinnerSections;
    String section, mclass;
    ArrayList<StudentModel> users;
    ArrayList<user_data> data;
    Boolean firststart = false;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.admin_home);
        binding = DataBindingUtil.setContentView(this, R.layout.admin_home);
        sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        setTitle("Admin screen");
        section ="";
        mclass = "";
        spinnerClass = binding.spinnerClass;
        spinnerSections = binding.spinnerSection;

        binding.addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(adminHome.this,add_studentActivity.class));
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(adminHome.this, R.layout.spinner, getResources().getStringArray(R.array.mclasses));
        adapter.setDropDownViewResource(R.layout.spinner);

        spinnerClass.setAdapter(adapter);
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapterView.getItemAtPosition(i).toString();

                if (i == 0){
                    mclass = "Classes";
                }else {
                    mclass = value;
                }
                if (firststart){
                    search();
                }

                Toast.makeText(adminHome.this, value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> madapter = new ArrayAdapter<String>(adminHome.this, R.layout.spinner, getResources().getStringArray(R.array.sections));
        adapter.setDropDownViewResource(R.layout.spinner);

        spinnerSections.setAdapter(madapter);
        spinnerSections.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapterView.getItemAtPosition(i).toString();
                if (i == 0){
                    section = "Sections";
                }else {
                    section = value;
                }
                if (firststart){
                    search();
                }
                Toast.makeText(adminHome.this, value, Toast.LENGTH_SHORT).show();
                firststart = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        get_user();
    }

    private void search(){
        ArrayList<StudentModel> list = new ArrayList<>();
        list.clear();
        Log.i("tessst",section);
        Log.i("tessst",mclass);
        if (!Objects.equals(section, "Sections") && !Objects.equals(mclass, "Classes")){
            Log.i("tessst","entred");
            for (int i = 0; i < users.size(); i++){
                Log.i("tessst",users.get(i).getSection());
                Log.i("tessst",users.get(i).getMclass());
                if (users.get(i).getSection().equals(section) && users.get(i).getMclass().equals(mclass)){

                    list.add(users.get(i));
                }
            }
            setrec(list);
        }else {
            setrec(users);
        }
    }

    protected void setrec(ArrayList<StudentModel> list) {

        admin_home_adapter itemHomeAdapter = new admin_home_adapter(list, this, this,data);

        LinearLayoutManager manager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        binding.rec.setLayoutManager(manager1);

        binding.rec.setAdapter(itemHomeAdapter);

    }

    private void get_user() {

        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        Call<user_model> call = services.get_users();

        call.enqueue(new Callback<user_model>() {
            @Override
            public void onResponse(Call<user_model> call, Response<user_model> response) {

                if (response.body().getSuccess().equals("1")) {
                    users = new ArrayList<>();
                    for (int i = 0; i < response.body().getUsers().size(); i++){
                        if (!Objects.equals(response.body().getUsers().get(i).getUser_type(), "1")){
                            users.add(response.body().getUsers().get(i));
                        }
                    }
                 //   users = response.body().getUsers();

                    get_users_data();

                } else {
                    Toast.makeText(adminHome.this, response.body().getStatus(), LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<user_model> call, Throwable t) {
                Toast.makeText(adminHome.this, t.getMessage(), LENGTH_SHORT).show();
            }
        });
    }

    private void get_users_data() {

        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        Call<users_data_model> call = services.get_users_data();

        call.enqueue(new Callback<users_data_model>() {
            @Override
            public void onResponse(Call<users_data_model> call, Response<users_data_model> response) {

                if (response.body().getSuccess().equals("1")) {

                     data = new ArrayList<>();
                    data = response.body().getData();

                } else {
                    Toast.makeText(adminHome.this, response.body().getSuccess(), LENGTH_SHORT).show();
                }
                setrec(users);
            }

            @Override
            public void onFailure(Call<users_data_model> call, Throwable t) {
                Toast.makeText(adminHome.this, t.getMessage(), LENGTH_SHORT).show();
                setrec(users);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

      switch (item.getItemId()){

          case R.id.logout:
               sharedPreferences.edit().clear().apply();
              startActivity(new Intent(adminHome.this,SplashActivity.class));
              finish();

      }

        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onItemHomeClick(int position) {

        Intent intent = new Intent(adminHome.this,attendanceActivity.class);
        intent.putExtra("id",users.get(position).getDevice_id());
        startActivity(intent);
    }
}

