package com.example.seg2505_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WorkingHoursActivity extends AppCompatActivity {
    EditText mondayStart;
    EditText mondayEnd;
    EditText tuesdayStart;
    EditText tuesdayEnd;
    EditText wednesdayStart;
    EditText wednesdayEnd;
    EditText thursdayStart;
    EditText thursdayEnd;
    EditText fridayStart;
    EditText fridayEnd;
    EditText saturdayStart;
    EditText saturdayEnd;
    EditText sundayStart;
    EditText sundayEnd;
    TextView errorOutput;
    Button buttonConfirm;
    Button buttonDiscard;
    Employee employee;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String id;
    DatabaseReference mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_hours);


        mondayStart = findViewById(R.id.startMonday);
        mondayEnd = findViewById(R.id.endMonday);
        tuesdayStart = findViewById(R.id.startTuesday);
        tuesdayEnd = findViewById(R.id.endTuesday);
        wednesdayStart = findViewById(R.id.startWednesday);
        wednesdayEnd = findViewById(R.id.endWednesday);
        thursdayStart = findViewById(R.id.startThursday);
        thursdayEnd = findViewById(R.id.endThursday);
        fridayStart = findViewById(R.id.startFriday);
        fridayEnd = findViewById(R.id.endFriday);
        saturdayStart = findViewById(R.id.startSaturday);
        saturdayEnd = findViewById(R.id.endSaturday);
        sundayStart = findViewById(R.id.startSunday);
        sundayEnd = findViewById(R.id.endSunday);
        buttonConfirm = findViewById(R.id.saveWorkingHours);
        buttonDiscard = findViewById(R.id.discardWorkingHours);
        errorOutput = findViewById(R.id.errorOutput);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        id = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();

        mref = firebaseDatabase.getReference("users/"+firebaseUser.getUid());

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verify()){
                    setNewHours();
                }
            }
        });

        buttonDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValues();
            }
        });

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employee = dataSnapshot.getValue(Employee.class);
                setValues();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean verify(){
        errorOutput.setText("");
        if(!isRightFormat(mondayStart.getText().toString().trim())){
            errorOutput.setText("Make monday start time right format");
            return false;
        }
        if(!isRightFormat(tuesdayStart.getText().toString().trim())) {
            errorOutput.setText("Make tuesday start time right format");
            return false;
        }
        if(!isRightFormat(wednesdayStart.getText().toString().trim())){
            errorOutput.setText("Make wednesday start time right format");
            return false;
        }
        if(!isRightFormat(thursdayStart.getText().toString().trim())){
            errorOutput.setText("Make thursday start time right format");
            return false;
        }
        if(!isRightFormat(fridayStart.getText().toString().trim())){
            errorOutput.setText("Make friday start time right format");
            return false;
        }
        if(!isRightFormat(saturdayStart.getText().toString().trim())) {
            errorOutput.setText("Make saturday start time right format");
            return false;
        }
        if(!isRightFormat(sundayStart.getText().toString().trim())) {
            errorOutput.setText("Make sunday start time right format");
            return false;
        }
        if(!isRightFormat(mondayEnd.getText().toString().trim())) {
            errorOutput.setText("Make monday end time right format");
            return false;
        }
        if(!isRightFormat(tuesdayEnd.getText().toString().trim())){
            errorOutput.setText("Make tuesday end time right format");
            return false;
        }
        if(!isRightFormat(wednesdayEnd.getText().toString().trim())) {
            errorOutput.setText("Make wednesday end time right format");
            return false;
        }
        if(!isRightFormat(thursdayEnd.getText().toString().trim())) {
            errorOutput.setText("Make thursday end time right format");
            return false;
        }
        if(!isRightFormat(fridayEnd.getText().toString().trim())) {
            errorOutput.setText("Make friday end time right format");
            return false;
        }
        if(!isRightFormat(saturdayEnd.getText().toString().trim())) {
            errorOutput.setText("Make saturday end time right format");
            return false;
        }
        if(!isRightFormat(sundayEnd.getText().toString().trim())) {
            errorOutput.setText("Make sunday end time right format");
            return false;
        }
        return true;
    }

    public void setNewHours(){
        String startMonday = mondayStart.getText().toString().trim();
        String endMonday = mondayEnd.getText().toString().trim();

        String startTuesday = tuesdayStart.getText().toString().trim();
        String endTuesday = tuesdayEnd.getText().toString().trim();

        String startWednesday = wednesdayStart.getText().toString().trim();
        String endWednesday = wednesdayEnd.getText().toString().trim();

        String startThursday = thursdayStart.getText().toString().trim();
        String endThursday = thursdayEnd.getText().toString().trim();

        String startFriday = fridayStart.getText().toString().trim();
        String endFriday = fridayEnd.getText().toString().trim();

        String startSaturday = saturdayStart.getText().toString().trim();
        String endSaturday = saturdayEnd.getText().toString().trim();

        String startSunday = sundayStart.getText().toString().trim();
        String endSunday = sundayEnd.getText().toString().trim();

        Availability monday = new Availability("Monday", startMonday, endMonday);
        Availability tuesday = new Availability("Tuesday", startTuesday, endTuesday);
        Availability wednesday = new Availability("Wednesday", startWednesday, endWednesday);
        Availability thursday = new Availability("Thursday", startThursday, endThursday);
        Availability friday = new Availability("Friday", startFriday, endFriday);
        Availability saturday = new Availability("Saturday", startSaturday, endSaturday);
        Availability sunday = new Availability("Sunday", startSunday, endSunday);

        List<Availability> availabilities = new ArrayList<Availability>();
        availabilities.add(monday);
        availabilities.add(tuesday);
        availabilities.add(wednesday);
        availabilities.add(thursday);
        availabilities.add(friday);
        availabilities.add(saturday);
        availabilities.add(sunday);

        DatabaseReference jref = FirebaseDatabase.getInstance().getReference("users").child(id);
        Employee temp =employee;
        temp.setAvailabilities(availabilities);
        jref.setValue(temp);
        Toast.makeText(this, "Clinic Hours Updated",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(WorkingHoursActivity.this, MainClinicActivity.class);
        startActivity(intent);
    }

    public static boolean isRightFormat(String message){
        if(message.length()!=5){
            return false;
        }
        char[] word = message.toCharArray();
        for(int i=0; i<word.length; i++){
            if ((i==0||i==1||i==3||i==4)&& !isInteger(Character.toString(word[i]))){
                return false;
            }
            if(i==2 && word[i]!=':'){
                return false;
            }
        }

        int startTimeFirst = Integer.parseInt(message.substring(0,2));
        int startTimeLast = Integer.parseInt(message.substring(3,5));
        if(startTimeFirst<0 || startTimeFirst>23){
            return false;
        }
        if (startTimeLast<0 || startTimeLast>59){
            return false;
        }
        return true;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

    public void setValues(){
        if(employee.getAvailabilities()==null){
            Toast.makeText(this, "Cannot discard",Toast.LENGTH_LONG).show();
            return;
        }
        for (int i = 0; i<employee.getAvailabilities().size();i++){
            Availability day  = employee.getAvailabilities().get(i);
            if(day.getDay().equals("Monday")){
                mondayStart.setText(day.getStartTime());
                mondayEnd.setText(day.getEndTime());
            }
            if(day.getDay().equals("Tuesday")){
                tuesdayStart.setText(day.getStartTime());
                tuesdayEnd.setText(day.getEndTime());
            }
            if(day.getDay().equals("Wednesday")){
                wednesdayStart.setText(day.getStartTime());
                wednesdayEnd.setText(day.getEndTime());
            }
            if(day.getDay().equals("Thursday")){
                thursdayStart.setText(day.getStartTime());
                thursdayEnd.setText(day.getEndTime());
            }
            if(day.getDay().equals("Friday")){
                fridayStart.setText(day.getStartTime());
                fridayEnd.setText(day.getEndTime());
            }
            if(day.getDay().equals("Saturday")){
                saturdayStart.setText(day.getStartTime());
                saturdayEnd.setText(day.getEndTime());
            }
            if(day.getDay().equals("Sunday")){
                sundayStart.setText(day.getStartTime());
                sundayEnd.setText(day.getEndTime());
            }

        }

    }
}


