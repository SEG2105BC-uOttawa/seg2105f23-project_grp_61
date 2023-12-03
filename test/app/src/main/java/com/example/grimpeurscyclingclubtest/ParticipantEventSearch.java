package com.example.grimpeurscyclingclubtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParticipantEventSearch extends AppCompatActivity {

    String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_event_search);

        Bundle bundle = getIntent().getExtras();
        uname = bundle.getString("uname");

        // inflate spinner with sort types
        Spinner sortTypeSpinner = (Spinner) findViewById(R.id.sortTypeSpinner);
        String[] sortTypes = new String[]{"Club", "Event Type", "All Events"};
        ArrayAdapter sortTypeAdapter = new ArrayAdapter<String>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,sortTypes);

        sortTypeSpinner.setAdapter(sortTypeAdapter);

        //populate event types
        Spinner eventTypeSortSpinner = (Spinner) findViewById(R.id.eventTypeSortSpinner);
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://grimpeurscyclingclubtest-default-rtdb.firebaseio.com/");
        DatabaseReference eventTypeRef = db.getReference("eventtype/");
        List<String> eventTypeList = new ArrayList<String>();
        ParticipantEventSearch context = this;

        ListView listView = (ListView) findViewById(R.id.searchableListView);
        DatabaseReference userRef = db.getReference("users/");
        List<String> organizerList = new ArrayList<String>();

        List<String> eventList = new ArrayList<String>();


        SearchView searchView = (SearchView) findViewById(R.id.searchView);




        //Adapter adapter;

        //probably need to move adapter up here and then change edittext to searchview
        //todo add onclicklistener and check which sort is selected



        eventTypeRef.addValueEventListener(new ValueEventListener() {//inflate adapter
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventTypeList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    eventTypeList.add(postSnapshot.getKey().toString());
                }

                String[] eventArr = new String[eventTypeList.size()];
                eventArr = eventTypeList.toArray(eventArr);


                ArrayAdapter adapter = new ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, eventArr);
                eventTypeSortSpinner.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        sortTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //TextView textView = (TextView) findViewById(R.id.textView4);
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //gonna wanna set adapter also wanna make sure second spinner is only visible when sorting by type
                switch (position){
                    case 0://sort by club
                        eventTypeSortSpinner.setVisibility(View.INVISIBLE);
                        searchView.setQuery("",true);
                        userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {//todo custom adapter for image, set onclick for esads club screen
                                organizerList.clear();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    if (postSnapshot.child("/role").getValue(String.class).equals("organizer")) {//postSnapshot.getKey().toString().equals("admin") || postSnapshot.getKey().toString().equals("participant")
                                        organizerList.add(postSnapshot.getKey().toString());
                                    }
                                    //organizerList.add(postSnapshot.getKey().toString());

                                }

                                String[] organizerArr = new String[organizerList.size()];
                                organizerArr = organizerList.toArray(organizerArr);


                                ArrayAdapter adapter = new ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, organizerArr);
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        break;
                    case 1://sort by event type
                        eventTypeSortSpinner.setVisibility(View.VISIBLE);
                        searchView.setQuery("",true);
                        //need to filter by eventtype
                        userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {//todo custom adapter for image, set onclick for esads club screen
                                eventList.clear();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    if (postSnapshot.child("/role").getValue(String.class).equals("organizer")) {//postSnapshot.getKey().toString().equals("admin") || postSnapshot.getKey().toString().equals("participant")
                                        //get events
                                        for (DataSnapshot postSnapshot2 : postSnapshot.child("/events").getChildren()){//DataSnapshot postSnapshot2 : dataSnapshot.child("/events").getChildren()

                                            if(postSnapshot2.child("/eventtype").getValue(String.class).equals(eventTypeSortSpinner.getSelectedItem().toString())){// need to have an update listener for event type spinner
                                                String test = postSnapshot2.getKey().toString();
                                                eventList.add(test);
                                            }


                                        }

                                        //eventList.add(postSnapshot.getKey().toString());
                                    }
                                    //organizerList.add(postSnapshot.getKey().toString());

                                }

                                String[] eventArr = new String[eventList.size()];
                                eventArr = eventList.toArray(eventArr);


                                ArrayAdapter adapter = new ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, eventArr);
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        //

                        //listView.setAdapter(new ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, new String[]{"test"}));
                        break;

                    case 2://sort by all events
                        eventTypeSortSpinner.setVisibility(View.INVISIBLE);
                        searchView.setQuery("",true);
                        userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {//todo custom adapter for image, set onclick for esads club screen
                                eventList.clear();
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    if (postSnapshot.child("/role").getValue(String.class).equals("organizer")) {//postSnapshot.getKey().toString().equals("admin") || postSnapshot.getKey().toString().equals("participant")
                                        //get events
                                        for (DataSnapshot postSnapshot2 : postSnapshot.child("/events").getChildren()){//DataSnapshot postSnapshot2 : dataSnapshot.child("/events").getChildren()
                                            String test = postSnapshot2.getKey().toString();
                                            eventList.add(test);
                                        }

                                        //eventList.add(postSnapshot.getKey().toString());
                                    }
                                    //organizerList.add(postSnapshot.getKey().toString());

                                }

                                String[] eventArr = new String[eventList.size()];
                                eventArr = eventList.toArray(eventArr);


                                ArrayAdapter adapter = new ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, eventArr);

                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        eventTypeSortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sortTypeSpinner.getSelectedItem().toString().equals("Event Type")){
//                    Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), "Route info must not be empty", Toast.LENGTH_SHORT);
//                    toastUpdate.show();

                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {//todo custom adapter for image, set onclick for esads club screen
                            eventList.clear();
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                if (postSnapshot.child("/role").getValue(String.class).equals("organizer")) {//postSnapshot.getKey().toString().equals("admin") || postSnapshot.getKey().toString().equals("participant")
                                    //get events
                                    for (DataSnapshot postSnapshot2 : postSnapshot.child("/events").getChildren()){//DataSnapshot postSnapshot2 : dataSnapshot.child("/events").getChildren()

                                        if(postSnapshot2.child("/eventtype").getValue(String.class).equals(eventTypeSortSpinner.getSelectedItem().toString())){// need to have an update listener for event type spinner
                                            String test = postSnapshot2.getKey().toString();
                                            eventList.add(test);
                                        }


                                    }

                                    //eventList.add(postSnapshot.getKey().toString());
                                }
                                //organizerList.add(postSnapshot.getKey().toString());

                            }

                            String[] eventArr = new String[eventList.size()];
                            eventArr = eventList.toArray(eventArr);


                            ArrayAdapter adapter = new ArrayAdapter<String>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, eventArr);
                            listView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(sortTypeSpinner.getSelectedItem().toString().equals("Club")){
                    //todo foreward to esads club screen

                    String clubName = parent.getItemAtPosition(position).toString();
                    Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), clubName + " to be implemented", Toast.LENGTH_SHORT);
                    toastUpdate.show();
                }
                else {

                    String ename = parent.getItemAtPosition(position).toString();
                    Intent intent = new Intent(context, ParticipantEventSearchResultActivity.class);
                    intent.putExtra("uname", uname);
                    intent.putExtra("ename", ename);
                    startActivity(intent);
                }
            }
        });




//        editTextSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(s.length()!=0){
//                    //filter
//                    Toast toastUpdate = Toast.makeText(getApplication().getBaseContext(), "Route info must not be empty", Toast.LENGTH_SHORT);
//                    toastUpdate.show();
//                }else{
//
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        ListView listView = (ListView) findViewById(R.id.searchableListView);

        Spinner sortTypeSpinner = (Spinner) findViewById(R.id.sortTypeSpinner);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();

                if(!newText.equals("")){
                    adapter.getFilter().filter(newText);
                }

                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
//                searchView.setQuery("", true);
//
//                ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();
//                adapter.getFilter().filter("");
//
                String sort = sortTypeSpinner.getSelectedItem().toString();

                if(sort == "Club"){
                    sortTypeSpinner.setSelection(2);
//                    sortTypeSpinner.setSelection(0);

                } else if (sort == "Event Type") {
                    sortTypeSpinner.setSelection(2);
//                    sortTypeSpinner.setSelection(1);

                }
                else {
                    sortTypeSpinner.setSelection(0);
//                    sortTypeSpinner.setSelection(2);

                }

                return false;
            }
        });
    }
}