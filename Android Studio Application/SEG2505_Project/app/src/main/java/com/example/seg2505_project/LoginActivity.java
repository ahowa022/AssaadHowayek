package com.example.seg2505_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button buttonLogin;
    Button buttonRegisterPatientHere;
    Button buttonRegisterEmployeeHere;
    EditText emailEntry;
    EditText passwordEntry;
    TextView errorOutput;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin = findViewById(R.id.loginBTN);
        buttonRegisterEmployeeHere = findViewById((R.id.registerEmployeeBTN));
        buttonRegisterPatientHere = findViewById((R.id.registerPatientBTN));
        emailEntry = findViewById(R.id.emailText);
        passwordEntry = findViewById(R.id.passwordtext);
        errorOutput = findViewById(R.id.errorOutput);

        firebaseAuth = FirebaseAuth.getInstance();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Verify()) {
                    errorOutput.setText("");
                    errorOutput.setText("Authenticating");
                    firebaseAuth.signInWithEmailAndPassword(emailEntry.getText().toString(), passwordEntry.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        errorOutput.setText("");
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        passwordEntry.setText("");
                                    } else {
                                        errorOutput.setText("");
                                        Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
        buttonRegisterPatientHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistrationPatient();
            }
        });

        buttonRegisterEmployeeHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistrationClinic();
            }
        });
    }
    public void openRegistrationPatient() {
        Intent linkToRegistration = new Intent(this, RegistrationPatientActivity.class);
        startActivity(linkToRegistration);
    }

    public void openRegistrationClinic() {
        Intent linkToRegistration = new Intent(this, RegistrationClinicActivity.class);
        startActivity(linkToRegistration);
    }

    private boolean Verify(){
        errorOutput.setText("");
        boolean validText = true;
        if(passwordEntry.getText().toString().trim().length()==0 || emailEntry.getText().toString().trim().length()==0){
            validText = false;
            errorOutput.setText("Insert an email or password");
        }
        return validText;
    }
}
