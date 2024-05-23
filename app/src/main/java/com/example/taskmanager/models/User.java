package com.example.taskmanager.models;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String firts_name;
    private String last_name;
    private String email;
    private String gender;
    private String phone;

    public User(){
    }

    public User(String userId, String firts_name, String last_name, String email, String gender, String phone) {
        this.userId = userId;
        this.firts_name = firts_name;
        this.last_name = last_name;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirts_name() {
        return firts_name;
    }

    public void setFirts_name(String firts_name) {
        this.firts_name = firts_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
