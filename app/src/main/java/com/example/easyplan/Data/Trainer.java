package com.example.easyplan.Data;

import java.util.Vector;

public class Trainer {

    private String name, address, gender, type, education, personal_page;
    private int age, cost, days, duration;
    private double rate;
    private Vector<String> targets, trainees;

    public Trainer(String name, String address, String gender, String type, String education, String personal_page, int age, int cost, int days, int duration, double rate, Vector<String> targets) {
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.education = education;
        this.personal_page = personal_page;
        this.age = age;
        this.cost = cost;
        this.days = days;
        this.duration = duration;
        this.rate = rate;
        this.targets = targets;
        this.trainees = new Vector<>();
        this.type = "Trainer";
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

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
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

    public Vector<String> getTargets() {
        return targets;
    }

    public void setTargets(Vector<String> targets) {
        this.targets = targets;
    }

    public Vector<String> getTrainees() {
        return trainees;
    }

    public void setTrainees(Vector<String> trainees) {
        this.trainees = trainees;
    }


}
