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

        if(!ename.equals("")){
            EditText eTextEventName = (EditText) findViewById(R.id.editTextEventName);
            eTextEventName.setText(ename);

            //set all of the relevant fields

            //set event type
            DatabaseReference typeRef = db.getReference("users/" + uname + "/events/" + ename + "/eventtype");
            typeRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String type = snapshot.getValue(String.class);

                    eventSpinner.setSelection(eventTypeList.indexOf(type));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



            //set date

            DatabaseReference dateRef = db.getReference("users/" + uname + "/events/" + ename + "/date");
            dateRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String date = snapshot.getValue(String.class);
                    EditText editTextDate = (EditText) findViewById(R.id.editTextDate);
                    editTextDate.setText(date);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            ///set start time
            DatabaseReference timeRef = db.getReference("users/" + uname + "/events/" + ename + "/startTime");
            timeRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String time = snapshot.getValue(String.class);
                    EditText editTextTime = (EditText) findViewById(R.id.editTextTime);
                    editTextTime.setText(time);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            //set participant limit

            DatabaseReference limitRef = db.getReference("users/" + uname + "/events/" + ename + "/participantLimit");
            limitRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String limit = snapshot.getValue(Integer.class).toString();
                    EditText editTextLimit = (EditText) findViewById(R.id.editTextParticipantLimit);
                    editTextLimit.setText(limit);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //set reg fee

            DatabaseReference feeRef = db.getReference("users/" + uname + "/events/" + ename + "/fee");
            feeRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String fee = snapshot.getValue(String.class);
                    EditText editTextFee = (EditText) findViewById(R.id.editTextRegistrationFee);
                    editTextFee.setText(fee);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            DatabaseReference routeRef = db.getReference("users/" + uname + "/events/" + ename + "/route");
            routeRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String route = snapshot.getValue(String.class);
                    EditText editTextRoute = (EditText) findViewById(R.id.editTextRoute);
                    editTextRoute.setText(route);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }



    }


//    protected void onStart(){
//        super.onStart();
//
//        //populate the information
//    }


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
        EditText eTextRouteInfo = (EditText) findViewById(R.id.editTextRoute);

        String eventName = eTextEventName.getText().toString();
        String eventDate = eTextDate.getText().toString();
        String eventTime = eTextTime.getText().toString();
        String participantLimit = eTextParticipantLimit.getText().toString();
        String registrationFee = eTextRegistrationFee.getText().toString();
        String routeInfo = eTextRouteInfo.getText().toString();

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
        if(!validateString(routeInfo)){
            validated = false;
            Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), "Route info must not be empty", Toast.LENGTH_SHORT);
            toastUpdate.show();
        }



        if(!validated){
            //set all fields to blank and return false

            eTextEventName.setText("");
            eTextDate.setText("");
            eTextTime.setText("");
            eTextParticipantLimit.setText("");
            eTextRegistrationFee.setText("");
            eTextRouteInfo.setText("");

            return false;


        }



        else{
            // do this the same way mark did d2
            int participantLimitInt = Integer.parseInt(participantLimit);
            Event event = new Event(eventType, uname, eventName, eventDate, eventTime, participantLimitInt, registrationFee, routeInfo);

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

            DatabaseReference eRouteInfo = db.getReference(eventPath + eventName +  "/route");
            eRouteInfo.setValue(event.getRouteInfo()); // set event fee

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