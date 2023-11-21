package com.example.grimpeurscyclingclubtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventManagementActivity extends AppCompatActivity {

    String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_management);

        Bundle bundle = getIntent().getExtras();
        uname = bundle.getString("uname");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference eventRef = db.getReference("users/" + uname + "events/");
        List<String> eventTypeList = new ArrayList<String>();
        EventManagementActivity context = this;
        ListView eventTypeListView = (ListView) findViewById(R.id.orgEventList);


        eventRef.addValueEventListener(new ValueEventListener() {//inflate adapter
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventTypeList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    eventTypeList.add(postSnapshot.getKey().toString());
                }

                String[] eventArr = new String[eventTypeList.size()];
                eventArr = eventTypeList.toArray(eventArr);


                ArrayAdapter adapter = new ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, eventArr);
                eventTypeListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        //need to inflate adapter or
    }

    public void onNewEventClick(View view){
        //open event creation activity
        Intent intent = new Intent(getApplicationContext(), EventCreationActivity.class);
        intent.putExtra("uname", uname);
        startActivity(intent);

    }

}