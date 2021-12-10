package com.example.easyplan.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Trainer {

    private String name, address, gender, type, education, personal_page;
    private int age, cost, duration;
    private double rate;
    private ArrayList<String> targets;
    private HashMap<String, String> my_trainees;

    public Trainer() {}

    public Trainer(String name, String address, String gender, String education, String personal_page, int age, int cost, ArrayList<String> targets , HashMap<String, String> my_trainees) {
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.education = education;
        this.personal_page = personal_page;
        this.age = age;
        this.cost = cost;
        this.targets = targets;
        this.my_trainees = my_trainees;
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

    public ArrayList<String> getTargets() {
        return targets;
    }

    public void setTargets(ArrayList<String> targets) {
        this.targets = targets;
    }

    public HashMap<String, String> getTrainees() {
        return my_trainees;
    }

    public void setTrainees(HashMap<String, String> trainees) {
        this.my_trainees = trainees;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", type='" + type + '\'' +
                ", education='" + education + '\'' +
                ", personal_page='" + personal_page + '\'' +
                ", age=" + age +
                ", cost=" + cost +
                ", duration=" + duration +
                ", rate=" + rate +
                ", targets=" + targets +
                ", my_trainees=" + my_trainees +
                '}';
    }
}
