package com.example.grimpeurscyclingclubtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import static com.example.grimpeurscyclingclubtest.TextInputValidation.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    RadioButton organizerButton;
    RadioButton participantButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    //The two onClick methods below help swap the visibly of the fields information
    public void OnCheckOrganizer(View view){
        EditText PhoneNumber = findViewById(R.id.PhoneNumber);
        EditText SocialMedia = findViewById(R.id.SocialMedia);
        View TextMandatory = findViewById(R.id.AdditionalInformation);

        organizerButton =  findViewById(R.id.radioButton2);
        if (organizerButton.isChecked()){
            //When its organizer these boxes pop up in activity
            PhoneNumber.setHint("Phone Number");
            SocialMedia.setHint("Social Media");
            PhoneNumber.setVisibility(View.VISIBLE);
            SocialMedia.setVisibility(View.VISIBLE);
            TextMandatory.setVisibility(View.VISIBLE);
        }
    }

    public void OnCheckParticipant(View view){
        EditText Level =  findViewById(R.id.PhoneNumber);
        EditText Age =  findViewById(R.id.SocialMedia);
        View TextMandatory =  findViewById(R.id.AdditionalInformation);

        participantButton =  findViewById(R.id.radioButton);

        if (participantButton.isChecked()){
            //When its organizer these boxes pop up in activity
            Age.setHint("Age");
            Level.setHint("Level");
            Level.setVisibility(View.VISIBLE);
            Age.setVisibility(View.VISIBLE);
            TextMandatory.setVisibility(View.VISIBLE);

        }
    }


    public void OnClickRegister(View view){

        EditText eTextEmail = (EditText) findViewById(R.id.registerEmailInput);
        EditText eTextPass = (EditText) findViewById(R.id.registerPasswordInput);
        EditText eTextUname = (EditText) findViewById(R.id.registerUnameInput);
        EditText eTextPhoneNumber_Level = (EditText) findViewById(R.id.PhoneNumber);
        EditText eTextSocialMedia_Age = (EditText) findViewById(R.id.SocialMedia);

        String email = eTextEmail.getText().toString();
        String pass = eTextPass.getText().toString();
        String username = eTextUname.getText().toString();
        String role = null;
        String SocialMedia = "";
        String PhoneNumber = "";
        String Age = "";
        String Level = "";

        participantButton =  findViewById(R.id.radioButton);
        organizerButton =  findViewById(R.id.radioButton2);

        if(!participantButton.isChecked() && !organizerButton.isChecked()){

        }
        else if (participantButton.isChecked()){
            role = "participant";
            Age = eTextSocialMedia_Age.getText().toString();
            Level = eTextPhoneNumber_Level.getText().toString();
        }
        else if (organizerButton.isChecked()){
            role = "organizer";
            SocialMedia = eTextSocialMedia_Age.getText().toString();
            if(!eTextPhoneNumber_Level.getText().toString().equals(""))
                PhoneNumber = eTextPhoneNumber_Level.getText().toString();
        }

        //makes sures the fields are not empty for organizer
        if(role.equals("organizer") && (SocialMedia.equals("") || PhoneNumber.equals(""))){
            Toast.makeText(this,"There is still unfilled information",Toast.LENGTH_LONG).show();
        }
        else if(role.equals("participant") && (Age.equals("") || Level.equals(""))){
            Toast.makeText(this,"There is still unfilled information",Toast.LENGTH_LONG).show();
        }
        else if(role.equals("organizer") && (validatePhoneNumberWithRegex(PhoneNumber)==false || validateSocialMedia(SocialMedia)==false)){
            Toast.makeText(this,"Unvalidated phone number or social link",Toast.LENGTH_LONG).show();
        }
        else if(validateEmailWithRegex(email)&&validateUsernameWithRegex(username)&&validatePass(pass)&&role != null){
            register(username.toLowerCase(),pass,email,role,PhoneNumber,SocialMedia,Age,Level);
        }


    }



    public void register(String username, String password, String email, String role, String PhoneNumber, String SocialMedia, String Age, String Level){

        final Toast[] toast = {Toast.makeText(getApplication().getBaseContext(), "Username taken!", Toast.LENGTH_SHORT)};

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference userRef = db.getReference("users/"+username);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                OrganizerAccount orgaccount = snapshot.getValue(OrganizerAccount.class);
                if (account == null) { // added check if username is taken
                    DatabaseReference newUserRoleRef = db.getReference("users/"+username+"/role");
                    DatabaseReference newUserEmailRef = db.getReference("users/"+username + "/email");
                    DatabaseReference newUserPasswordRef = db.getReference("users/"+username + "/password");
                    DatabaseReference newUserPhoneNumberRef = db.getReference("users/"+username + "/PhoneNumber");
                    DatabaseReference newUserSocialMediaRef = db.getReference("users/"+username + "/SocialMedia");
                    DatabaseReference newUserAgeRef = db.getReference("users/"+username+"/Age");
                    DatabaseReference newUserLevelRef = db.getReference("users/"+username+"/Level");

                    newUserRoleRef.setValue(role);
                    newUserEmailRef.setValue(email);
                    newUserPasswordRef.setValue(password);
                    if(orgaccount == null) {
                        newUserPhoneNumberRef.setValue(PhoneNumber);
                        newUserSocialMediaRef.setValue(SocialMedia);
                    }
                    newUserAgeRef.setValue(Age);
                    newUserLevelRef.setValue(Level);



                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivityForResult(intent, 0);
                    toast[0] = null;
                } else {
                    if (toast[0] != null) {
                        toast[0].show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        userRef.addValueEventListener(userListener);

    }
}