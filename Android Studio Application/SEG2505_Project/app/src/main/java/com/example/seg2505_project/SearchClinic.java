package com.example.seg2505_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchClinic extends AppCompatActivity {
    ListView serviceList;
    DatabaseReference mref;
    List<Service> services;

    Button searchHours;
    Button searchAddress;

    Spinner day;
    String dayChosen;
    TextView hours;
    TextView address;
    TextView errorOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_clinic);

        searchHours = (Button) findViewById(R.id.searchByTime);
        searchAddress = (Button) findViewById(R.id.searchByAddress);

        day = (Spinner) findViewById(R.id.daySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(adapter);

        hours = (TextView) findViewById(R.id.timeInput);
        address = (TextView) findViewById(R.id.addressInput);
        errorOutput = findViewById(R.id.errorOutput);

        serviceList = (ListView) findViewById(R.id.searchableServicesList);
        services = new ArrayList<>();
        mref = FirebaseDatabase.getInstance().getReference("services");

        //when user clicks the add button, calls method addService
        searchHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchWithHours();
            }
        });
        searchAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchWithAddress();
            }
        });

        //what happens after long clicking on a service
        serviceList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i); //get the service at specific index
                searchWithService(service); //function called when wanting to update/delete service
                return true;
            }
        });

        day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dayChosen = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    protected void onStart(){ //method called on the start of the activity
        super.onStart();
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear(); //clear whole list that was present since maybe changes were made in popup
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){ //look though database
                    Service service  = postSnapshot.getValue(Service.class);
                    services.add(service); //remodify list that is used in listview to display services
                }
                ServiceListAdapter serviceAdapter = new ServiceListAdapter(SearchClinic.this, services);
                //call service adapter for listView to display the services
                serviceList.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void searchWithHours(){
        if(verifyHour()){
            Intent intent = new Intent(SearchClinic.this , ClinicsImplementingTime.class);
            intent.putExtra("hour", hours.getText().toString());
            intent.putExtra("day", dayChosen);
            this.startActivity(intent);
        }

    }

    public void searchWithAddress(){
        if(verifyAddress()){
            Intent intent = new Intent(SearchClinic.this , ClinicsImplementingAddress.class);
            intent.putExtra("address", address.getText().toString());
            this.startActivity(intent);
        }
    }

    public void searchWithService(Service service){
        Intent intent = new Intent(SearchClinic.this , ClinicsImplementingService.class);
        intent.putExtra("id", service.getId());
        this.startActivity(intent);
    }

    public boolean verifyAddress(){
        if(address.getText().toString().length()==0){
            errorOutput.setText("Address input is empty, needs to have a value");
            return false;
        }
        if(!isAlphabet(address.getText().toString())){
            errorOutput.setText("Address input is not alphabetical");
            return false;
        }
        return true;
    }

    public static boolean isAlphabet(String message){
        message = message.trim();
        char[] wordInChar = message.toCharArray();
        for(int i=0; i<wordInChar.length; i++){
            char c  = wordInChar[i];
            if(!Character.isLetter(c) && c != '-' && c!=' '){
                return false;
            }
        }
        return true;
    }

    public boolean verifyHour(){
        if(hours.getText().toString().length()==0){
            errorOutput.setText("Hours input is empty, needs to have a value");
            return false;
        }
        if(!isRightFormat(hours.getText().toString().trim())){
            errorOutput.setText("Make sure your hours has the correct time format (00:00)");
            return false;
        }
        return true;
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
}
