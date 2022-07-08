package com.example.theonefitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etVerifyPassword;
    Button btnSignup, btnLogin;
    //create firebase authenticator, edit text vars and buttons for our app
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create instance of firebase authentication and connect the components in view id
        mAuth = FirebaseAuth.getInstance();
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etVerifyPassword = findViewById(R.id.etVerifyPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        // login button will create an activity with intent
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActiviity.class);
                startActivity(loginIntent);
            }
        });

        // sing up button will register and sign the user into the app via firebase authentication
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // our pass on default google setting must be 6 chars long and email has to have @g.something
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String verPassword = etVerifyPassword.getText().toString();
                //check if all the fields are filled or put up a toast if not
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ){
                    Toast.makeText(MainActivity.this, "Fill out all details", Toast.LENGTH_LONG).show();
                // check if the password is at least 6 char in length
                }else if(password.length() < 6){
                    Toast.makeText(MainActivity.this, "Password must be at least 6 char. long!", Toast.LENGTH_SHORT).show();
                //check if the email look like a real email
                }else if(!email.contains("@")){
                    Toast.makeText(MainActivity.this, "The email has to be valid.", Toast.LENGTH_SHORT).show();
                // check that both of the passwords match
                }else if(!password.equals(verPassword)){
                    Toast.makeText(MainActivity.this, "The Passwords don't match", Toast.LENGTH_SHORT).show();
                // create the user and sign up
                }
                else {
                    //create user with email and password
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        //notify if its created or isnt.
                                        Toast.makeText(MainActivity.this, "Success! Account created.", Toast.LENGTH_LONG).show();
                                        startActivity( new Intent(MainActivity.this, DashboardActivity.class));
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "Email already registered.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    // If the user has previously logged in, skip the sign-up/login process
    @Override
    protected void onStart() {
        super.onStart();
        //check the user instance if its not null, then skip the sign in
        FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();
        if (curUser != null){
            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            //Toast.makeText(MainActivity.this, "Hello " + curUser.getDisplayName(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {

        android.os.Process.killProcess(android.os.Process.myPid());
        // This above line close correctly
    }
}