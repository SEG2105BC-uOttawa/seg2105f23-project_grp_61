package com.example.grimpeurscyclingclubtest;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class OrganizerAccount extends Account{

    String PhoneNumber;
    String ContactName;
    String SocialMedia;
    String WorkHours;
//    List<EventType> joinedEvents;

    String ProfileImageId;

    public OrganizerAccount() {
        ProfileImageId = "ic_logo_00";
    }

    public OrganizerAccount(String username) {
        super(username);

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference phoneNumberRef = db.getReference("users" + username + "/PhoneNumber");
        DatabaseReference socialMediaRef = db.getReference("users" + username + "/SocialMedia");
        DatabaseReference workHoursRef = db.getReference("users" +username + "/WorkHours");
        DatabaseReference profileImageRef = db.getReference("users" +username + "/ProfileImageId");
        DatabaseReference contactNameRef = db.getReference("users" +username+ "/ContactName");

        //Change once list is setup for organizer
        //DatabaseReference joinedEventsRef = db.getReference("users" + username + "/joinedEvents");
        ValueEventListener valueListener1 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                PhoneNumber = dataSnapshot.getValue(String.class);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Log.e("FirebaseError", "Data retrieval failed: " + databaseError.getMessage());
            }
        };
        phoneNumberRef.addValueEventListener(valueListener1);

        ValueEventListener valueListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                SocialMedia = dataSnapshot.getValue(String.class);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Log.e("FirebaseError", "Data retrieval failed: " + databaseError.getMessage());
            }
        };
        socialMediaRef.addValueEventListener(valueListener2);

        ValueEventListener valueListener3 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                WorkHours = dataSnapshot.getValue(String.class);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        workHoursRef.addValueEventListener(valueListener3);

        ValueEventListener valueListener4 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ProfileImageId = dataSnapshot.getValue(String.class);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Log.e("FirebaseError", "Data retrieval failed: " + databaseError.getMessage());
            }
        };
        profileImageRef.addValueEventListener(valueListener4);

        ValueEventListener valueListener5 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                ContactName = dataSnapshot.getValue(String.class);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Log.e("FirebaseError", "Data retrieval failed: " + databaseError.getMessage());
            }
        };
        contactNameRef.addValueEventListener(valueListener5);


    }


    public String getPhoneNumber(){
        return PhoneNumber;
    }
    public String getSocialMedia(){
        return SocialMedia;
    }
    public String getWorkHours(){return WorkHours;}

//    public List<EventType> getJoinedEvents(){return joinedEvents;}

    public String getProfileImageId() {
        return ProfileImageId;
    }

    public String getContactName(){return ContactName;}
}