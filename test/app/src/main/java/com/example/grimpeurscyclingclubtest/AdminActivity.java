package com.example.grimpeurscyclingclubtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Bundle bundle = getIntent().getExtras();
        String uname = bundle.getString("uname");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference roleRef = db.getReference("users/"+uname + "/role");



        ValueEventListener roleListener = new ValueEventListener() {
            boolean finished = false;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (finished) {
                    return;
                }

                // Get Post object and use the values to update the UI
                String role = dataSnapshot.getValue(String.class);
                // ..

                AdminAccount userAccount = new AdminAccount(uname);

                finished = true;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        roleRef.addValueEventListener(roleListener);

    }

    public void onClickUser(View view) {
        Intent intent = new Intent(getApplicationContext(), UserManagementActivity.class);
        startActivity(intent);
    }

    public void onClickEventType(View view) {
        Intent intent = new Intent(getApplicationContext(), EventTypeManagementActivity.class);
        startActivity(intent);
    }

}