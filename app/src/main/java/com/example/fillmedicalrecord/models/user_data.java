package com.example.fillmedicalrecord.models;

public class user_data {

    String wristband_id ="";
    String longitude ="";
    String latitude ="";
    String heart_rate ="NA";
    String body_temp ="NA";

    public String getWristband_id() {
        return wristband_id;
    }

    public void setWristband_id(String wristband_id) {
        this.wristband_id = wristband_id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(String heart_rate) {
        this.heart_rate = heart_rate;
    }

    public String getBody_temp() {
        return body_temp;
    }

    public void setBody_temp(String body_temp) {
        this.body_temp = body_temp;
    }
}
