package com.example.grimpeurscyclingclubtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventTypeSearchResultActivity extends AppCompatActivity {

    String ename;
    //boolean recentDelete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_type_search_result);
        Bundle bundle = getIntent().getExtras();
        ename = bundle.getString("ename");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference eventRef = db.getReference("eventtype/"+ename);

        ValueEventListener eventListener = new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                EventType event = dataSnapshot.getValue(EventType.class);
                EditText labelText = (EditText) findViewById(R.id.labelEditText);
                labelText.setText(ename);


                if (event == null) {
                    Toast toast = Toast.makeText(getApplication().getBaseContext(), "Event not found...", Toast.LENGTH_SHORT);
                    toast.show();
                } else {

//                    EditText ageEdit = (EditText) findViewById(R.id.ageEditText);
//                    EditText paceEdit = (EditText) findViewById(R.id.PaceEditText);
//                    EditText levelEdit = (EditText) findViewById(R.id.levelEditText);
                    EditText descEdit = (EditText) findViewById(R.id.descEditText);

//                    ageEdit.setText(Integer.toString(event.getAgeReq()));
//                    paceEdit.setText(Double.toString(event.getPaceReq()));
//                    levelEdit.setText(Integer.toString(event.getLevelReq()));
                    descEdit.setText(event.getDescription());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        eventRef.addValueEventListener(eventListener);

    }

    public void onClickDelete(View view) {

        //recentDelete = true;

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference eventRef = db.getReference("eventtype/"+ename);

        eventRef.removeValue();
        Toast toast = Toast.makeText(getApplication().getBaseContext(), "Event deleted!", Toast.LENGTH_SHORT);
        toast.show();

        EditText labelText = (EditText) findViewById(R.id.labelEditText);
//        EditText ageEdit = (EditText) findViewById(R.id.ageEditText);
//        EditText paceEdit = (EditText) findViewById(R.id.PaceEditText);
//        EditText levelEdit = (EditText) findViewById(R.id.levelEditText);
        EditText descEdit = (EditText) findViewById(R.id.descEditText);

        labelText.setText("");
//        ageEdit.setText("");
//        paceEdit.setText("");
//        levelEdit.setText("");
        descEdit.setText("");

    }

    public void onClickUpdate(View view) {

        final Toast[] toast = {Toast.makeText(getApplication().getBaseContext(), "Event name taken!", Toast.LENGTH_SHORT)};

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference eventRef = db.getReference("eventtype/"+ename);

        EditText labelText = (EditText) findViewById(R.id.labelEditText);
//        EditText ageEdit = (EditText) findViewById(R.id.ageEditText);
//        EditText paceEdit = (EditText) findViewById(R.id.PaceEditText);
//        EditText levelEdit = (EditText) findViewById(R.id.levelEditText);
        EditText descEdit = (EditText) findViewById(R.id.descEditText);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account account = snapshot.getValue(Account.class);
                if (account == null || ename.equals(labelText.getText().toString())) { // added check if event name is taken


                    EventType event = new EventType(labelText.getText().toString(), descEdit.getText().toString()); //ageEdit.getText().toString(), paceEdit.getText().toString(), levelEdit.getText().toString()

                    //if (recentDelete) {
                    //    recentDelete = false;
                    //    return;
                    //}

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