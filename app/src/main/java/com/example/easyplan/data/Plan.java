package com.example.easyplan.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

public class Plan {

    private String trainer_id, trainee_id, date;
    private HashMap<String, HashMap<String,String>> trains; // <1, <time1, 1 hour>, <excersize, workout>>, <2, <time2, excer2>>, ...
    private Vector<String> menu;

    public Plan(String trainer_id, String trainee_id, HashMap<String, HashMap<String,String>> t, Vector<String> m) {
        this.trainer_id = trainer_id;
        this.trainee_id = trainee_id;
        this.trains = t;
        this.menu = m;

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
        String strDate = dateFormat.format(date);
        this.date = strDate;
    }

    public String getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(String trainer_id) {
        this.trainer_id = trainer_id;
    }

    public String getTrainee_id() {
        return trainee_id;
    }

    public void setTrainee_id(String trainee_id) {
        this.trainee_id = trainee_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HashMap<String, HashMap<String, String>> getTrains() {
        return trains;
    }

    public void setTrains(HashMap<String, HashMap<String, String>> trains) {
        this.trains = trains;
    }

    public Vector<String> getMenu() {
        return menu;
    }

    public void setMenu(Vector<String> menu) {
        this.menu = menu;
    }
}