package com.example.easyplan.Model;

import java.util.List;
import java.util.Map;

public class Trainer {

    private String name, address, gender, type, education, personal_page, notifications, token, phone_number, email;
    private int age, cost, duration, counter, total;
    private double rate;
    private List<String> targets;
    private Map<String, String> my_trainees;

    public Trainer() {}

    public Trainer(String name, String address, String gender, String type, String education, String personal_page, String notifications, int age, int cost, int duration, double rate, List<String> targets, Map<String, String> my_trainees, String token, String phone_number, String email, int count, int total) {
        this.name = name;
        this.counter = count;
        this.total = total;
        this.address = address;
        this.gender = gender;
        this.type = type;
        this.education = education;
        this.personal_page = personal_page;
        this.notifications = notifications;
        this.age = age;
        this.cost = cost;
        this.duration = duration;
        this.rate = rate;
        this.targets = targets;
        this.my_trainees = my_trainees;
        this.token = token;
        this.phone_number = phone_number;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPersonal_page() {
        return personal_page;
    }

    public void setPersonal_page(String personal_page) {
        this.personal_page = personal_page;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public List<String> getTargets() {
        return targets;
    }

    public void setTargets(List<String> targets) {
        this.targets = targets;
    }

    public Map<String, String> getMy_trainees() {
        return my_trainees;
    }

    public void setMy_trainees(Map<String, String> my_trainees) {
        this.my_trainees = my_trainees;
    }
}
