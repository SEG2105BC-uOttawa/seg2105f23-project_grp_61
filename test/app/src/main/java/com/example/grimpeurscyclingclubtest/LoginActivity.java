package com.example.grimpeurscyclingclubtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void OnClickLogin(View view){
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");

        EditText eTextEmail = (EditText) findViewById(R.id.loginUsernameInput);
        EditText eTextPass = (EditText) findViewById(R.id.loginPaswordInput);

        String username = eTextEmail.getText().toString();
        String pass = eTextPass.getText().toString();

        DatabaseReference passwordRef = db.getReference("users/"+username +"password");

        String checkPassword = passwordRef



    }




    public void onClickRegister(View view) {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivityForResult(intent, 0);
    }
}