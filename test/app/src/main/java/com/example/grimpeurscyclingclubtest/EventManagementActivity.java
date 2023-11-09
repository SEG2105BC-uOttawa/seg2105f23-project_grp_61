package com.example.grimpeurscyclingclubtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EventManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_management);
    }

    public void onSearchClick(View view) {
        EditText eTextSearch = (EditText) findViewById(R.id.eventText);
        String event = eTextSearch.getText().toString();

        if (event.equals("")) {
            return;
        } else {
            Intent intent = new Intent(getApplicationContext(), EventSearchResultActivity.class);
            intent.putExtra("ename", event);
            startActivityForResult(intent, 0);
        }
    }
}