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

public class MainClinicActivity extends AppCompatActivity {
    Button viewServices;
    Button viewHours;
    TextView clinicDisplay;
    TextView displayName;
    TextView addressClinic;
    TextView phoneClinic;
    TextView cityClinic;

    Employee employee;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String id;
    DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_clinic);


        viewServices = (Button) findViewById(R.id.servicesBTN);
        viewHours = (Button) findViewById(R.id.workingHoursBTN);
        clinicDisplay =  findViewById(R.id.clinicName);
        displayName = findViewById(R.id.displayName);
        addressClinic = findViewById(R.id.addressClinic);
        phoneClinic = findViewById(R.id.phoneClinic);
        cityClinic = findViewById(R.id.cityClinic);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        id = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();

        mref = firebaseDatabase.getReference("users/"+firebaseUser.getUid());


        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employee = dataSnapshot.getValue(Employee.class);
                setDisplay();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        viewServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainClinicActivity.this, ServicesAssociatedToClinicActivity.class));
            }
        });

        viewHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainClinicActivity.this, WorkingHoursActivity.class));
            }
        });


    }

    public void setDisplay(){
        clinicDisplay.setText(employee.getClinicName());
        displayName.setText(employee.getFirstName()+" " +employee.getLastName());
        phoneClinic.setText(Long.toString(employee.getPhoneNumber()));
        addressClinic.setText(Integer.toString(employee.getAddress().getStreetNumber())+" "+
                employee.getAddress().getStreetName());
        cityClinic.setText(employee.getAddress().getCity());
    }
}
