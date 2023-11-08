package com.example.grimpeurscyclingclubtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class UserManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
    }

    public void onSearchClick(View view) {
        EditText eTextSearch = (EditText) findViewById(R.id.usernameText);
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