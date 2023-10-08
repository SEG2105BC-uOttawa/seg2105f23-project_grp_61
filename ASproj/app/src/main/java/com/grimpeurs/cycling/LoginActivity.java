package com.grimpeurs.cycling;

import static android.content.ContentValues.TAG;
import static com.grimpeurs.cycling.TestValidation.validateEmailWithRegex;
import static com.grimpeurs.cycling.TestValidation.validatePass;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

        if (validatePass(pass) && validateEmailWithRegex(email)) {
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
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

    public void onLoginRequest(View view) {
        EditText eTextEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        EditText eTextPass = (EditText) findViewById(R.id.editTextTextPassword);

        String email = eTextEmail.getText().toString();
        String pass = eTextPass.getText().toString();

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
        // use this function to update the UI following a successful login.
        if (user == null) {
            System.out.println("Didn't work...");
        } else {
            System.out.println("Worked!");
        }
    }
}