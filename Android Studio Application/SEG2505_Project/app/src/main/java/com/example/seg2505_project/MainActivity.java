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

public class MainActivity extends AppCompatActivity {

    TextView displayName;
    TextView displayRole;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    User user;
    Button buttonContinue;
    Employee employee;
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayName = findViewById(R.id.displayName);
        displayRole = findViewById(R.id.displayRole);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users/"+firebaseUser.getUid());
        buttonContinue = (Button) findViewById(R.id.continueBTN);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                displayName.setText(user.getFirstName()+" "+user.getLastName());
                displayRole.setText(user.getRole());
                if(user.getRole().equals("Employee")){
                    employee = dataSnapshot.getValue(Employee.class);
                }
                if(user.getRole().equals("Patient")){
                    patient = dataSnapshot.getValue(Patient.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getRole().equals("Admin")){
                    startActivity(new Intent(MainActivity.this, MainAdminActivity.class));
                }
                if (user.getRole().equals("Employee")){
                    if(employee.getAddress()==null){
                        startActivity(new Intent(MainActivity.this, ClinicCompleteProfileActivity.class));
                    } else {
                        Intent intent = new Intent(MainActivity.this, MainClinicActivity.class);
                        startActivity(intent);
                    }
                }

                if(user.getRole().equals("Patient")){
                    Intent intent = new Intent(MainActivity.this, MainPatientActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}

