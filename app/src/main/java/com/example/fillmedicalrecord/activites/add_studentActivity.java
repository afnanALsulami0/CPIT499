package com.example.fillmedicalrecord.activites;


import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.fillmedicalrecord.R;
import com.example.fillmedicalrecord.database.ApiClient;
import com.example.fillmedicalrecord.database.ApiInterface;
import com.example.fillmedicalrecord.models.user_model;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_studentActivity extends AppCompatActivity implements View.OnClickListener{
    Spinner spinnerClass,spinnerSections;
    String section,mclass;
    EditText firstname,last_name,address,email,password,device_id,age,user_id;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        setTitle("Add Student");
        firstname = findViewById(R.id.first_name_ed);
        last_name = findViewById(R.id.last_name);
        address = findViewById(R.id.address_ed_add);
        password = findViewById(R.id.password_ed);
        device_id = findViewById(R.id.device_id_ed);
        email = findViewById(R.id.user_email_ed);
        age = findViewById(R.id.age_ed);
        user_id = findViewById(R.id.user_id_ed);
        btn = findViewById(R.id.add_btn);

        setUpToolBar();

        spinnerClass=findViewById(R.id.spinnerClass);
        spinnerSections =findViewById(R.id.spinnerSection);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(add_studentActivity.this, R.layout.spinner,getResources().getStringArray(R.array.mclasses));
        adapter.setDropDownViewResource(R.layout.spinner);

        spinnerClass.setAdapter(adapter);
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value=adapterView.getItemAtPosition(i).toString();
                mclass = value;
                Toast.makeText(add_studentActivity.this,value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> madapter = new ArrayAdapter<String>(add_studentActivity.this, R.layout.spinner,getResources().getStringArray(R.array.sections));
        adapter.setDropDownViewResource(R.layout.spinner);

        spinnerSections.setAdapter(madapter);
        spinnerSections.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value=adapterView.getItemAtPosition(i).toString();
                section = value;
                Toast.makeText(add_studentActivity.this,value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

    private void add_student(){

        String first_nm = firstname.getText().toString();
        String last_nm = last_name.getText().toString();
        String maddress = address.getText().toString();
        String mpassword = password.getText().toString();
        String memail = email.getText().toString();
        String mdevice_id = device_id.getText().toString();
        String mage = age.getText().toString();
        String muser_id = user_id.getText().toString();

        if (section.equals("Sections") || mclass.equals("Classes")){
            Toast.makeText(this, "please choose class and section", Toast.LENGTH_SHORT).show();
            return;
        }

        if (first_nm.equals("")){
            Toast.makeText(this, "please enter first name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (last_nm.equals("")){
            Toast.makeText(this, "please enter last name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mpassword.equals("")){
            Toast.makeText(this, "please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (memail.equals("")){
            Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (muser_id.equals("")){
            Toast.makeText(this, "please enter user id", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mdevice_id.equals("")){
            Toast.makeText(this, "please enter device_id", Toast.LENGTH_SHORT).show();
            return;
        }
        if (maddress.equals("")){
            Toast.makeText(this, "please enter address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mage.equals("")){
            Toast.makeText(this, "please enter age", Toast.LENGTH_SHORT).show();
            return;
        }
        // call script add user
        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        Log.i("loginnnn","clicked3");
        Call<user_model> call = services.add_student(first_nm,last_nm,mpassword,maddress,memail,mclass,section,mdevice_id,mage,muser_id);
//
        call.enqueue(new Callback<user_model>() {
            @Override
            public void onResponse(Call<user_model> call, Response<user_model> response) {

                if (Objects.equals(response.body().getSuccess(), "1")){
                    finish();
                }else {
                    // startActivity(new Intent(add_studentActivity.this,add_studentActivity.class));
                    Toast.makeText(add_studentActivity.this, response.body().getStatus(), LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<user_model> call, Throwable t) {
                Toast.makeText(add_studentActivity.this, t.getMessage(), LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_btn) {
            add_student();
        }
    }
}