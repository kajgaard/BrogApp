package com.example.brogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateUserActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "TAG";
    TextView alreadyUser;
    EditText nameET, emailET, passwordET;
    Button save;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);



        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        nameET = findViewById(R.id.nameET);
        nameET.requestFocus();
        emailET = findViewById(R.id.newEmailET);
        passwordET = findViewById(R.id.newPasswordET);
        alreadyUser = findViewById(R.id.alreadyUserTV);
        alreadyUser.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar);
        save = findViewById(R.id.createUserBtn);
        save.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {

        if(view == alreadyUser){
            startActivity(new Intent(getApplicationContext(),LogInActivity.class));
            finish();
        }else if(view == save){
            final String name = nameET.getText().toString().trim();
            final String email = emailET.getText().toString().trim();
            String password = passwordET.getText().toString().trim();

            //These loops checks if input is valid
            if(TextUtils.isEmpty(name)){
                nameET.setError("Feltet skal udfyldes");
                return;
            }
            if(TextUtils.isEmpty(email)){
                emailET.setError("Feltet skal udfyldes");
                return;
            }
            if(TextUtils.isEmpty(password)){
                passwordET.setError("Feltet skal udfyldes");
                return;
            }
            if(password.length() < 6 ){
                passwordET.setError("Password skal min. have 6 karakterer");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            //Create user in database
            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(CreateUserActivity.this, "Bruger oprettet", Toast.LENGTH_SHORT).show();
                        userID = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("users").document(userID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("name",name);
                        user.put("email",email);
                        user.put("userID",userID);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Succes; User created for "+userID);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure" + e.toString());
                            }
                        });
                        startActivity(new Intent(getApplicationContext(),HomePage.class));
                    }else{
                        Toast.makeText(CreateUserActivity.this, "Fejl " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}