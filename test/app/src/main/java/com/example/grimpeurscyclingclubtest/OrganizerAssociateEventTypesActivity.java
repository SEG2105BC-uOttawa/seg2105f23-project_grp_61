package com.example.grimpeurscyclingclubtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class OrganizerAssociateEventTypesActivity extends AppCompatActivity {

    String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_associate_event_types);

        Bundle bundle = getIntent().getExtras();
        uname = bundle.getString("uname");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference eventRef = db.getReference("events/");
        List<String> eventTypeList = new ArrayList<String>();
        OrganizerAssociateEventTypesActivity context = this;
        ListView listView = (ListView) findViewById(R.id.eTypeList);


        //inflate list with event types
    }


    private void toggleProfileEventType(String eventType){

    }

}