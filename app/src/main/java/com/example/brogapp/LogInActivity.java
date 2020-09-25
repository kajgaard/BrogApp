package com.example.brogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    Button login;
    EditText mailET, pswdET;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        fAuth = FirebaseAuth.getInstance();

        login =  findViewById(R.id.logInBtn);
        login.setOnClickListener(this);

        mailET =  findViewById(R.id.emailET);
        pswdET =  findViewById(R.id.passwordET);

        progressBar = findViewById(R.id.progressBar2);

        //Checks is user is already signed in
        if(fAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(),HomePage.class));
            finish();
        }

    }

    @Override
    public void onClick(View view) {

        if(view  == login){

            String email = mailET.getText().toString().trim(); // obs trim() removes whitespace
            String password  = pswdET.getText().toString().trim();

            //These loops checks if input is valid
            if(TextUtils.isEmpty(email)){
                mailET.setError("Feltet skal udfyldes");
                return;
            }
            if(TextUtils.isEmpty(password)){
                pswdET.setError("Feltet skal udfyldes");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        startActivity(new Intent(getApplicationContext(),HomePage.class));
                        Toast.makeText(LogInActivity.this, "Login succesfuld", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LogInActivity.this, "Fejl: "+ task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }


                }

            });


        }

    }
}