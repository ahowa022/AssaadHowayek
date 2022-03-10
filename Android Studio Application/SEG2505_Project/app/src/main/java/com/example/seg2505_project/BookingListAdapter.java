package com.example.seg2505_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.List;

public class BookingListAdapter extends ArrayAdapter<Booking> {
    private Activity context;
    List<Booking> bookings;
    String name;
    FirebaseAuth firebaseAuth;
    String userId;

    public BookingListAdapter(Activity context, List<Booking> bookings){
        super(context, R.layout.activity_booking_list, bookings);
        this.context = context;
        this.bookings = bookings;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_booking_list, null,true);

        TextView clinicName = (TextView) listViewItem.findViewById(R.id.textViewClinicName); //used show username (main Item)
        TextView bookingDate = (TextView) listViewItem.findViewById(R.id.textViewClinicRating); //used show role (sub item)

        Booking booking = bookings.get(position);

        clinicName.setText(booking.getDay()); //set mainItem to firstName and lastName of user
        bookingDate.setText("");
        return listViewItem;
    }
}

