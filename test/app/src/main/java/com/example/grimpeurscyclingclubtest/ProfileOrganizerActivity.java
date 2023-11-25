package com.example.grimpeurscyclingclubtest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileOrganizerActivity extends AppCompatActivity {

    EditText phoneEdit;
    EditText socialEdit;
    EditText hoursEdit;

    ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_organizer);

        phoneEdit = (EditText) findViewById(R.id.PhoneNumberedit);
        socialEdit = (EditText) findViewById(R.id.SocialMediaedit);
        hoursEdit = (EditText) findViewById(R.id.WorkHoursedit);
        profilePic = (ImageView) findViewById(R.id.profileImage);

        Bundle bundle = getIntent().getExtras();
        String uname = bundle.getString("uname");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference orgRef = db.getReference("users2/"+uname);



        ValueEventListener orgListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                OrganizerAccount users = dataSnapshot.getValue(OrganizerAccount.class);

                String DrawableName = users.getProfileImageId();
                int resID = getResources().getIdentifier(DrawableName, "drawable", getPackageName());


                TextView userText = (TextView) findViewById(R.id.UserNameText);


                userText.setText(uname);
                profilePic.setImageResource(resID);
                phoneEdit.setText(users.getPhoneNumber());
                socialEdit.setText(users.getSocialMedia());
                hoursEdit.setText(users.getWorkHours());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        orgRef.addValueEventListener(orgListener);

        phoneEdit.setEnabled(false);
        socialEdit.setEnabled(false);
        hoursEdit.setEnabled(false);

    }

    //allows the user to edit the information
    public void onClickEdit(View view){
        phoneEdit = (EditText) findViewById(R.id.SocialMediaedit);
        socialEdit = (EditText) findViewById(R.id.PhoneNumberedit);
        hoursEdit = (EditText) findViewById(R.id.WorkHoursedit);

        //Makes the editText editable
        phoneEdit.setEnabled(true);
        socialEdit.setEnabled(true);
        hoursEdit.setEnabled(true);
    }

    //Saves information placed by user
    public void onClickSaveEdit(View view){

        phoneEdit = (EditText) findViewById(R.id.PhoneNumberedit);
        socialEdit = (EditText) findViewById(R.id.SocialMediaedit);
        hoursEdit = (EditText) findViewById(R.id.WorkHoursedit);

        String phoneNumber = phoneEdit.getText().toString();
        String socialMedia = socialEdit.getText().toString();
        String workHours = hoursEdit.getText().toString();

        Bundle bundle = getIntent().getExtras();
        String uname = bundle.getString("uname");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference infoUser = db.getReference("users2/"+uname);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseReference PhoneNumberRef = db.getReference("users2/" + uname + "/PhoneNumber");
                DatabaseReference SocialMediaRef = db.getReference("users2/" + uname + "/SocialMedia");
                DatabaseReference HoursWorkedRef = db.getReference("users2/" + uname + "/WorkHours");

                PhoneNumberRef.setValue(phoneNumber);
                SocialMediaRef.setValue(socialMedia);
                HoursWorkedRef.setValue(workHours);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        infoUser.addValueEventListener(userListener);

        //setText of editText
        phoneEdit.setText(phoneNumber);
        socialEdit.setText(socialMedia);
        hoursEdit.setText(workHours);


        //Makes the editText uneditable
        phoneEdit.setEnabled(false);
        socialEdit.setEnabled(false);
        hoursEdit.setEnabled(false);
    }

    public void onClickBack(View view){
        finish();
    }

    public void onClickProfileImage(View view){
        Intent intent = new Intent(getApplicationContext(), ProfileImage.class);
        startActivityForResult (intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) return;
        //Getting the Avatar Image we show to our users
        ImageView avatarImage = (ImageView) findViewById(R.id.profileImage);
        //Figuring out the correct image
        String drawableName = "ic_logo_00";
        int imageID = data.getIntExtra("imageID", R.id.teamid00);
        if (imageID == R.id.teamid00) {
            drawableName = "ic_logo_00";
        } else if (imageID == R.id.teamid01) {
            drawableName = "ic_logo_01";
        } else if (imageID == R.id.teamid02) {
            drawableName = "ic_logo_02";
        } else if (imageID == R.id.teamid03) {
            drawableName = "ic_logo_03";
        } else if (imageID == R.id.teamid04) {
            drawableName = "ic_logo_04";
        } else if (imageID == R.id.teamid05) {
            drawableName = "ic_logo_05";
        } else {
            drawableName = "ic_logo_00";
        }
        int resID = getResources().getIdentifier(drawableName, "drawable", getPackageName());
        avatarImage.setImageResource(resID);

        Bundle bundle = getIntent().getExtras();
        String uname = bundle.getString("uname");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference infoUser = db.getReference("users2/"+uname);

        String finalDrawableName = drawableName;
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseReference profilePictureRef = db.getReference("users2/" + uname + "/ProfileImageId");

                profilePictureRef.setValue(finalDrawableName);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        infoUser.addValueEventListener(userListener);


    }

}