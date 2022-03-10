package com.example.seg2505_project;

import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.List;

public class Service {
    // Variables
    private String name;
    private String role;
    private String id;
    private List<String> clinics;

    public Service() {
        clinics = new ArrayList<String>();
    }

    public Service(String name, String role, String id) {
        this.name = name;
        this.role = role;
        this.id =id;
    }


    // Getters
    public String getName(){
        return this.name;
    }

    public String getId(){
        return this.id;
    }

    public String getRole(){return this.role;}

    public List<String> getClinics(){
        return this.clinics;
    }
    public void setClinics(List<String> newList){clinics=newList;}
    // Setters
    public void setName(String newName) {
        name = newName;
    }
    public void setRole(String newRole) {
        role = newRole;
    }

    public void addClinic(String clinic){
        if(clinics == null) {
            clinics = new ArrayList<String>();
        }
        this.clinics.add(clinic);
    }

    public void createClinics(){
        clinics = new ArrayList<String>();
    }

}
