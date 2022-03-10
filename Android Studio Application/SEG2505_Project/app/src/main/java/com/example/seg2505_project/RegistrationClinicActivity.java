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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationClinicActivity extends AppCompatActivity {
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    EditText clinicName;
    Button createAccount;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_clinic);

        // All variables required to the registration
        clinicName = findViewById(R.id.clinicNameEditText);
        firstName = findViewById(R.id.firstNameText);
        lastName = findViewById(R.id.lastNameText);
        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        createAccount = findViewById(R.id.createAccount);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        createAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean check = verify(); // Verifies all fields were validated
                if (check == true){
                    final TextView textView = findViewById(R.id.errorOutput);
                    textView.setText("creating account");
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), // Creates the user in the database
                            password.getText().toString()).
                            addOnCompleteListener(RegistrationClinicActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) { // If everything went well
                                        TextView textView = findViewById(R.id.errorOutput);
                                        textView.setText("");
                                        addInRealTimeDatabase();

                                        // Cleans the UI
                                        Toast.makeText(RegistrationClinicActivity.this, "Registered succesfully",
                                                Toast.LENGTH_LONG).show();
                                        clinicName.setText("");
                                        firstName.setText("");
                                        lastName.setText("");
                                        email.setText("");
                                        password.setText("");
                                        startActivity(new Intent(RegistrationClinicActivity.this, LoginActivity.class));
                                    } else {
                                        TextView textView = findViewById(R.id.errorOutput);
                                        textView.setText("");
                                        Toast.makeText(RegistrationClinicActivity.this, task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

            }
        });
    }

    // Method to make sure all fields are validated
    private boolean verify() {
        TextView textView = findViewById(R.id.errorOutput);
        textView.setText("");
        boolean validText= true;
        // Condition if password or email fields are empty
        if(password.getText().toString().trim().length()==0|| email.getText().toString().trim().length()==0){
            validText = false;
            textView.setText("Insert an email or password");
        }
        // Condition to make sure names are only composed of letters in the alphabet
        if(!isAlphabet(firstName.getText().toString())|| !isAlphabet(lastName.getText().toString())){
            validText = false;
            textView.setText("Make first and/or last name alphabetical");
        }
        // Makes sure the name fields are not empty
        if(firstName.getText().toString().trim().length()==0 || lastName.getText().toString().trim().length()==0){
            textView.setText("Insert a first and/or last name");
            validText = false;
        }
        // Checks that the clinic name is in the alphabet
        if(!isAlphabet(clinicName.getText().toString())){
            textView.setText("Make Clinic name alphabetical");
            validText = false;
        }
        // Checks that the clinic name field is empty
        if(clinicName.getText().toString().trim().length()==0){
            textView.setText("Insert a Clinic name");
            validText=false;
        }
        return validText; // If any of the conditions are entered, returns false, else returns true
    }


    // Method that adds to the RealTime Database
    private void addInRealTimeDatabase(){
        // Variables to create the User to be added
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        String uId = user.getUid();
        TextView textView = findViewById(R.id.errorOutput);
        // Makes the instance of the child and adds it to the databse
        reference.child(uId).setValue(new Employee(email.getText().toString().trim(),firstName.getText().toString().trim(),lastName.getText().toString().trim(),"Employee", clinicName.getText().toString(),uId));
    }

    // Method that checks that an entered string only contains words in the alphabet
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
