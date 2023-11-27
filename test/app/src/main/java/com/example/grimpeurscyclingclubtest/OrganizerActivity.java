package com.example.grimpeurscyclingclubtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrganizerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer);

        Bundle bundle = getIntent().getExtras();
        String uname = bundle.getString("uname");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference imageRef = db.getReference("users2/"+uname+"/ProfileImageId");


        ImageView profilePic = (ImageView) findViewById(R.id.imageView);


        ValueEventListener roleListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //String role = dataSnapshot.getValue(String.class);
                // ..

                OrganizerAccount userAccount = new OrganizerAccount(uname);

                String DrawableName = dataSnapshot.getValue(String.class);
                int resID;
                if(DrawableName == null){
                    resID = getResources().getIdentifier("ic_logo_00", "drawable", getPackageName());
                }
                else {
                    resID = getResources().getIdentifier(DrawableName, "drawable", getPackageName());
                }
                profilePic.setImageResource(resID);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        imageRef.addValueEventListener(roleListener);






    }

    public void onClickAvatarButton(View view) {

        Bundle bundle = getIntent().getExtras();
        String uname = bundle.getString("uname");

        Intent intent = new Intent(getApplicationContext(), ProfileOrganizerActivity.class);
        intent.putExtra("uname",uname);
        startActivity(intent);
    }


}