package com.example.easyplan.Data;

import java.util.Vector;

public class Trainee {

    private String name, address, height, weight, gender, type;
    private int age;
    private Vector<String> targets;

    public Trainee(String name, String address, String height, String weight, String gender, int age) {
        this.name = name;
        this.address = address;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.age = age;
        this.type = "Trainee";
        this.targets = new Vector<>();
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Vector<String> getTargets() {
        return targets;
    }

    public void setTargets(Vector<String> targets) {
        this.targets = targets;
    }
}
