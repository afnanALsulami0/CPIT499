package com.example.fillmedicalrecord.activites;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
import com.example.fillmedicalrecord.databinding.ActivityLoginBinding;
import com.example.fillmedicalrecord.models.user_model;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity implements View.OnClickListener {
    private ActivityLoginBinding binding;
    SharedPreferences sharedPreferences;

    EditText email, password;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login Screen");
        email = findViewById(R.id.email);
        password = findViewById(R.id.signin_text_password);
        btn = findViewById(R.id.signin_btn);
        btn.setOnClickListener(this);
        sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);

    }

    private void user_login() {

        String memail = email.getText().toString().trim();
        String mpassword = password.getText().toString().trim();
        Log.i("loginnnn", memail);
        Log.i("loginnnn", mpassword);

        if (memail.equals("")) {
            email.setError("user id is required");

            email.requestFocus();
            return;
        }
        if (mpassword.equals("")) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        Log.i("loginnnn", "clicked3");
        Call<user_model> call = services.performUserLogin(memail, mpassword);

        call.enqueue(new Callback<user_model>() {
            @Override
            public void onResponse(Call<user_model> call, Response<user_model> response) {

                if (response.body().getStatus().equals("ok")) {
                    StudentModel user = response.body().getUsers().get(0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    editor.putString("user", json);
                    editor.putBoolean("is_login", true);
                    editor.apply();
                    if (user.getUser_type().equals("0")) {
                        startActivity(new Intent(login.this, home.class));
                        finish();
                    } else {
                        SharedPreferences.Editor meditor = sharedPreferences.edit();
                        meditor.putBoolean("is_login", true);
                        meditor.putBoolean("is_admin", true);
                        meditor.apply();
                        startActivity(new Intent(login.this, adminHome.class));
                        finish();
                    }
                } else {
                    Toast.makeText(login.this, response.body().getStatus(), LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<user_model> call, Throwable t) {
                Toast.makeText(login.this, t.getMessage(), LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Log.i("loginnnn", "clicked2");

        if (view.getId() == R.id.signin_btn) {
            user_login();
        }
    }


}
