package com.example.grimpeurscyclingclubtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrganizerActivity extends AppCompatActivity {

    String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer);

        Bundle bundle = getIntent().getExtras();
        uname = bundle.getString("uname");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference roleRef = db.getReference("users/" + uname + "/role/");


        ValueEventListener roleListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                String role = dataSnapshot.getValue(String.class);
                // ..

                OrganizerAccount userAccount = new OrganizerAccount(uname);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        roleRef.addValueEventListener(roleListener);





    }

    public void onEventManagementClick(View view){
        Intent intent = new Intent(getApplicationContext(), EventManagementActivity.class);
        intent.putExtra("uname", uname);
        startActivity(intent);
    }
}