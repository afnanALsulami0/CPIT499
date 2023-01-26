package com.example.fillmedicalrecord.models;

import java.util.ArrayList;

public class users_data_model {

    ArrayList<user_data> data;
    String success;

    public ArrayList<user_data> getData() {
        return data;
    }

    public void setData(ArrayList<user_data> data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}

