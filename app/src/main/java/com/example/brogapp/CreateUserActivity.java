package com.example.brogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateUserActivity extends AppCompatActivity {

    TextView logIn;
    EditText name, email, password;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        name = findViewById(R.id.nameET);
        email = findViewById(R.id.emailET);
        password = findViewById(R.id.newPasswordET);

        logIn = findViewById(R.id.alreadyUserTV);
        save = findViewById(R.id.createUserBtn);


    }
}