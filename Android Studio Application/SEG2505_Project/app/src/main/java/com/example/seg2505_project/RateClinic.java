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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RateClinic extends AppCompatActivity {

    ListView listEmployee;
    DatabaseReference employeeRef;
    List<Employee> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_clinic);
        employees = new ArrayList<Employee>();
        listEmployee = findViewById(R.id.clinicRatingList);
        employeeRef = FirebaseDatabase.getInstance().getReference("users");

        listEmployee.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Employee employee = employees.get(i); //get the service at specific index
                showRatingDialog(employee); //function called when wanting to update/delete service
                return true;
            }
        });
    }

    @Override
    protected void onStart(){ //method called on the start of the activity
        super.onStart();
        employeeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employees.clear(); //clear whole list that was present since maybe changes were made in popup
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){ //look though database
                    User user  = postSnapshot.getValue(User.class);
                    if(user.getRole().equals("Employee")){
                        Employee employee = postSnapshot.getValue(Employee.class);
                        employees.add(employee);
                    }
                }
                RatingListAdapter ratingAdapter = new RatingListAdapter(RateClinic.this, employees);
                //call service adapter for listView to display the services
                listEmployee.setAdapter(ratingAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showRatingDialog(final Employee employee){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rate_clinic_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText rating =  (EditText) dialogView.findViewById(R.id.clinicRatingInput);
        final EditText comment =  (EditText) dialogView.findViewById(R.id.clinicMessageInput);
        final Button confirmRating = (Button) dialogView.findViewById(R.id.confirmRating);
        final Button cancel = (Button) dialogView.findViewById(R.id.cancelRating);

        dialogBuilder.setTitle("Rating Clinic");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        confirmRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate(rating.getText().toString().trim(), comment.getText().toString())){
                    updateEmployee(employee, Double.parseDouble(rating.getText().toString().trim()), comment.getText().toString().trim());
                    b.dismiss(); // close the popup
                } else {
                    Toast.makeText(RateClinic.this, "Make rating between 1 and 5 or enter a comment",Toast.LENGTH_LONG).show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss(); // close the popup
            }
        });

    }

    public static boolean validate(String rating, String comment){
        if(isDouble(rating)==false){
            return false;
        }

        Double check = Double.parseDouble(rating);

        if (check>5||check<1){
            return false;
        }

        if(comment.length()==0){
            return false;
        }
        return true;
    }

    public void updateEmployee(Employee employee, double rating, String comment){
        DatabaseReference jref = FirebaseDatabase.getInstance().getReference("users").child(employee.getId());
        Employee newEmployee = employee;
        List<Rating> newRatings = employee.getRatings();
        if(newRatings==null)
            newRatings = new ArrayList<Rating>();

        newRatings.add(new Rating(comment,rating));
        newEmployee.setRatings(newRatings);

        jref.setValue(newEmployee);
        Toast.makeText(this, "Rate Updated to Clinic",Toast.LENGTH_LONG).show();
    }

    public static boolean isDouble(String message){
        try {
            Double.parseDouble(message);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

}
