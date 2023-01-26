package com.example.fillmedicalrecord.models;

public class StudentModel {
    private String id="";
    private String first_name="";
    private String last_name="";
    private String address="";
    private String phone_number="";
    private String user_email="";
    private String mclass="";
    private String age="";
    private String section="";
    private String device_id="";
    private String blood_type="";
    private String hight="";
    private String wight="";
    private String health_conditions="";
    private String user_type="";
    private String allergies="";
    private String add_Date="";

    public StudentModel() {
    }


    public StudentModel(String fnameStudent, String lnameStudent, String studentID, String studentAge, String studentclass, String studentSection, String studentPhone, String studentAddress,
                        String studentAllergy, String studentBloodType, String healthConditions, String studentHeight, String studentWeight,String user_type,String user_email, String device_id,String add_Date) {
        this.first_name = fnameStudent;
        this.last_name = lnameStudent;
        this.id = studentID;
        this.age = studentAge;
        this.mclass = studentclass;
        this.section = studentSection;
        this.phone_number = studentPhone;
        this.address = studentAddress;
        this.allergies = studentAllergy;
        this.blood_type = studentBloodType;
        this.health_conditions = healthConditions;
        this.hight = studentHeight;
        this.wight = studentWeight;
        this.user_type = user_type;
        this.user_email = user_email;
        this.device_id = device_id;
        this.add_Date = add_Date;
    }

    public String getAdd_Date() {
        return add_Date;
    }

    public void setAdd_Date(String add_Date) {
        this.add_Date = add_Date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getMclass() {
        return mclass;
    }

    public void setMclass(String mclass) {
        this.mclass = mclass;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public String getHight() {
        return hight;
    }

    public void setHight(String hight) {
        this.hight = hight;
    }

    public String getWight() {
        return wight;
    }

    public void setWight(String wight) {
        this.wight = wight;
    }

    public String getHealth_conditions() {
        return health_conditions;
    }

    public void setHealth_conditions(String health_conditions) {
        this.health_conditions = health_conditions;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }
}
