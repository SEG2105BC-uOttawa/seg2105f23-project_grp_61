package com.example.grimpeurscyclingclubtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class EventCreationActivity extends AppCompatActivity {

    String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);
        // inflate spinner

        Bundle bundle = getIntent().getExtras();
        uname = bundle.getString("uname");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference eventTypeRef = db.getReference("events/");
        List<String> eventTypeList = new ArrayList<String>();
        EventCreationActivity context = this;
        Spinner eventSpinner = (Spinner) findViewById(R.id.eventTypeSpinner);


        eventTypeRef.addValueEventListener(new ValueEventListener() {//inflate adapter
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventTypeList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    eventTypeList.add(postSnapshot.getKey().toString());
                }

                String[] eventArr = new String[eventTypeList.size()];
                eventArr = eventTypeList.toArray(eventArr);


                ArrayAdapter adapter = new ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, eventArr);
                eventSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }


    public boolean onCreateEventClick(View view){
        return false;
    }

}