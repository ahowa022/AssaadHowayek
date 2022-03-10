package com.example.seg2505_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainPatientActivity extends AppCompatActivity {
    TextView patientName;

    DatabaseReference bookingsRef;
    Patient patient;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference patientRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_patient);

        // Variables for buttons
        Button rating = (Button) findViewById(R.id.viewRatings);
        Button viewBookings = (Button) findViewById(R.id.viewBookings);
        Button search = (Button) findViewById(R.id.searchAndBook);
        patientName = findViewById(R.id.patientName);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        patientRef = FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid());

        patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patient = dataSnapshot.getValue(Patient.class);
                patientName.setText(patient.getFirstName()+" "+patient.getLastName());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPatientActivity.this, RateClinic.class));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPatientActivity.this, SearchClinic.class));
            }
        });

        viewBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPatientActivity.this, ViewBookings.class));
            }
        });
    }
}
