package com.example.fillmedicalrecord.models;

import java.util.ArrayList;

public class user_model {

    public ArrayList<StudentModel> users;
    public String status;
    public String success;
    public ArrayList<StudentModel> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<StudentModel> users) {
        this.users = users;
    }

    public String getStatus() {
        return status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
