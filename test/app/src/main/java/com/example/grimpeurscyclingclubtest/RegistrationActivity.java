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
        View PhoneNumber = findViewById(R.id.PhoneNumber);
        View SocialMedia = findViewById(R.id.SocialMedia);
        View TextMandatory = findViewById(R.id.AdditionalInformation);

        organizerButton =  findViewById(R.id.radioButton2);
        if (organizerButton.isChecked()){
            //When its organizer these boxes pop up in activity
            PhoneNumber.setVisibility(View.VISIBLE);
            SocialMedia.setVisibility(View.VISIBLE);
            TextMandatory.setVisibility(View.VISIBLE);
        }
    }

    public void OnCheckParticipant(View view){
        View PhoneNumber =  findViewById(R.id.PhoneNumber);
        View SocialMedia =  findViewById(R.id.SocialMedia);
        View TextMandatory =  findViewById(R.id.AdditionalInformation);

        participantButton =  findViewById(R.id.radioButton);

        if (participantButton.isChecked()){
            //When its organizer these boxes pop up in activity
            PhoneNumber.setVisibility(View.INVISIBLE);
            SocialMedia.setVisibility(View.INVISIBLE);
            TextMandatory.setVisibility(View.INVISIBLE);
        }
    }


    public void OnClickRegister(View view){

        EditText eTextEmail = (EditText) findViewById(R.id.registerEmailInput);
        EditText eTextPass = (EditText) findViewById(R.id.registerPasswordInput);
        EditText eTextUname = (EditText) findViewById(R.id.registerUnameInput);
        EditText eTextPhoneNumber = (EditText) findViewById(R.id.PhoneNumber);
        EditText eTextSocialMedia = (EditText) findViewById(R.id.SocialMedia);

        String email = eTextEmail.getText().toString();
        String pass = eTextPass.getText().toString();
        String username = eTextUname.getText().toString();
        String role = null;
        String SocialMedia = "";
        String PhoneNumber = "";

        participantButton =  findViewById(R.id.radioButton);
        organizerButton =  findViewById(R.id.radioButton2);

        if(!participantButton.isChecked() && !organizerButton.isChecked()){

        }
        else if (participantButton.isChecked()){
            role = "participant";
        }
        else if (organizerButton.isChecked()){
            role = "organizer";
            SocialMedia = eTextSocialMedia.getText().toString();
            if(!eTextPhoneNumber.getText().toString().equals(""))
                PhoneNumber = eTextPhoneNumber.getText().toString();
        }

        //makes sures the fields are not empty for organizer
        if(role.equals("organizer") && (SocialMedia.equals("") || PhoneNumber.equals(""))){
            Toast.makeText(this,"There is still unfilled information",Toast.LENGTH_LONG).show();
        }
        else if(role.equals("organizer") && (!validatePhoneNumberWithRegex((PhoneNumber))
                && (!validateSocialMedia(SocialMedia)))){
            Toast.makeText(this,"Unvalidated phone number or social link",Toast.LENGTH_LONG).show();

        }
        else if(validateEmailWithRegex(email)&&validateUsernameWithRegex(username)&&validatePass(pass)&&
                role != null){
            register(username.toLowerCase(),pass,email,role,PhoneNumber,SocialMedia);
        }


    }



    public void register(String username, String password, String email, String role, String PhoneNumber, String SocialMedia){

        final Toast[] toast = {Toast.makeText(getApplication().getBaseContext(), "Username taken!", Toast.LENGTH_SHORT)};

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference userRef = db.getReference("users2/"+username);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                OrganizerAccount orgaccount = snapshot.getValue(OrganizerAccount.class);
                if (account == null) { // added check if username is taken
                    DatabaseReference newUserRoleRef = db.getReference("users2/"+username+"/role");
                    DatabaseReference newUserEmailRef = db.getReference("users2/"+username + "/email");
                    DatabaseReference newUserPasswordRef = db.getReference("users2/"+username + "/password");
                    DatabaseReference newUserPhoneNumberRef = db.getReference("users2/"+username + "/PhoneNumber");
                    DatabaseReference newUserSocialMediaRef = db.getReference("users2/"+username + "/SocialMedia");

                    newUserRoleRef.setValue(role);
                    newUserEmailRef.setValue(email);
                    newUserPasswordRef.setValue(password);
                    if(orgaccount == null) {
                        newUserPhoneNumberRef.setValue(PhoneNumber);
                        newUserSocialMediaRef.setValue(SocialMedia);
                    }


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