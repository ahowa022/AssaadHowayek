package com.example.seg2505_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;

import android.view.View;
import android.widget.Button;


public class MainAdminActivity extends AppCompatActivity {
    Button buttonServices;
    Button buttonUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        buttonUsers = (Button) findViewById(R.id.viewUser);
        buttonServices = (Button) findViewById(R.id.viewService);

        buttonServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainAdminActivity.this, DisplayServicesActivity.class));
            }
        });

        buttonUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainAdminActivity.this, DisplayUsersActivity.class));
            }
        });
    }
}
