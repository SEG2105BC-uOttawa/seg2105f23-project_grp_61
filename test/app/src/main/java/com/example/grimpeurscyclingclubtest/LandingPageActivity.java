package com.example.grimpeurscyclingclubtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LandingPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        Bundle bundle = getIntent().getExtras();
        String uname = bundle.getString("uname");

        TextView textView = findViewById(R.id.landingPageTextVIew);

        textView.setText(uname);

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference roleRef = db.getReference("users/"+uname + "/role");



        ValueEventListener roleListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                String role = dataSnapshot.getValue(String.class);
                // ..

                textView.setText("Welcome " + uname + ", you are logged in as " + role);

                if (role == "admin"){
                    AdminAccount userAccount = new AdminAccount(uname);
                } else if (role == "organizer"){
                    OrganizerAccount userAccount = new OrganizerAccount(uname);
                } else {
                    ParticipantAccount userAccount = new ParticipantAccount(uname);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        roleRef.addValueEventListener(roleListener);





    }
}