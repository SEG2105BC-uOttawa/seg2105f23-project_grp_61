package com.example.grimpeurscyclingclubtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void OnClickLogin(View view){

        EditText eTextEmail = (EditText) findViewById(R.id.loginUsernameInput);
        EditText eTextPass = (EditText) findViewById(R.id.loginPaswordInput);

        String username = eTextEmail.getText().toString();
        String pass = eTextPass.getText().toString();

        if(username.contains(".")){
            eTextEmail.setText("");
            username = null;
        }
        else if(username.contains("#")){
            eTextEmail.setText("");
            username = null;

        }
        else if(username.contains("$")){
            eTextEmail.setText("");
            username = null;

        }
        else if(username.contains("[")){
            eTextEmail.setText("");
            username = null;

        }
        else if(username.contains("]")){
            eTextEmail.setText("");
            username = null;

        }


        if (username != null && pass != null) {
            login(username.toLowerCase(), pass);
        }





//        if(pass == testpass){
//            Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
//            startActivityForResult(intent, 0);
//        }





    }

    public void login(String username, String pass){
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        String path = "users/"+username +"/password";
        DatabaseReference passwordRef = db.getReference(path);
        ValueEventListener passListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String testpass = dataSnapshot.getValue(String.class);
                // ..
                if(pass.equals(testpass)){

                    userRouter(username);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        passwordRef.addValueEventListener(passListener);

    }

    private void userRouter(String uname) {

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference roleRef = db.getReference("users/"+uname + "/role");

        ValueEventListener roleListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                String role = dataSnapshot.getValue(String.class);

                if (role.equals("admin")) {
                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                    intent.putExtra("uname", uname);
                    startActivity(intent);
                } else if (role.equals("organizer")) {
                    Intent intent = new Intent(getApplicationContext(), OrganizerActivity.class);
                    intent.putExtra("uname", uname);
                    startActivity(intent);
                } else if (role.equals("participant")) {
                    Intent intent = new Intent(getApplicationContext(), ParticipantActivity.class);
                    intent.putExtra("uname", uname);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        roleRef.addValueEventListener(roleListener);

        //Intent intent = new Intent(getApplicationContext(), RoutingActivity.class);
        //intent.putExtra("uname", uname);
        //startActivity(intent);



    }





    public void onClickRegister(View view) {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivityForResult(intent, 0);
    }
}