package com.example.grimpeurscyclingclubtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.grimpeurscyclingclubtest.TextInputValidation.*;



public class EventCreationActivity extends AppCompatActivity {

    String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);
        // inflate spinner

        Bundle bundle = getIntent().getExtras();
        uname = bundle.getString("uname");


        String ename = bundle.getString("ename"); // need to do like if(ename.equals(""){}

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference eventTypeRef = db.getReference("eventtype/");
        List<String> eventTypeList = new ArrayList<String>();
        EventCreationActivity context = this;
        Spinner eventSpinner = (Spinner) findViewById(R.id.eventTypeSpinner);

        if(!ename.equals("")){
            EditText eTextEventName = (EditText) findViewById(R.id.editTextEventName);
            eTextEventName.setText(ename);
        }


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
        //validate all fields
        /*
        - event name
        - event type
        - date
        - starttime
        - participant limit
        - registration fees


        there should be empty fields for registrants and route, for now set them to null ig
         */

        //need to grab values from fields

        EditText eTextEventName = (EditText) findViewById(R.id.editTextEventName);
        EditText eTextDate = (EditText) findViewById(R.id.editTextDate);
        EditText eTextTime = (EditText) findViewById(R.id.editTextTime);
        EditText eTextParticipantLimit = (EditText) findViewById(R.id.editTextParticipantLimit);
        EditText eTextRegistrationFee = (EditText) findViewById(R.id.editTextRegistrationFee);

        String eventName = eTextEventName.getText().toString();
        String eventDate = eTextDate.getText().toString();
        String eventTime = eTextTime.getText().toString();
        String participantLimit = eTextParticipantLimit.getText().toString();
        String registrationFee = eTextRegistrationFee.getText().toString();

        Spinner eventTypeSpinner = (Spinner) findViewById(R.id.eventTypeSpinner);
        String eventType = eventTypeSpinner.getSelectedItem().toString();

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        String eventPath = "users/" + uname + "/events/";//"events/";//andrei
        DatabaseReference eventRef = db.getReference(eventPath);

        boolean validated = true;

        if(!validateString(eventName)){
            validated = false;
            Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), "Event name validation failed, name field may be empty", Toast.LENGTH_SHORT);
            toastUpdate.show();

        }
        if(!validateDate(eventDate)){//rn it doesnt check if the date is in the future
            validated = false;
            Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), "Date must be in DD/MM/YYYY, include slashes", Toast.LENGTH_SHORT);
            toastUpdate.show();
        }
        if(!validate24hrTime(eventTime)){
            validated = false;
            Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), "Time must range from 0000-2359", Toast.LENGTH_SHORT);
            toastUpdate.show();
        }
        if(!validateParticipantLimit(participantLimit)){
            validated = false;
            Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), "Participant limit must range from 1-1000", Toast.LENGTH_SHORT);
            toastUpdate.show();
        }
        if(!validateRegistrationFee(registrationFee)){
            validated = false;
            Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), "Registration fee must range from 0-999.99", Toast.LENGTH_SHORT);
            toastUpdate.show();
        }



        if(!validated){
            //set all fields to blank and return false

            eTextEventName.setText("");
            eTextDate.setText("");
            eTextTime.setText("");
            eTextParticipantLimit.setText("");
            eTextRegistrationFee.setText("");

            return false;


        }



        else{
            // do this the same way mark did d2
            int participantLimitInt = Integer.parseInt(participantLimit);
            Event event = new Event(eventType, uname, eventName, eventDate, eventTime, participantLimitInt, registrationFee);

//            //eventRef.setValue(eventName); // set name
//
////            DatabaseReference eHostRef = db.getReference(eventPath + eventName + eventName);
////            eHostRef.setValue(event.getEventOrganizer()); // set organizer
//
            DatabaseReference eTypeRef = db.getReference(eventPath +  eventName + "/eventtype");
            eTypeRef.setValue(eventType); // set event type

            DatabaseReference eDateRef = db.getReference(eventPath + eventName + "/date");
            eDateRef.setValue(event.getEventDate()); // set event date

            DatabaseReference eTimeRef = db.getReference(eventPath +  eventName + "/startTime");
            eTimeRef.setValue(event.getEventTime()); // set event start time

            DatabaseReference eParticipantLimit = db.getReference(eventPath + eventName +  "/participantLimit");
            eParticipantLimit.setValue(event.getParticipantLimit()); // set event limit

            DatabaseReference eRegFee = db.getReference(eventPath + eventName +  "/fee");
            eRegFee.setValue(event.getRegistrationFee()); // set event fee

//            DatabaseReference dbr = db.getReference(eventPath + "/" + event.getEventName());
//            dbr.setValue(event); // i guess what we did in d2 was just black magic

            Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), "event updated", Toast.LENGTH_SHORT);
            toastUpdate.show();

            return true;
        }
    }

    public void onClickDelete(View view){
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        EditText editTextEventName = (EditText) findViewById(R.id.editTextEventName) ;
        DatabaseReference eventRef = db.getReference("users/" + uname + "/events/" + editTextEventName.getText().toString());
        eventRef.removeValue();

        Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), "event deleted", Toast.LENGTH_SHORT);
        toastUpdate.show();
    }

}