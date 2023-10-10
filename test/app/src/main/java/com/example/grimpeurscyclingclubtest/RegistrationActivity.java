package com.example.grimpeurscyclingclubtest;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import static com.example.grimpeurscyclingclubtest.TextInputValidation.*;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }


    public void OnClickRegister(View view){
        EditText eTextEmail = (EditText) findViewById(R.id.registerEmailInput);
        EditText eTextPass = (EditText) findViewById(R.id.registerPasswordInput);
        EditText eTextUname = (EditText) findViewById(R.id.registerUnameInput);

        String email = eTextEmail.getText().toString();
        String pass = eTextPass.getText().toString();
        String username = eTextUname.getText().toString();
        String role = null;

        RadioButton participantButton = (RadioButton) findViewById(R.id.radioButton);
        RadioButton organizerButton = (RadioButton) findViewById(R.id.radioButton2);

        if(!participantButton.isChecked() && !organizerButton.isChecked()){

        }
        else if (participantButton.isChecked()){
            role = "participant";
        }
        else if (organizerButton.isChecked()){
            role = "organizer";
        }

        if(validateEmailWithRegex(email)&&validateUsernameWithRegex(username)&&validatePass(pass)&& role != null){
            register(username.toLowerCase(),pass,email,role);
        }


    }



    public void register(String username, String password, String email, String role){
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference newUserRoleRef = db.getReference("users/"+username+"/role");
        DatabaseReference newUserEmailRef = db.getReference("users/"+username + "/email");
        DatabaseReference newUserPasswordRef = db.getReference("users/"+username + "/password");

        newUserRoleRef.setValue(role);
        newUserEmailRef.setValue(email);
        newUserPasswordRef.setValue(password);


        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(intent, 0);
    }
}