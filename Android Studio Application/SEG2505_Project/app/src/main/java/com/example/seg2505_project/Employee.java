package com.example.seg2505_project;

import java.util.ArrayList;
import java.util.List;

public class Employee extends User {
    private String clinicName;
    private List<String>  paymentMehods;
    private List<Availability>  availabilities;
    private Address address;
    private long phoneNumber;
    private List<String> services;
    private List<Rating> ratings;

    public Employee(){
        availabilities = new ArrayList<Availability>();
        paymentMehods = new ArrayList<String>();
        services = new ArrayList<String>();
        ratings = new ArrayList<Rating>();
    }

    public Employee(String email, String firstName, String lastName, String role, String clinicName, String id){
        super(email,firstName,lastName, role, id);
        this.clinicName = clinicName;
    }


    public String getClinicName(){
        return clinicName;
    }

    public List<String> getPaymentMehods(){
        return paymentMehods;
    }

    public void setPaymentMethods(List<String> payment) {
        this.paymentMehods = payment;
    }

    public void createPaymentMethods(){
        paymentMehods = new ArrayList<String>();
    }

    public List<String> getServices(){
        return services;
    }

    public void setServices(List<String> newServices) {
        this.services = newServices;
    }

    public void createServices(){
        services = new ArrayList<String>();
    }

    public List<Availability> getAvailabilities(){return availabilities;}

    public void setAvailabilities(List<Availability> newList){availabilities=newList;}

    public void createAvailabilities(){availabilities = new ArrayList<Availability>();}

    public Address getAddress(){
        return address;
    }

    public void setAddress(Address change){
        address = change;
    }

    public void setPhoneNumber(long x){
        phoneNumber = x;
    }

    public long getPhoneNumber(){
        return phoneNumber;
    }

    public void addService(String service){
        if(services == null) {
            services = new ArrayList<String>();
        }
        this.services.add(service);
    }

    public void setRatings(List<Rating> newRatings){
        ratings = newRatings;
    }

    public List<Rating> getRatings(){
        return ratings;
    }

    public void createRatings(){
        ratings = new ArrayList<Rating>();
    }
}
