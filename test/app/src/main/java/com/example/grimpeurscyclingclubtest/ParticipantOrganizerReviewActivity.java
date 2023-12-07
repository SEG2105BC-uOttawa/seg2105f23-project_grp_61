package com.example.grimpeurscyclingclubtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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


public class ParticipantOrganizerReviewActivity extends Activity {
    String uname, clubName;
    EditText reviewRating, reviewText;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_organizer_review);
        Bundle bundle = getIntent().getExtras();
        uname = bundle.getString("uname");
        clubName = bundle.getString("clubName");
        reviewRating = (EditText) findViewById(R.id.starsEditText);
        reviewText= (EditText) findViewById(R.id.commentEditText);
    }
    public boolean onClick(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reviewRef = database.getReference("/users/"+ clubName + "/review/" + reviewText.getText().toString());


        //get the values from the fields;
        boolean validated = true;
        if(!validateStars(String.valueOf(reviewRating.getText()))){
            validated = false;
            //toast for error message
            Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), "Stars must be an integer 1-5", Toast.LENGTH_SHORT);
            toastUpdate.show();

        }


        if(!validated){
            //set text to blank
            reviewText.setText("");
            reviewRating.setText("");
            return false; //YOOOO WE CAN TEST THE VALIDATION CLASS AND THIS THATS MORE CASES
        }
        else{
            reviewRef.setValue(reviewRating.getText().toString());
            finish();
        }
        return validated;
    }
}
