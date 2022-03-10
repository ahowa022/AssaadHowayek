package com.example.seg2505_project;

public class Availability {
    private String day;
    private String startTime;
    private String endTime;

    public Availability(String day, String startTime, String endTime){
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Availability(){
    }

    public String getDay(){
        return day;
    }

    public String getStartTime(){
        return startTime;
    }

    public void setStartTime(String newTime){
        startTime = newTime;
    }

    public String getEndTime(){
        return endTime;
    }

    public void setEndTime(String newTime){
        endTime = newTime;
    }

    public void setDay(String newDay){
        day = newDay;
    }

}
