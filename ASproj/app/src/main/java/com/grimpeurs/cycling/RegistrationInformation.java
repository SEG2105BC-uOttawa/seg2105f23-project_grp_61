package com.grimpeurs.cycling;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationInformation extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_information);
    }

    public void setRole(View view) {
        Intent returnIntent = new Intent();
        int selectedButton = view.getId();
        EditText userName = (EditText) findViewById(R.id.userNameEdit);
        if(selectedButton==R.id.Role_00 || selectedButton==R.id.Role_01 || selectedButton==R.id.Role_02) {

            returnIntent.putExtra("buttonID", selectedButton);
            returnIntent.putExtra("registerID",R.id.complete);
        }
        else if(selectedButton==R.id.complete) {
            System.out.println(userName.getText().toString());
            returnIntent.putExtra("username", userName.getText().toString()); // This still doesn't work, will look into - Marc
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }
}