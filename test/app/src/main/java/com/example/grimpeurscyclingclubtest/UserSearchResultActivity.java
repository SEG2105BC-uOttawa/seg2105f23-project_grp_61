package com.example.grimpeurscyclingclubtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserSearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search_result);
        Bundle bundle = getIntent().getExtras();
        String uname = bundle.getString("uname");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference userRef = db.getReference("users/"+uname);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Account user = dataSnapshot.getValue(Account.class);

                if (user == null) {
                    Toast toast = Toast.makeText(getApplication().getBaseContext(), "User not found...", Toast.LENGTH_SHORT);
                    toast.show();
                } else {

                    TextView emailText = (TextView) findViewById(R.id.emailText);
                    TextView userText = (TextView) findViewById(R.id.userText);
                    TextView roleText = (TextView) findViewById(R.id.roleText);

                    emailText.setText(emailText.getText() + user.getEmail());
                    userText.setText(userText.getText() + uname);
                    roleText.setText(roleText.getText() + user.getRole());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        userRef.addValueEventListener(userListener);

    }

    public void onClickDelete(View view) {
        Bundle bundle = getIntent().getExtras();
        String uname = bundle.getString("uname");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference userRef = db.getReference("users/"+uname);

        userRef.removeValue();
        Toast toast = Toast.makeText(getApplication().getBaseContext(), "User deleted!", Toast.LENGTH_SHORT);
        toast.show();

        TextView emailText = (TextView) findViewById(R.id.emailText);
        TextView userText = (TextView) findViewById(R.id.userText);
        TextView roleText = (TextView) findViewById(R.id.roleText);

        emailText.setText("email: ");
        userText.setText("user: ");
        roleText.setText("role: ");

        finish();

    }
}