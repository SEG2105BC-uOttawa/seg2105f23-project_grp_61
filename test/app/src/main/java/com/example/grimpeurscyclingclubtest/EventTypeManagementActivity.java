package com.example.grimpeurscyclingclubtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventTypeManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_type_management);

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference eventRef = db.getReference("events/");
        List<String> eventList = new ArrayList<String>();
        EventTypeManagementActivity context = this;
        ListView listView = (ListView) findViewById(R.id.eventList);


        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    eventList.add(postSnapshot.getKey().toString());
                }

                String[] eventArr = new String[eventList.size()];
                eventArr = eventList.toArray(eventArr);


                ArrayAdapter adapter = new ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, eventArr);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String event = eventList.get(position);
                Intent intent = new Intent(getApplicationContext(), EventTypeSearchResultActivity.class);
                intent.putExtra("ename", event);
                startActivityForResult(intent, 0);
            }
        });

        // make events clickable

    }

    public void onSearchClick(View view) {
        EditText eTextSearch = (EditText) findViewById(R.id.eventText);
        String event = eTextSearch.getText().toString();

        if (event.equals("")) {
            return;
        } else {
            Intent intent = new Intent(getApplicationContext(), EventTypeSearchResultActivity.class);
            intent.putExtra("ename", event);
            startActivityForResult(intent, 0);
        }
    }
}