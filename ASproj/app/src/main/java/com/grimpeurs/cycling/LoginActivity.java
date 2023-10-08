package com.grimpeurs.cycling;

import static android.content.ContentValues.TAG;
import static com.grimpeurs.cycling.TestValidation.validateEmailWithRegex;
import static com.grimpeurs.cycling.TestValidation.validatePass;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
    }

    public void onRegisterRequest(View view) {
        EditText eTextEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        EditText eTextPass = (EditText) findViewById(R.id.editTextTextPassword);

        String email = eTextEmail.getText().toString();
        String pass = eTextPass.getText().toString();

        if (email == "admin" && pass == "admin") {
            // More secure solution to be implemented once further app functionality established.
            email = "g61f23seg2105c@gmail.com";
            pass = "Adminadmin12!";
        }

        if (validatePass(pass) && validateEmailWithRegex(email)) {
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        registrationInformationUI();
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        updateUI(null);
                    }
                }
            });
        }

    }

    public void onLoginRequest(View view) {
        EditText eTextEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        EditText eTextPass = (EditText) findViewById(R.id.editTextTextPassword);

        String email = eTextEmail.getText().toString();
        String pass = eTextPass.getText().toString();

        if (email.equals("admin") && pass.equals("admin")) {
            // More secure solution to be implemented once further app functionality established.
            email = "g61f23seg2105c@gmail.com";
            pass = "Adminadmin12!";
        }

        if (validatePass(pass) && validateEmailWithRegex(email)) {
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        updateUI(null);
                    }
                }
            });
        }

    }

    public void updateUI(FirebaseUser user) {
        Intent intent = new Intent(getApplicationContext(), RegisteredActivity.class);
        if (user == null) {
            Toast toast = Toast.makeText(this, "Login failed...", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            AppUser serialUser = new AppUser(user);
            startActivityForResult (intent.putExtra("USER_INFO", serialUser),0);
        }
    }

    public void registrationInformationUI(){
        Intent intent=new Intent(getApplicationContext(),RegistrationInformation.class);
        startActivityForResult(intent, 0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) return;
        int complete = data.getIntExtra("registerID", R.id.complete);
        int roleID = data.getIntExtra("buttonID", R.id.Role_00);
        String userName=data.getStringExtra("username");
        String role="";

        if(roleID == R.id.Role_00){
            role="Admin";
        }
        else if(roleID == R.id.Role_01){
            role="Cycle Club";
        }
        else if(roleID == R.id.Role_02){
            role="Participant";
        }

        if (complete == R.id.complete) {
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        }

    }
}