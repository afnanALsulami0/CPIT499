package com.example.fillmedicalrecord.database;


import com.example.fillmedicalrecord.models.attendance_model;
import com.example.fillmedicalrecord.models.user_model;
import com.example.fillmedicalrecord.models.users_data_model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("API/user_login.php")
    Call<user_model> performUserLogin(@Query("user_email") String username, @Query("user_password") String password);


    @GET("API/user_register.php")
    Call<user_model> add_student(@Query("first_name") String first_name,
                                 @Query("last_name") String last_name,
                                 @Query("password") String password,
                                 @Query("address") String address,
                                 @Query("email") String email,
                                 @Query("mclass") String mclass,
                                 @Query("section") String section,
                                 @Query("device_id") String device_id,
                                 @Query("age") String age,
                                 @Query("user_id") String user_id


    );

    @GET("API/complet_user_data.php")
    Call<user_model> complete_user_data(@Query("user_id") String user_id,
                                 @Query("blood_type") String blood_type,
                                 @Query("phone") String phone,
                                 @Query("height") String height,
                                 @Query("wight") String wight,
                                 @Query("allergies") String allergies,
                                 @Query("health_conditions") String health_conditions

    );

    @GET("API/select_users.php")
    Call<user_model> get_users();

    @GET("API/select_all_user_data.php")
    Call<users_data_model> get_users_data();

    @GET("API/select_user_data.php")
    Call<users_data_model> get_user_data(@Query("device_id") String device_id);

    @GET("API/select_user_attendance.php")
    Call<attendance_model> get_users_attendance(@Query("device_id") String device_id);

}




