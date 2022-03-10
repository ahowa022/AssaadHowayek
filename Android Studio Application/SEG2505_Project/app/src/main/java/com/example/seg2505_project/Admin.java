package com.example.seg2505_project;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {
    private List<Service> services;
    public Admin(){
        services = new ArrayList<Service>();
    }
    public Admin(String email, String firstName, String lastName, String role, String id){
        super(email,firstName,lastName, role, id);
    }
}

