package com.csci3397.mobileappdev.tracciesapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class Login extends AppCompatActivity {
    dbhelper db;
    Button loginBtn;
    boolean loginSucc = false;
    EditText loginUsername, loginPassword;
    SharedPreferences preferences;
    TextView toSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.Loginbtn);
        loginUsername = findViewById(R.id.editTextLoginUsername);
        loginPassword = findViewById(R.id.editTextLoginPassword);
        toSignUp = findViewById(R.id.toSignUpLink);
        db = new dbhelper(this);

        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();
                Cursor res = db.getData();
                if (res.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Database is empty", Toast.LENGTH_SHORT).show();
                } else {
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext() && loginSucc == false) {

                        if (username.equals(res.getString(0)) && password.equals(res.getString(1))){
                            loginSucc = true;
                            Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();

                            preferences = getApplicationContext().getSharedPreferences("Preferences", 0);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("username", username);
                            editor.putString("password", password);
                            editor.commit();

                            Intent intent = new Intent(Login.this, ApplicationMain.class);
                            startActivity(intent);
                            break;
                        }
                    }
                    if(loginSucc == false) {
                        Log.d(Login.class.getSimpleName(), "Login unsuccessful");
                        Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                    else loginSucc = false;
                }

            }
        });


    }
}