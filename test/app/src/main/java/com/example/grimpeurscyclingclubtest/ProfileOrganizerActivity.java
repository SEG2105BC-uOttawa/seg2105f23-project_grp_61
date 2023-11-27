package com.example.grimpeurscyclingclubtest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.grimpeurscyclingclubtest.TextInputValidation.*;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileOrganizerActivity extends AppCompatActivity {

    // Declare ValueEventListener as a class variable
    private ValueEventListener userListener;

    EditText phoneEdit;
    EditText socialEdit;
    EditText hoursEdit;
    EditText contactEdit;

    ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_organizer);

        phoneEdit = findViewById(R.id.PhoneNumberedit);
        socialEdit = findViewById(R.id.SocialMediaedit);
        hoursEdit = findViewById(R.id.WorkHoursedit);
        profilePic = findViewById(R.id.profileImage);
        contactEdit = findViewById(R.id.ContactNameedit);

        Bundle bundle = getIntent().getExtras();
        String uname = bundle.getString("uname");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference orgRef = db.getReference("users/" + uname);

        userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                OrganizerAccount users = dataSnapshot.getValue(OrganizerAccount.class);

                String DrawableName = users.getProfileImageId();
                int resID = getResources().getIdentifier(DrawableName, "drawable", getPackageName());

                TextView userText = findViewById(R.id.UserNameText);

                userText.setText(uname);
                profilePic.setImageResource(resID);
                phoneEdit.setText(users.getPhoneNumber());
                socialEdit.setText(users.getSocialMedia());
                hoursEdit.setText(users.getWorkHours());
                contactEdit.setText(users.getContactName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handling onCancelled logic if needed
            }
        };
        orgRef.addValueEventListener(userListener);

        phoneEdit.setEnabled(false);
        socialEdit.setEnabled(false);
        hoursEdit.setEnabled(false);
        contactEdit.setEnabled(false);
    }

    public void onClickEdit(View view) {
        phoneEdit.setEnabled(true);
        socialEdit.setEnabled(true);
        hoursEdit.setEnabled(true);
        contactEdit.setEnabled(true);
    }

    public void onClickSaveEdit(View view) {
        phoneEdit.setEnabled(false);
        socialEdit.setEnabled(false);
        hoursEdit.setEnabled(false);
        contactEdit.setEnabled(false);

        String PhoneNumber = phoneEdit.getText().toString();
        String SocialMedia = socialEdit.getText().toString();
        String WorkHours = hoursEdit.getText().toString();
        String ContactName = contactEdit.getText().toString();

        Bundle bundle = getIntent().getExtras();
        String uname = bundle.getString("uname");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference infoUser = db.getReference("users/" + uname);

        if (validateWorkingHours(WorkHours) && validateSocialMedia(SocialMedia) && validatePhoneNumberWithRegex(PhoneNumber)) {
            // Remove the previous ValueEventListener before adding a new one
            infoUser.removeEventListener(userListener);

            infoUser.addValueEventListener(userListener);

            DatabaseReference PhoneNumberRef = db.getReference("users/" + uname + "/PhoneNumber");
            DatabaseReference SocialMediaRef = db.getReference("users/" + uname + "/SocialMedia");
            DatabaseReference HoursWorkedRef = db.getReference("users/" + uname + "/WorkHours");
            DatabaseReference contactNameRef = db.getReference("users/" + uname + "/ContactName");

            PhoneNumberRef.setValue(PhoneNumber);
            SocialMediaRef.setValue(SocialMedia);
            HoursWorkedRef.setValue(WorkHours);
            contactNameRef.setValue(ContactName);

            phoneEdit.setText(PhoneNumber);
            socialEdit.setText(SocialMedia);
            hoursEdit.setText(WorkHours);
            contactEdit.setText(ContactName);
        } else {
            Toast.makeText(this, "Save failed, Invalid Information", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickProfileImage(View view) {
        Intent intent = new Intent(getApplicationContext(), ProfileImage.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) return;

        ImageView avatarImage = findViewById(R.id.profileImage);

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

        Bundle bundle = getIntent().getExtras();
        String uname = bundle.getString("uname");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference infoUser = db.getReference("users/" + uname);


        infoUser.removeEventListener(userListener);

        infoUser.addValueEventListener(userListener);

        DatabaseReference profilePictureRef = db.getReference("users/" + uname + "/ProfileImageId");
        profilePictureRef.setValue(drawableName);
    }
}
