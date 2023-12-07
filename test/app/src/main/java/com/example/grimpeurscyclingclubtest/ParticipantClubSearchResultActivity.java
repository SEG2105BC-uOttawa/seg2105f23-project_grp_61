package com.example.grimpeurscyclingclubtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.data.DataBufferSafeParcelable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ParticipantClubSearchResultActivity extends AppCompatActivity {

    //todo validate joining event skilllevel and spotsleft

    String uname;
    String ename;
    String clubName;


    String date;
    String[] eventArr;
    String[] reviewArr;
    String time;

    int participantSkill;
    String eventSkill;
    int participantsLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_club_search_result);

        Bundle bundle = getIntent().getExtras();
        uname = bundle.getString("uname");
        clubName = bundle.getString("clubName");

        View submitButton = (Button) findViewById(R.id.goToReviewButton);

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference eventRef = db.getReference("users/" + clubName + "/events/");
        DatabaseReference reviewRef = db.getReference("users/" + clubName + "/review/");
        DatabaseReference clubRef = db.getReference("users/" + clubName);
        DatabaseReference imageRef = db.getReference("users/" + clubName + "/ProfileImageId");


        ImageView profilePic = (ImageView) findViewById(R.id.imageView2);


        ValueEventListener roleListener = new ValueEventListener() {
            boolean finished = false;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (finished) {
                    return;
                }

                String DrawableName = dataSnapshot.getValue(String.class);
                int resID;
                if (DrawableName == null) {
                    resID = getResources().getIdentifier("ic_logo_00", "drawable", getPackageName());
                } else {
                    resID = getResources().getIdentifier(DrawableName, "drawable", getPackageName());
                }
                profilePic.setImageResource(resID);

                finished = true;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        imageRef.addValueEventListener(roleListener);

        List<String> eventList = new ArrayList<>();
        List<String> reviewList = new ArrayList<>();

        TextView textViewName = (TextView) findViewById(R.id.textViewOrganizerName);
        textViewName.setText(clubName);

        ParticipantClubSearchResultActivity context = this;

//        Button button = (Button) findViewById(R.id.button6);
        eventArr = new String[eventList.size()];
        reviewArr = new String[reviewList.size()];

        ListView eventListView = (ListView) findViewById(R.id.eventview2);
        ListView reviewListView = (ListView) findViewById(R.id.reviewsView);
        eventRef.addValueEventListener(new ValueEventListener() {//inflate adapter
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                eventList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    eventList.add(postSnapshot.getKey().toString());
                }


                eventArr = eventList.toArray(eventArr);


                ArrayAdapter adapter = new ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, eventArr);
                eventListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        reviewRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                reviewList.clear();
                for ( DataSnapshot postSnapshot : snapshot.getChildren()) {
                    reviewList.add(postSnapshot.getValue().toString() + " - " + postSnapshot.getKey().toString());
                }

                reviewArr = reviewList.toArray(reviewArr);

                ArrayAdapter adapter = new ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, reviewArr);
                reviewListView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        clubRef.child("/SocialMedia").addValueEventListener(new ValueEventListener() {
            boolean finished = false;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (finished) {
                    return;
                }

                TextView SMtextview = (TextView) findViewById(R.id.SMtextview);
                SMtextview.setText(snapshot.getValue(String.class));

                finished = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ename = eventArr[position];
                Intent intent = new Intent(context, ParticipantEventSearchResultActivity.class);
                intent.putExtra("uname", uname);
                intent.putExtra("ename", ename);
                intent.putExtra("clubName", clubName);

                startActivity(intent);

            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewButtonOnClick(v);
            }
        });
    }

        public void reviewButtonOnClick(View view){

            Intent intent = new Intent(getApplicationContext(), ParticipantOrganizerReviewActivity.class);
            intent.putExtra("uname", uname);
            intent.putExtra("clubName", clubName);

            startActivity(intent);


        }
    }
