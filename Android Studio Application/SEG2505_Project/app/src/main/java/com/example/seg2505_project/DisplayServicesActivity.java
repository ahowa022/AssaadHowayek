package com.example.seg2505_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class DisplayServicesActivity extends AppCompatActivity {
   ListView serviceList;
   DatabaseReference mref;
   DatabaseReference jref;
   List<Service> services;
   Employee employeed;
   Button newService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_services);

        //declare necessary variables to display the services
        newService = (Button) findViewById(R.id.addServices);
        serviceList = (ListView) findViewById(R.id.serviceList);
        services = new ArrayList<>();
        mref = FirebaseDatabase.getInstance().getReference("services");

        //when user clicks the add button, calls method addService
        newService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addService();
            }
        });

        //what happens after long clicking on a service
        serviceList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i); //get the service at specific index
                showUpdateDeleteDialog(service); //function called when wanting to update/delete service
                return true;
            }
        });
    }

    public void showUpdateDeleteDialog(final Service service){ //method called to update or delete service

        //code below is to create the popup and declare variables available in pop up
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DisplayServicesActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editName);
        final EditText editTextRole  = (EditText) dialogView.findViewById(R.id.editRole);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduct);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);

        dialogBuilder.setTitle("Editing/Deleting Service");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        //what happens when update button is clicked from the popup
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the elements in the popup
                String name = editTextName.getText().toString().trim();
                String role = editTextRole.getText().toString().trim();
                if (validate(name, role)) { //if is to validate before updating
                    updateService(service.getId(), name, role, service.getClinics()); // method called to update service
                    b.dismiss(); //close the popup
                } else {
                    Toast.makeText(DisplayServicesActivity.this, "Please enter a name and/or role or make name and/or role alphabetical ",Toast.LENGTH_LONG).show();
                }
            }
        });

        //what happens when delete button on pop up is clicked
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService(service); //method called to delete the service
                b.dismiss(); // close the popup
            }
        });
    }

    private void deleteService(final Service service){
        DatabaseReference jref = FirebaseDatabase.getInstance().getReference("services").child(service.getId()); //get reference in database
        jref.removeValue(); //remove value in database
            DatabaseReference iref = FirebaseDatabase.getInstance().getReference("users/");
            iref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        User user = postSnapshot.getValue(User.class);
                        if (user.getRole().equals("Employee")) {
                            Employee employeet = postSnapshot.getValue(Employee.class);
                            if (employeet.getServices().contains(service.getId())) {
                                employeet.getServices().remove(service.getId());
                                DatabaseReference tref = FirebaseDatabase.getInstance().getReference("users/" + employeet.getId());
                                tref.setValue(employeet);
                            }
                        }
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }

            });
        Toast.makeText(this, "Service Deleted", Toast.LENGTH_LONG).show(); //show user service deleted
        }



    private void updateService(String id, String name, String role, List<String> clinics) {
        //get reference of current service
        DatabaseReference jref = FirebaseDatabase.getInstance().getReference("services").child(id);
        Service service = new Service(name, role, id);
        service.setClinics(clinics);
        jref.setValue(service); //modify the old service to new service
        Toast.makeText(this, "Service Updated",Toast.LENGTH_LONG).show(); //show user service updated
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
                ServiceListAdapter serviceAdapter = new ServiceListAdapter(DisplayServicesActivity.this, services);
                //call service adapter for listView to display the services
                serviceList.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addService(){
        //all code below is to create the popup and important variables associated to it
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.addName);
        final EditText editTextRole  = (EditText) dialogView.findViewById(R.id.addRole);
        final Button buttonAdd = (Button) dialogView.findViewById(R.id.addService);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.cancelAddService);

        dialogBuilder.setTitle("Adding Service");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        //when button add in pop up is clicked
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String role = editTextRole.getText().toString().trim();
                if(validate(name, role)){ //validate service
                    String key = mref.push().getKey();
                    //add service to database
                    Service service =new Service(name,role,key);
                    mref.child(key).setValue(service);
                    Toast.makeText(DisplayServicesActivity.this, "Service Added",Toast.LENGTH_LONG).show();
                    b.dismiss(); // close the popup
                }
                else{
                    Toast.makeText(DisplayServicesActivity.this, "Please enter a name and/or role or make name and/or role alphabetical ",Toast.LENGTH_LONG).show();
                }
            }
        });


        //when button is cancelled
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }

    public static boolean validate(String name, String role){
        if(name.trim().length() == 0  || role.trim().length() == 0 ){
            return false;
        }
        if(!isAlphabet(name) || !isAlphabet(role)){
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
}
