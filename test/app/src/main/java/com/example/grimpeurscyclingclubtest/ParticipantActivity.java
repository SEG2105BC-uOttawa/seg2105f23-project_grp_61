package com.example.grimpeurscyclingclubtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParticipantActivity extends AppCompatActivity {


    String uname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);

        Bundle bundle = getIntent().getExtras();
        String uname = bundle.getString("uname");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference roleRef = db.getReference("users/"+uname + "/role");



        ValueEventListener roleListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                String role = dataSnapshot.getValue(String.class);
                // ..

                AdminAccount userAccount = new AdminAccount(uname);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        roleRef.addValueEventListener(roleListener);


        Button button = (Button) findViewById(R.id.button4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onJoinEventsClick(v);
            }
        });

    }

    /* branch off of aiden's branch for skilllevel
    join new events button
    change skill level button


    for creating the lists
    - default sort will just be organizers
    - you can change it to either all events or all events of one type

    - default sort
    - users/ iterate / role / if role is organizer

    - sort by event type
    - select event type
    - users/ iterate / role / if role is organizer / events / if event/eventtype is nasbike

    - all events
    - users/ iterate / role / if role is organizer / events / iterate


    JOINING EVENTS
    - need to validate
        1. user is not already in event
        2. participant limit not exceeded
        3. user meets skill req
     */

    private void onJoinEventsClick(View view){
        // new event discovery activity
        Intent intent = new Intent(this, ParticipantEventSearch.class);
        intent.putExtra("uname", uname);
        startActivity(intent);
    }
}