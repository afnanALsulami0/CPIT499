package com.example.fillmedicalrecord.models;

import java.util.ArrayList;

public class attendance_model {

  ArrayList<user_attendance> data;
  String success ;

    public ArrayList<user_attendance> getData() {
        return data;
    }

    public void setData(ArrayList<user_attendance> data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
