package com.example.grimpeurscyclingclubtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParticipantClubSearchResultActivity extends AppCompatActivity {

    //todo validate joining event skilllevel and spotsleft

    String uname;
    String ename;
    String clubName;


    String date;
    String[] eventArr;
    String time;

    int participantSkill;
    String eventSkill;
    int participantsLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_club_search_result);

        Bundle bundle = getIntent().getExtras();
        uname = bundle.getString("uname");
        clubName = bundle.getString("clubName");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference eventRef = db.getReference("users/" + clubName + "/events/");

        List<String> eventList = new ArrayList<>();

        TextView textViewName = (TextView) findViewById(R.id.textViewOrganizerName);
        textViewName.setText(clubName);

        ParticipantClubSearchResultActivity context = this;

//        Button button = (Button) findViewById(R.id.button6);
        eventArr = new String[eventList.size()];

        ListView eventListView = (ListView) findViewById(R.id.eventview2);
        eventRef.addValueEventListener(new ValueEventListener() {//inflate adapter
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    eventList.add(postSnapshot.getKey().toString());
                }


                eventArr = eventList.toArray(eventArr);


                ArrayAdapter adapter = new ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, eventArr);
                eventListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });


        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ename = eventArr[position];
                Intent intent = new Intent(context, ParticipantEventSearchResultActivity.class);
                intent.putExtra("uname", uname);
                intent.putExtra("ename", ename);
                intent.putExtra("clubName", clubName);

                startActivity(intent);

            }
        });

//        searchView.setQuery("",true);
        //need to filter by eventtype
//        userRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {////todo custom adapter for image, set onclick for esads club screen
//                eventList.clear();
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    if (postSnapshot.child("/role").getValue(String.class).equals("organizer")) {//postSnapshot.getKey().toString().equals("admin") || postSnapshot.getKey().toString().equals("participant")
//                        //get events
//                        for (DataSnapshot postSnapshot2 : postSnapshot.child("/events").getChildren()){//DataSnapshot postSnapshot2 : dataSnapshot.child("/events").getChildren()
//
//                            if(postSnapshot2.child("/eventtype").getValue(String.class).equals(eventTypeSortSpinner.getSelectedItem().toString())){// need to have an update listener for event type spinner
//                                String test = postSnapshot2.getKey().toString();
//                                eventList.add(test);
//                            }
//
//
//                        }
//
//                        //eventList.add(postSnapshot.getKey().toString());
//                    }
//                    //organizerList.add(postSnapshot.getKey().toString());
//
//                }
//
//                String[] eventArr = new String[eventList.size()];
//                eventArr = eventList.toArray(eventArr);
//
//
//                ArrayAdapter adapter = new ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, eventArr);
//                listView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        //
//
//        //listView.setAdapter(new ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, new String[]{"test"}));
//        break;

//        eventRef.child("/registeredParticipants/" + uname).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    button.setText("Unregister for event");
//                }
//                else {
//                    button.setText("Register for event");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (button.getText().toString().equals("Unregister for event")){
//                    unRegisterForEvent();
//                }
//                else {
//                    registerForEvent();
//                }
//
//                registerForEvent();
//            }
//        });


        //set event type
//        eventRef.child("/eventtype").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                TextView textViewEventType = (TextView) findViewById(R.id.textViewEventType);
//                textViewEventType.setText(snapshot.getValue(String.class));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        //set datetime
//        eventRef.child("/date").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                date = snapshot.getValue(String.class);
//
//                //get event time
//                eventRef.child("/startTime").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        time = snapshot.getValue(String.class);
//
//                        //set datetime
//                        TextView textViewEventDateTime = (TextView) findViewById(R.id.textViewEventDateTIme);
//                        textViewEventDateTime.setText(date + " " + time + "hrs");
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        //todo make it (participantslimit - getchildrencount) spots left
        //get participantlimit
//        eventRef.child("/participantLimit").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                TextView textViewParticipants = (TextView) findViewById(R.id.textViewParticipantsLeft);
//                int participantLimit = snapshot.getValue(Integer.class);
//                eventRef.child("/registeredParticipants").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        participantsLeft = participantLimit - (int) snapshot.getChildrenCount();
//                        textViewParticipants.setText(String.valueOf(participantsLeft) + " spots left");
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        eventRef.child("/fee").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                TextView textViewRegFee = (TextView) findViewById(R.id.textViewRegFee);
//                textViewRegFee.setText("$" + snapshot.getValue(String.class) + " registration fee");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        eventRef.child("/route").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                TextView textViewRouteDesc = (TextView) findViewById(R.id.textViewRouteDesc);
//                textViewRouteDesc.setText(snapshot.getValue(String.class));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        eventRef.child("/skillReq").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                TextView textViewSkillReq = (TextView) findViewById(R.id.textViewSkillReq);
//                eventSkill = snapshot.getValue(Integer.class).toString();
//                textViewSkillReq.setText("Minimum skill level: " + eventSkill);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

//    private boolean registerForEvent(){
//        //need to validate skilllevel and participantlimit
//
//        //need to write in 2 places  /users/uname/registeredEvents/clubname(value will be ename) and users/clubname/events/ename/registeredParticipants/uname
//
//        //put in if, need to get spotsLeft and skilllevel and make sure not already registered
//        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
//        DatabaseReference eventRef = db.getReference("users/" + clubName + "/events/" + ename + "/registeredParticipants/" + uname);
//        DatabaseReference userRef = db.getReference("/users/" + uname + "/registeredEvents/" + clubName);
//        DatabaseReference userSkillRef = db.getReference("/users/" + uname + "/Level");
//
//
//        if (participantsLeft > 0){
//            userSkillRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    participantSkill = snapshot.getValue(Integer.class);
//
//                    if(participantSkill < Integer.valueOf(eventSkill)){
//                        Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), "You are not skilled enough", Toast.LENGTH_SHORT);
//                        toastUpdate.show();
//
//                    }
//                    else {
//                        eventRef.setValue(true);
//                        userRef.setValue(ename);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//
//        }
//        else {
//            Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), "No space left", Toast.LENGTH_SHORT);
//            toastUpdate.show();
//            return false;
//
//        }
//
//
//
//
//        return false;
//    }

//    private void unRegisterForEvent(){
//        //need to make sure user is in event
//
//        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
//        DatabaseReference eventRef = db.getReference("users/" + clubName + "/events/" + ename + "/registeredParticipants/" + uname);
//        DatabaseReference userRef = db.getReference("/users/" + uname + "/registeredEvents/" + clubName);
//
//        eventRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.getValue(Boolean.class)){
//                    userRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if(snapshot.getValue(String.class).equals(ename)){
//                                eventRef.removeValue();
//                                userRef.removeValue();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



        //need to erase in 2 places  /users/uname/registeredEvents and users/clubname/events/ename/registeredParticipants
    }
