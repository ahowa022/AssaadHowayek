package com.example.seg2505_project;

public class Booking {

    private String day, id;

    public Booking(){

    }

    public Booking(String day, String id){
        this.day = day;
        this.id = id;
    }

    public String getDay(){
        return day;
    }

    public String getId(){
        return id;
    }

    public void setDay(String day){
        this.day = day;
    }

    public void setId(String id){
        this.id = id;
    }
}
