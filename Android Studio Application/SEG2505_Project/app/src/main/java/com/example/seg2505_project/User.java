package com.example.seg2505_project;



public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String id;

    public User(){
    }

    public User (String email, String firstName, String lastName, String role, String id) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.id = id;
    }

    public String getEmail(){
        return email;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getRole(){
        return role;
    }

    public String getId(){ return id;}
}

