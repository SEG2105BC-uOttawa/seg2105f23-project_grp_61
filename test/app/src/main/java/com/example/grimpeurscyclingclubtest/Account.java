package com.example.grimpeurscyclingclubtest;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class Account {



    String email;
    String username;
    String role;


    public Account() {
        // Account objects constructed from Firebase will have a null username value, this will need to be fixed in the future.
    }

    public Account(String username){
        this.username = username;

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference emailRef = db.getReference("users" + username + "/email");
        DatabaseReference roleRef = db.getReference("users" + username + "/role");


        ValueEventListener emailListener = new ValueEventListener() {
            boolean finished = false;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (finished) {
                    return;
                }

                email = dataSnapshot.getValue(String.class);

                finished = true;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        emailRef.addValueEventListener(emailListener);

        ValueEventListener roleListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                role = dataSnapshot.getValue(String.class);
                // ..



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        roleRef.addValueEventListener(roleListener);
    }


    public String getEmail(){
        return email;
    }
    public String getRole(){
        return role;
    }
    public String getUname(){
        return username;
    }
}
