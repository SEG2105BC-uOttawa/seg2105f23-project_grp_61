package com.example.grimpeurscyclingclubtest;

import static com.example.grimpeurscyclingclubtest.TextInputValidation.validateSkillLevel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ParticipantActivity extends AppCompatActivity {


    String uname;
    SeekBar seekBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        uname = bundle.getString("uname");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);
        ListView listView = (ListView) findViewById(R.id.eventList);

        List<String> eventList = new ArrayList<String>();
        List<String> clubList = new ArrayList<String>();
        ParticipantActivity context = this;

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference eventRef = db.getReference("users/"+uname + "/registeredEvents");

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList.clear();
                clubList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    eventList.add(postSnapshot.getKey().toString());
                    clubList.add(postSnapshot.getValue(String.class));
                }

                String[] eventArr = new String[eventList.size()];
                eventArr = eventList.toArray(eventArr);

                System.out.println(eventArr.length);

                ArrayAdapter adapter = new ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, eventArr);
                ListView listView = (ListView) findViewById(R.id.eventList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String event = eventList.get(position);
                String clubName = clubList.get(position);
                Intent intent = new Intent(context, ParticipantEventSearchResultActivity.class);
                intent.putExtra("uname", uname);
                intent.putExtra("ename", event);
                intent.putExtra("clubName", clubName);

                startActivity(intent);
            }
        });

        seekBar = (SeekBar)findViewById(R.id.seekbar);
        TextView editTextSkillLevel = (TextView) findViewById(R.id.levelView);

        seekBar.setOnSeekBarChangeListener(
                        new SeekBar
                                .OnSeekBarChangeListener() {

                            // When the progress value has changed
                            @Override
                            public void onProgressChanged(
                                    SeekBar seekBar,
                                    int progress,
                                    boolean fromUser)
                            {
                                //System.out.println("on progress");
                                if (fromUser){

                                    editTextSkillLevel.setText(String.valueOf(progress+1));

                                }

                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar)
                            {

                                //System.out.println("start tracking");
                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar)
                            {
                                setSkillLVL();
                                //System.out.println("on stop");
                            }
                        });

        //FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference roleRef = db.getReference("users/"+uname + "/role");
        DatabaseReference skillRef = db.getReference("users/" + uname + "/Level");

        skillRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView editTextSkillLevel = (TextView) findViewById(R.id.levelView);

                String level = snapshot.getValue(Long.class).toString();
                seekBar.setProgress(Integer.parseInt(level)-1);

                editTextSkillLevel.setText(level);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

    private void setSkillLVL(){
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference skillRef = db.getReference("users/"+ uname + "/Level");

        TextView editTextSkillLevel = (TextView) findViewById(R.id.levelView);
        String level = editTextSkillLevel.getText().toString();
        if(validateSkillLevel(level)){
            skillRef.setValue(Integer.valueOf(level));
        }
    }
}