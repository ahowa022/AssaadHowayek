package com.example.seg2505_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class ClinicCompleteProfileActivity extends AppCompatActivity {

    //declare variables needed to complete clinic profile
    EditText city;
    EditText street;
    EditText numberStreet;
    EditText phoneNumber;
    Button confirm;
    CheckBox p1,p2,p3,i1,i2,i3,i4;
    TextView error;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String id;
    DatabaseReference mref;
    Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_complete_profile);
        city = findViewById(R.id.cityEditText);
        numberStreet = findViewById(R.id.streetNumberEditText);
        street = findViewById(R.id.streetNameEditText);
        confirm = findViewById(R.id.saveChanges);
        phoneNumber = findViewById(R.id.phoneNumberEditText);
        p1 = findViewById(R.id.cashCheckBox);
        p2 = findViewById(R.id.creditCheckBox);
        p3 = findViewById(R.id.debitCheckBox);
        i1 =findViewById(R.id.hmoCheckBox);
        i2 =findViewById(R.id.ppoCheckBox);
        i3 =findViewById(R.id.epoCheckBox);
        i4 =findViewById(R.id.posCheckBox);
        error = findViewById(R.id.errorOutput);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        id = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();

        mref = firebaseDatabase.getReference("users/"+firebaseUser.getUid());

        //confirm information process
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verify()){ //if inputs are valid we complete information
                    addAdditionalInfo();
                }
            }
        });

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                employee = dataSnapshot.getValue(Employee.class);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //verifies if users inputs are valid to complete information
    public boolean verify(){
        boolean validText = true;
        boolean validCheckBox = true;

        error.setText("");
        if(street.getText().toString().trim().length()==0|| city.getText().toString().trim().length()==0){
            validText = false;
            error.setText("Insert a street or city");
        }

        if(!isAlphabet(street.getText().toString().trim())|| !isAlphabet(street.getText().toString().trim())){
            validText = false;
            error.setText("Make street or city alphabetical");
        }

        if(!isInteger(numberStreet.getText().toString().trim()) ||numberStreet.getText().toString().trim().length()==0 ){
            error.setText("Insert street number or make it numerical");
            validText = false;
        }

        if(!isInteger(phoneNumber.getText().toString().trim()) ||phoneNumber.getText().toString().trim().length()==0 ){
            error.setText("Insert phone number or make it numerical");
            validText = false;
        }

        if(isInteger(phoneNumber.getText().toString().trim())&& (phoneNumber.getText().toString().trim().length()<10|| phoneNumber.getText().toString().trim().length()>13)){
            error.setText("Insert real phone number with right format");
            validText =  false;
        }

        if(!i1.isChecked()&& !i2.isChecked()&& !i3.isChecked()&& !i4.isChecked()&&!p1.isChecked()&&!p2.isChecked()&&!p3.isChecked()){
            error.setText("Check at least one payment method");
            validCheckBox = false;
        }

        return validCheckBox && validText;
    }


    //update information in real time database
    public void addAdditionalInfo(){
        List<String> paymentMehotds = new ArrayList<String>();
        if(i1.isChecked()){
            paymentMehotds.add("HMO insurance");
        }
        if(i2.isChecked()){
            paymentMehotds.add("PPO insurance");
        }
        if(i3.isChecked()){
            paymentMehotds.add("EPO insurance");
        }
        if(i4.isChecked()){
            paymentMehotds.add("POS insurance");
        }
        if(p1.isChecked()){
            paymentMehotds.add("Cash");
        }
        if(p2.isChecked()){
            paymentMehotds.add("Credit Card");
        }
        if(p3.isChecked()){
            paymentMehotds.add("Debit Card");
        }

        Address address = new Address(Integer.parseInt(numberStreet.getText().toString().trim()),street.getText().toString().trim(),
                city.getText().toString().trim());
        DatabaseReference jref = FirebaseDatabase.getInstance().getReference("users").child(id);
        Employee temp = employee;
        temp.setAddress(address);
        temp.setPaymentMethods(paymentMehotds);
        temp.setPhoneNumber(Long.parseLong(phoneNumber.getText().toString().trim()));
        jref.setValue(temp); //modify the old employee to info to new employee info

        Intent intent = new Intent(ClinicCompleteProfileActivity.this, MainClinicActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Employee Info Updated",Toast.LENGTH_LONG).show(); //show user employee updated
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

    public static boolean isInteger(String s) {
        try {
            Long.parseLong(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }
}
