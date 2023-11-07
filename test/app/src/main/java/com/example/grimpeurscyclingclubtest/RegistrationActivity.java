package com.example.grimpeurscyclingclubtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference mdb = db.getReference();


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    mdb.child("login").child(username).child("email").setValue(email);


                    mdb.child("usersTest").child(user.getUid()).child("email").setValue(email);
                    mdb.child("usersTest").child(user.getUid()).child("role").setValue(role);
                    mdb.child("usersTest").child(user.getUid()).child("uname").setValue(username);

                }
            }
        });

        //newUserRoleRef.setValue(role);
        //newUserPasswordRef.setValue(password);


        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(intent, 0);
    }
}