package brog.coffee.brogapp.LogOnActivities;

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

import brog.coffee.brogapp.StartActivity.HomePage;

import brog.coffee.brogapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    Button login;
    TextView createUser, forgotPswd;
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

        //Dette textview bliver lige nu brugt som testbruger log ind isetdet
        forgotPswd = findViewById(R.id.forgotPswTV);
        forgotPswd.setOnClickListener(this);

        createUser = findViewById(R.id.newUserTV);
        createUser.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar2);

        //Checks is user is already signed in
        if(fAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(), HomePage.class));
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
                        finish();
                    }else{
                        Toast.makeText(LogInActivity.this, "Fejl: "+ task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }

                }

            });


        }else if(view == forgotPswd){
            //Der logges ind som testbruger istedet
            Toast.makeText(this, "Du logger nu ind som testbruger", Toast.LENGTH_SHORT).show();
            String email1 = "test@test.dk";
            String password1 = "123456";
            progressBar.setVisibility(View.VISIBLE);
            fAuth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        startActivity(new Intent(getApplicationContext(),HomePage.class));
                        Toast.makeText(LogInActivity.this, "Login succesfuld", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(LogInActivity.this, "Fejl: "+ task.getException().toString(), Toast.LENGTH_SHORT).show();
                        Log.d("LOGIN","Fejlen er "+task.getException().toString());
                    }

                }

            });

        }else if(view == createUser){
            startActivity(new Intent(getApplicationContext(), CreateUserActivity.class));
            finish();
        }

    }
}