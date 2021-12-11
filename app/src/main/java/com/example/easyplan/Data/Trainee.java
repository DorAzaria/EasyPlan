package com.example.easyplan.Data;

import java.util.ArrayList;
import java.util.Vector;

public class Trainee {

    private String name, address, height, weight, gender, type, notifications, plan_status;
    private int age;

    public Trainee() {}

    public Trainee(String name, String address, String height, String weight, String gender, String type, String notifications, String plan_status, int age) {
        this.name = name;
        this.address = address;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.type = type;
        this.notifications = notifications;
        this.plan_status = plan_status;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

    public String getPlan_status() {
        return plan_status;
    }

    public void setPlan_status(String plan_status) {
        this.plan_status = plan_status;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
