package com.example.seg2505_project;

import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
    private List<Booking> bookings;

    public Patient(){
        bookings = new ArrayList<Booking>();
    }
    public Patient(String email, String firstName, String lastName, String role, String id){
        super(email,firstName,lastName, role, id);
    }

    public List<Booking> getBookings(){
        return bookings;
    }

    public void setBookings(List<Booking> bookings){
        this.bookings = bookings;
    }
}
