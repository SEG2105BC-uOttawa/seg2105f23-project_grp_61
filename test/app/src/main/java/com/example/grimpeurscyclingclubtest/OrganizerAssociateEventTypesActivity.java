package com.example.grimpeurscyclingclubtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrganizerAssociateEventTypesActivity extends AppCompatActivity {

    String uname;

    FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
    DatabaseReference eventRef = db.getReference("eventtype/");


    List<String> eventTypeList = new ArrayList<String>();

    List<String> associatedEventTypeList = new ArrayList<String>();

    String[] eventArr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_associate_event_types);

        Bundle bundle = getIntent().getExtras();
        uname = bundle.getString("uname");

        OrganizerAssociateEventTypesActivity context = this;
        ListView listView = (ListView) findViewById(R.id.eTypeList);
        ListView listView1 =  (ListView) findViewById(R.id.listVview);


        //inflate list with event types

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventTypeList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    eventTypeList.add(postSnapshot.getKey().toString());
                }

                eventArr = new String[eventTypeList.size()];
                eventArr = eventTypeList.toArray(eventArr);


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

        DatabaseReference clubETypeRef = db.getReference("users/" + uname + "/eventtypes");


        clubETypeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                associatedEventTypeList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    associatedEventTypeList.add(postSnapshot.getKey().toString());
                }

                eventArr = new String[associatedEventTypeList.size()];
                eventArr = associatedEventTypeList.toArray(eventArr);


                ArrayAdapter adapter = new ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, eventArr);
                listView1.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                enableProfileEventType(eventTypeList.get(position));
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                disableProfileEventType(eventTypeList.get(position));
                return false;
            }
        });

        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                disableProfileEventType(eventTypeList.get(position));
                return false;
            }
        });







        //populate associatedEventTypeList
                //set textview to associated
    }




    private void enableProfileEventType(String eventType){
        //see if it is there

        //associatedEventTypeList.add(eventType);

        DatabaseReference clubEventTypesRef = db.getReference("users/" + uname + "/eventtypes/" + eventType);

        clubEventTypesRef.setValue(true);

        Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), "Associated with " + eventType, Toast.LENGTH_SHORT);
        toastUpdate.show();
        //see value if there

        //case when true

        //case when false
    }

    private void disableProfileEventType(String eventType){
        //see if it is there

        //associatedEventTypeList.remove(eventType);

        DatabaseReference clubEventTypesRef = db.getReference("users/" + uname + "/eventtypes/" + eventType);

        clubEventTypesRef.removeValue();

        Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), "Dissociated with " + eventType, Toast.LENGTH_SHORT);
        toastUpdate.show();
        //see value if there

        //case when true

        //case when false
    }

}