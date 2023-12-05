package com.example.grimpeurscyclingclubtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParticipantEventSearchResultActivity extends AppCompatActivity {

    //todo validate joining event skilllevel and spotsleft

    String uname;
    String ename;
    String clubName;


    String date;

    String time;

    int participantSkill;
    String eventSkill;
    int participantsLeft;

    ParticipantEventSearchResultActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_event_search_result);

        Bundle bundle = getIntent().getExtras();
        uname = bundle.getString("uname");
        ename = bundle.getString("ename");
        clubName = bundle.getString("clubName");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference eventRef = db.getReference("users/" + clubName + "/events/" + ename);

        ParticipantEventSearchResultActivity context = this;

        TextView textViewName = (TextView) findViewById(R.id.textViewOrganizerName);
        textViewName.setText(ename);


        Button button = (Button) findViewById(R.id.button6);

        eventRef.child("/registeredParticipants/" + uname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    button.setText("Unregister for event");
                    return;
                }
                else {
                    button.setText("Register for event");
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getText().toString().equals("Unregister for event")){
                    unRegisterForEvent();
                }
                else {
                    registerForEvent();
                }

            }
        });


        //set event type
        eventRef.child("/eventtype").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView textViewEventType = (TextView) findViewById(R.id.textViewEventType);
                textViewEventType.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //set datetime
        eventRef.child("/date").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                date = snapshot.getValue(String.class);

                //get event time
                eventRef.child("/startTime").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        time = snapshot.getValue(String.class);

                        //set datetime
                        TextView textViewEventDateTime = (TextView) findViewById(R.id.textViewEventDateTIme);
                        textViewEventDateTime.setText(date + " " + time + "hrs");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        //todo make it (participantslimit - getchildrencount) spots left
        //get participantlimit
        eventRef.child("/participantLimit").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView textViewParticipants = (TextView) findViewById(R.id.textViewParticipantsLeft);
                int participantLimit = snapshot.getValue(Integer.class);
                eventRef.child("/registeredParticipants").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        participantsLeft = participantLimit - (int) snapshot.getChildrenCount();
                        textViewParticipants.setText(String.valueOf(participantsLeft) + " spots left");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        eventRef.child("/fee").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView textViewRegFee = (TextView) findViewById(R.id.textViewRegFee);
                textViewRegFee.setText("$" + snapshot.getValue(String.class) + " registration fee");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        eventRef.child("/route").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView textViewRouteDesc = (TextView) findViewById(R.id.textViewRouteDesc);
                textViewRouteDesc.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        eventRef.child("/skillReq").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView textViewSkillReq = (TextView) findViewById(R.id.textViewSkillReq);
                eventSkill = snapshot.getValue(Integer.class).toString();
                textViewSkillReq.setText("Minimum skill level: " + eventSkill);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private boolean registerForEvent(){
        //need to validate skilllevel and participantlimit

        //need to write in 2 places  /users/uname/registeredEvents/clubname(value will be ename) and users/clubname/events/ename/registeredParticipants/uname

        //put in if, need to get spotsLeft and skilllevel and make sure not already registered
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference eventRef = db.getReference("users/" + clubName + "/events/" + ename + "/registeredParticipants/" + uname);
        DatabaseReference userRef = db.getReference("/users/" + uname + "/registeredEvents/" + clubName);
        DatabaseReference userSkillRef = db.getReference("/users/" + uname + "/Level");


        if (participantsLeft > 0){
            userSkillRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    participantSkill = snapshot.getValue(Integer.class);

                    if(participantSkill < Integer.valueOf(eventSkill)){
                        Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), "You are not skilled enough", Toast.LENGTH_SHORT);
                        toastUpdate.show();

                    }
                    else {
                        eventRef.setValue(true);
                        userRef.setValue(ename);
                        finish();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else {
            Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), "No space left", Toast.LENGTH_SHORT);
            toastUpdate.show();
            return false;
        }




        return false;
    }

    private void unRegisterForEvent(){
        //need to make sure user is in event

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference eventRef = db.getReference("users/" + clubName + "/events/" + ename + "/registeredParticipants/" + uname);
        DatabaseReference userRef = db.getReference("/users/" + uname + "/registeredEvents/" + clubName);

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if(snapshot.getValue(Boolean.class)){
                        userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    if(snapshot.getValue(String.class).equals(ename)){
                                        eventRef.removeValue();
                                        userRef.removeValue();
                                        finish();

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //need to erase in 2 places  /users/uname/registeredEvents and users/clubname/events/ename/registeredParticipants
    }
}