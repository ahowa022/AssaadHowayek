package com.example.seg2505_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.String;


public class RegistrationPatientActivity extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    Button createAccount;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    //code
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //declares all variables necessary to complete registration
        firstName = findViewById(R.id.firstNameText);
        lastName = findViewById(R.id.lastNameText);
        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        createAccount = findViewById(R.id.createAccount);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance(); //gets instance of database
        reference = database.getReference("users"); //reference to users
        createAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean check = verify(); //validates all components before creating instance in authentication database
                if (check == true){
                final TextView textView = findViewById(R.id.errorOutput);
                textView.setText("creating account");
                    //creates instance in firebase authentication with validated email
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),
                            password.getText().toString()).
                            addOnCompleteListener(RegistrationPatientActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        TextView textView = findViewById(R.id.errorOutput);
                                        textView.setText("");
                                        addInRealTimeDatabase();

                                        Toast.makeText(RegistrationPatientActivity.this, "Registered succesfully",
                                                Toast.LENGTH_LONG).show();
                                        //resets all values in UI
                                        firstName.setText("");
                                        lastName.setText("");
                                        email.setText("");
                                        password.setText("");
                                        startActivity(new Intent(RegistrationPatientActivity.this, LoginActivity.class));
                                    } else {
                                        TextView textView = findViewById(R.id.errorOutput);
                                        textView.setText("");
                                        Toast.makeText(RegistrationPatientActivity.this, task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

            }
        });
    }

    // Method to make sure everything is valid
    public boolean verify() {
        TextView textView = findViewById(R.id.errorOutput);
        textView.setText("");
        boolean validText= true;
        if(password.getText().toString().trim().length()==0|| email.getText().toString().trim().length()==0){ // If the fields are empty
            validText = false;
            textView.setText("Insert an email or password");
        }
        if(!isAlphabet(firstName.getText().toString())|| !isAlphabet(lastName.getText().toString())){ // If the first name or last name are not fully alphabetical
            validText = false;
            textView.setText("Make first and/or last name alphabetical");
        }
        if(firstName.getText().toString().trim().length()==0 || lastName.getText().toString().trim().length()==0){ // If name fields are validated
            textView.setText("Insert a first and/or last name");
            validText = false;
        }

        return validText;
    }

    // Method that adds to the RealTime Database
    private void addInRealTimeDatabase(){
        // Variables to create the User to be added
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        String uId = user.getUid();
        TextView textView = findViewById(R.id.errorOutput);
        // Makes the instance of the child and adds it to the databse
        reference.child(uId).setValue(new Patient(email.getText().toString().trim(),firstName.getText().toString().trim(),lastName.getText().toString().trim(),"Patient", uId));

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
