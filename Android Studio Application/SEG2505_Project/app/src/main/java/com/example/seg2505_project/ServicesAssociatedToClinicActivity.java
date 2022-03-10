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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServicesAssociatedToClinicActivity extends AppCompatActivity {
    ListView serviceList;
    DatabaseReference jref;
    DatabaseReference mref;
    DatabaseReference iref;
    List<Service> services;
    List<String> userIds;
    FirebaseAuth firebaseAuth;
    String userId;
    Employee employeed;
    Button button;
    Button back;
    TextView clinicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_associated_to_clinic);

        //declare necessary variables to display the services
        button = (Button) findViewById(R.id.addServicesToClinic);
        serviceList = (ListView) findViewById(R.id.clinicServiceList);
        services = new ArrayList<>();
        userIds = new ArrayList<>();
        mref = FirebaseDatabase.getInstance().getReference("services");
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        jref = FirebaseDatabase.getInstance().getReference("users/" + userId);
        back = findViewById(R.id.backButton);
        clinicName = findViewById(R.id.clinicName);


        //when user clicks the add button, calls method addService
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServicesAssociatedToClinicActivity.this, RegisterServiceToClinicActivity.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServicesAssociatedToClinicActivity.this, MainClinicActivity.class));
            }
        });

        //what happens after long clicking on a service
        serviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Service service = services.get(i); //get the service at specific index
                showRemoveDialog(service); //function called when wanting to update/delete service
            }
        });
    }

    @Override
    protected void onStart() { //method called on the start of the activity
        super.onStart();
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear(); //clear whole list that was present since maybe changes were made in popup
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) { //look though database
                    Service service = postSnapshot.getValue(Service.class);
                    if (service.getClinics().contains(userId)) {
                        services.add(service); //remodify list that is used in listview to display services
                    }
                }
                ServiceListAdapter serviceAdapter = new ServiceListAdapter(ServicesAssociatedToClinicActivity.this, services);
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
                clinicName.setText((employeed.getClinicName()));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    public void showRemoveDialog(final Service service){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ServicesAssociatedToClinicActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.remove_service_dialog, null);
        dialogBuilder.setView(dialogView);

        //the buttons delete and cancelled
        final Button buttonRegister = (Button) dialogView.findViewById(R.id.buttonRemoveService);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.buttonCancelService);

        dialogBuilder.setTitle("Removing Service");
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
                RemoveService(service);//removes user from database
                b.dismiss();
            }
        });
    }
    public void RemoveService(Service service){
        iref = FirebaseDatabase.getInstance().getReference("services/"+service.getId());
        ArrayList<String> clinicsInServices = (ArrayList<String>) service.getClinics();
        clinicsInServices.remove(userId);
        service.setClinics(clinicsInServices);
        iref.setValue(service);

        ArrayList<String> servicesInEmployee = (ArrayList<String>) employeed.getServices();
        servicesInEmployee.remove(service.getId());
        employeed.setServices(servicesInEmployee);
        jref.setValue(employeed);
        Toast.makeText(this, "Service Deleted", Toast.LENGTH_LONG).show(); //show user service deleted
    }
}
