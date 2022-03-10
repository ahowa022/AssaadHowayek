package com.example.seg2505_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewBookings extends AppCompatActivity {

    ListView bookingList;
    DatabaseReference mref;
    List<Booking> bookings;
    FirebaseAuth firebaseAuth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings);

        bookingList = (ListView) findViewById(R.id.bookingsList);
        bookings = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        mref = FirebaseDatabase.getInstance().getReference("users/" + userId+"/bookings");

        //When one long clicks on a user it comes into this method

    }

    @Override
    protected void onStart(){
        super.onStart();
        //loops through all users in the database and add them to the arraylist of users
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookings.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Booking booking = postSnapshot.getValue(Booking.class);
                    bookings.add(booking);
                }
                BookingListAdapter bookingAdapter = new BookingListAdapter(ViewBookings.this, bookings);//creates adapter for UserListAdapter view
                bookingList.setAdapter(bookingAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

