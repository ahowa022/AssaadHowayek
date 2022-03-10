package com.example.seg2505_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegisterServiceToClinicActivity extends AppCompatActivity {
    ListView serviceList;
    DatabaseReference jref;
    DatabaseReference mref;
    List<Service> services;
    List<String> userIds;
    FirebaseAuth firebaseAuth;
    String userId;
    Employee employeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_service_to_clinic);

        //declare necessary variables to display the services
        serviceList = (ListView) findViewById(R.id.availableServicesList);
        services = new ArrayList<>();
        userIds = new ArrayList<>();
        mref = FirebaseDatabase.getInstance().getReference("services");
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        jref = FirebaseDatabase.getInstance().getReference("users/"+userId);
        //when user clicks the add button, calls method addService

        //what happens after long clicking on a service
        serviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Service service = services.get(i); //get the service at specific index
                service.addClinic(userId);
                showAddedDialog(service); //function called when wanting to update/delete service
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
                    if(!service.getClinics().contains(userId)){
                        services.add(service); //remodify list that is used in listview to display services
                    }
                }
                ServiceListAdapter serviceAdapter = new ServiceListAdapter(RegisterServiceToClinicActivity.this, services);
                //call service adapter for listView to display the services
                serviceList.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        jref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employeed = dataSnapshot.getValue(Employee.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void showAddedDialog(final Service service){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RegisterServiceToClinicActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.register_service_dialog, null);
        dialogBuilder.setView(dialogView);
        //the buttons delete and cancelled
        final Button buttonRegister = (Button) dialogView.findViewById(R.id.buttonRegisterService);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.buttonCancelRegister);

        dialogBuilder.setTitle("Adding Service");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        //onclick listener for cancel
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
        //onclick listener for delete
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerService(service);//removes user from database
                b.dismiss();
            }
        });
    }
    public void registerService(Service service){
        FirebaseDatabase.getInstance().getReference("services/"+service.getId()).setValue(service);
        employeed.addService(service.getId());
        jref.setValue(employeed);
        startActivity(new Intent(RegisterServiceToClinicActivity.this, ServicesAssociatedToClinicActivity.class));
    }
}

