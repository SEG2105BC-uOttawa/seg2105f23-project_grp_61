package com.example.grimpeurscyclingclubtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventSearchResultActivity extends AppCompatActivity {

    String ename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_search_result);
        Bundle bundle = getIntent().getExtras();
        ename = bundle.getString("ename");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference eventRef = db.getReference("events/"+ename);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Event event = dataSnapshot.getValue(Event.class);


                if (event == null) {
                    Toast toast = Toast.makeText(getApplication().getBaseContext(), "Event not found...", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    /*
                    TextView emailText = (TextView) findViewById(R.id.emailText);
                    TextView userText = (TextView) findViewById(R.id.userText);
                    TextView roleText = (TextView) findViewById(R.id.roleText);
                     */

                    TextView labelText = (TextView) findViewById(R.id.labelEditText);
                    EditText ageEdit = (EditText) findViewById(R.id.ageEditText);
                    EditText paceEdit = (EditText) findViewById(R.id.PaceEditText);
                    EditText levelEdit = (EditText) findViewById(R.id.levelEditText);
                    EditText typeEdit = (EditText) findViewById(R.id.typeEditText);

                    /*
                    emailText.setText(emailText.getText() + event.getEmail());
                    userText.setText(userText.getText() + ename);
                    roleText.setText(roleText.getText() + event.getRole());
                     */

                    labelText.setText(ename);
                    ageEdit.setText(Integer.toString(event.getAgeReq()));
                    paceEdit.setText(Double.toString(event.getPaceReq()));
                    levelEdit.setText(Integer.toString(event.getLevelReq()));
                    if (event.getIsOffroad()) {
                        typeEdit.setText("offroad");
                    } else {
                        typeEdit.setText("road");
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        eventRef.addValueEventListener(eventListener);

    }

    public void onClickDelete(View view) {

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference eventRef = db.getReference("events/"+ename);

        eventRef.removeValue();
        Toast toast = Toast.makeText(getApplication().getBaseContext(), "Event deleted!", Toast.LENGTH_SHORT);
        toast.show();

        TextView labelText = (TextView) findViewById(R.id.labelEditText);
        EditText ageEdit = (EditText) findViewById(R.id.ageEditText);
        EditText paceEdit = (EditText) findViewById(R.id.PaceEditText);
        EditText levelEdit = (EditText) findViewById(R.id.levelEditText);
        EditText typeEdit = (EditText) findViewById(R.id.typeEditText);

        labelText.setText("");
        ageEdit.setText("");
        paceEdit.setText("");
        levelEdit.setText("");
        typeEdit.setText("");

    }

    public void onClickUpdate(View view) {

        final Toast[] toast = {Toast.makeText(getApplication().getBaseContext(), "Event name taken!", Toast.LENGTH_SHORT)};

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference eventRef = db.getReference("events/"+ename);

        TextView labelText = (TextView) findViewById(R.id.labelEditText);
        EditText ageEdit = (EditText) findViewById(R.id.ageEditText);
        EditText paceEdit = (EditText) findViewById(R.id.PaceEditText);
        EditText levelEdit = (EditText) findViewById(R.id.levelEditText);
        EditText typeEdit = (EditText) findViewById(R.id.typeEditText);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                if (account == null || ename.equals(labelText.getText().toString())) { // added check if event name is taken


                    Event event = new Event(ageEdit.getText().toString(), paceEdit.getText().toString(), levelEdit.getText().toString(), typeEdit.getText().toString());

                    if (!event.isEmpty()) {
                        eventRef.setValue(event);
                    }

                    Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), "Event updated!", Toast.LENGTH_SHORT);
                    toastUpdate.show();

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
        eventRef.addValueEventListener(userListener);



    }
}