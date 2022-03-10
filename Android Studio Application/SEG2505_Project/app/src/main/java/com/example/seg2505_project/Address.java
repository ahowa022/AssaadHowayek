package com.example.seg2505_project;

public class Address {
    private String streetName, city;
    private int streetNumber;

    public Address() {}

    public Address(int streetNum, String streetName, String city ){
        this.streetNumber = streetNum;
        this.streetName = streetName;
        this.city = city;
    }

    public String getCity(){
        return city;
    }

    public String getStreetName(){
        return streetName;
    }

    public int getStreetNumber(){
        return streetNumber;
    }

    public void setStreetName(String street){
        streetName = street;
    }
    public void setCity(String place){
        city = place;
    }

    public void setStreetNumber(int x){
        streetNumber = x;
    }

}
