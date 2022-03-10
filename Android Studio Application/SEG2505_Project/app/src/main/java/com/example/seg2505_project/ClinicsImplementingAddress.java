package com.example.seg2505_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClinicsImplementingAddress extends AppCompatActivity
{
    ListView employeeList;
    DatabaseReference mref;
    Bundle extras;
    List<Employee> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinics_implementing_address);

        employeeList = (ListView) findViewById(R.id.clinicsImplementingAddressList);
        employees = new ArrayList<>();
        mref = FirebaseDatabase.getInstance().getReference("users/");

        extras = getIntent().getExtras();

        employeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Employee employee = employees.get(position); //get the service at specific index
                showBookingActivity(employee); //function called when wanting to update/delete service
            }
        });
    }


    @Override
    protected void onStart(){ //method called on the start of the activity
        super.onStart();
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employees.clear(); //clear whole list that was present since maybe changes were made in popup
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){ //look though database
                    Employee employee  = postSnapshot.getValue(Employee.class);
                    if(employee.getAddress() != null) {
                        if (employee.getAddress().getStreetName().contains(extras.getString("address")) || employee.getClinicName().contains(extras.getString("address"))) {
                            employees.add(employee); //remodify list that is used in listview to display services
                        }
                    }
                }
                RatingListAdapter employeeAdapter = new RatingListAdapter(ClinicsImplementingAddress.this, employees);
                //call service adapter for listView to display the services
                employeeList.setAdapter(employeeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void showBookingActivity(Employee employee){
        Intent intent = new Intent(ClinicsImplementingAddress.this , BookAppointment.class);
        intent.putExtra("id", employee.getId());
        this.startActivity(intent);
    }
}