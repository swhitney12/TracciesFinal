package com.csci3397.mobileappdev.tracciesapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    Button signUpBtn;
    EditText usernameText, passwordText;
    dbhelper db;
    TextView toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUpBtn = findViewById(R.id.SignupBtn);
        toLogin = findViewById(R.id.toLoginLink);
        usernameText = findViewById(R.id.editTextLoginUsername);
        passwordText = findViewById(R.id.editTextTextUserPass);
        db = new dbhelper(this);

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();

                Boolean createUserCheck = db.createUser(username, password);
                if(createUserCheck == true) {
                    Toast.makeText(SignUp.this, "User Created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, Login.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignUp.this, "User Creation Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}