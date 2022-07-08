package com.example.theonefitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActiviity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText etEmail, etPassword;
    Button btnLogin, btnSignup;
    TextView tvForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // getting all the instances from the xml
        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        tvForgot = findViewById(R.id.tvForgot);

        // reset the password method
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // using firebase auth, send reset password to the email and prompt on complete
                mAuth.sendPasswordResetEmail(etEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(LoginActiviity.this, "Please check your emailbox for instructions", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        // sign up method, create a new intent and start signup
        btnSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(LoginActiviity.this, MainActivity.class);
                startActivity(signupIntent);
            }
        });

        // login method
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                // check that fields are filled and activate sign method or prompt toast to fix
                // upon successful registration user is passed onto the dashboard
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActiviity.this, "Fill out the details", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // check that the task is completed successfully, if did, start intent and go to dashboard if it not, then make sure the details are correct toast
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActiviity.this, "Log in success!", Toast.LENGTH_LONG).show();
                                Intent dashboardIntent = new Intent(LoginActiviity.this, DashboardActivity.class);
                                startActivity(dashboardIntent);
                            } else {
                                Toast.makeText(LoginActiviity.this, "Please correct the details", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            }
        });
    }
}