package com.example.grimpeurscyclingclubtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference userRef = db.getReference("users/");
        List<String> userList = new ArrayList<String>();
        UserManagementActivity context = this;
        ListView listView = (ListView) findViewById(R.id.userList);


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    if (postSnapshot.getKey().toString().equals("admin")) {
                        continue;
                    }
                    userList.add(postSnapshot.getKey().toString());
                }

                String[] userArr = new String[userList.size()];
                userArr = userList.toArray(userArr);


                ArrayAdapter adapter = new ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, userArr);
                ListView listView = (ListView) findViewById(R.id.userList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String uname = userList.get(position);
                Intent intent = new Intent(getApplicationContext(), UserSearchResultActivity.class);
                intent.putExtra("uname", uname);
                startActivityForResult(intent, 0);
                return true;
            }
        });



    }

    public void onSearchClick(View view) {
        EditText eTextSearch = (EditText) findViewById(R.id.eventText);
        String username = eTextSearch.getText().toString();

        if (username.equals("")) {
            return;
        } else {
            Intent intent = new Intent(getApplicationContext(), UserSearchResultActivity.class);
            intent.putExtra("uname", username);
            startActivityForResult(intent, 0);
        }
    }
}