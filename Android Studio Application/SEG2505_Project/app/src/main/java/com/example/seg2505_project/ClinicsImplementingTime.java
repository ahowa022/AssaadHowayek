package com.example.seg2505_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClinicsImplementingTime extends AppCompatActivity {
    ListView employeeList;
    DatabaseReference mref;
    Bundle extras;
    int firstTwo;
    int lastTwo;
    int dayInDb;
    List<Employee> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinics_implementing_time);

        employeeList = (ListView) findViewById(R.id.clinicsImplementingTimeList);
        employees = new ArrayList<>();
        mref = FirebaseDatabase.getInstance().getReference("users/");

        extras = getIntent().getExtras();
        firstTwo = Integer.parseInt(extras.getString("hour").substring(0,2));
        lastTwo = Integer.parseInt(extras.getString("hour").substring(3,5));
        dayInDb = findDayInt(extras.getString("day"));

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
                    if(employee.getAvailabilities()!=null && employee.getAvailabilities().size()!=0) {
                        if(parseStartHour(employee.getAvailabilities().get(dayInDb).getStartTime(), employee.getAvailabilities().get(dayInDb).getEndTime())) {
                            employees.add(employee); //remodify list that is used in listview to display services
                        }
                    }
                }
                RatingListAdapter employeeAdapter = new RatingListAdapter(ClinicsImplementingTime.this, employees);
                //call service adapter for listView to display the services
                employeeList.setAdapter(employeeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void showBookingActivity(Employee employee){
        Intent intent = new Intent(ClinicsImplementingTime.this , BookAppointment.class);
        intent.putExtra("id", employee.getId());
        this.startActivity(intent);
    }

    public Boolean parseStartHour(String startTime, String endTime){
        int startTimeFirst = Integer.parseInt(startTime.substring(0,2));
        int startTimeLast = Integer.parseInt(startTime.substring(3,5));
        int endTimeFirst = Integer.parseInt(endTime.substring(0,2));
        int endTimeLast = Integer.parseInt(endTime.substring(3,5));

        if(endTimeFirst>startTimeFirst){
            if(firstTwo>startTimeFirst && firstTwo<endTimeFirst){  //if hours in between
                return true;
            }
            if(firstTwo==startTimeFirst&&lastTwo>=startTimeLast){ //if hours same check if minutes bigger than start time
                return true;
            }
            if(firstTwo==endTimeFirst&&lastTwo<=endTimeLast){ //if hour same as end time check if minutes smaller end time
                return true;
            }
        }

        if(startTimeFirst>endTimeFirst){ //startTime hours bigger end time hours 23:00 - 8:00
            if(firstTwo>startTimeFirst || firstTwo<endTimeFirst){
                return true;
            }
            if(firstTwo==startTimeFirst && lastTwo>=startTimeLast){
                return true;
            }

            if(firstTwo==endTimeFirst && lastTwo<=endTimeLast){
                return true;
            }
        }

        //first two are main scenarios

        if(startTimeFirst==endTimeFirst && endTimeLast>startTimeLast){ //clinic open for like 25 mins scenario
            if(firstTwo==startTimeFirst &&lastTwo>=startTimeLast && lastTwo<=endTimeLast){
                return true;
            }
        }

        if(startTimeFirst==endTimeFirst && endTimeLast<startTimeLast){ //almost 24 hour scenario
            if(firstTwo==startTimeFirst && lastTwo>endTimeLast && lastTwo<startTimeLast){
                return false;
            } else {
                return true;
            }
        }

        return false; // will never get to this scenario

    }



    public int findDayInt(String day) {
        if(day.equals("Monday")) {
            return 0;
        }
        if(day.equals("Tuesday")) {
            return 1;
        }
        if(day.equals("Wednesday")) {
            return 2;
        }
        if(day.equals("Thursday")) {
            return 3;
        }
        if(day.equals("Friday")) {
            return 4;
        }
        if(day.equals("Saturday")) {
            return 5;
        }
        if(day.equals("Sunday")) {
            return 6;
        }
        return 1000;
    }
}