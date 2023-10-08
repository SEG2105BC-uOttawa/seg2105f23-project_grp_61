package com.grimpeurs.cycling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

public class RegisteredActivity extends AppCompatActivity {

    private AppUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        user = (AppUser) getIntent().getSerializableExtra("USER_INFO");

        TextView eText = (TextView) findViewById(R.id.registerStatus);
        if (user == null) {
            eText.setText("Login failed ):");
        } else {
            eText.setText("Welcome " + user.getEmail() + "! You are logged in as 'role'.");
        }

    }
}