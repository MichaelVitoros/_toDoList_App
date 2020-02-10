package com.example.rickyrayne.mylist;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText mText;
    private Button mButton;
    private ListView mListView;
    private ArrayList<String> jobs = new ArrayList<String>();

    private DatabaseReference mDatabase;
    private DatabaseReference newRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = (EditText) findViewById(R.id.editText1);
        mButton = (Button) findViewById(R.id.myBtn);
        mListView = (ListView) findViewById(R.id.mListView);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, jobs);

        mListView.setAdapter(arrayAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase = mDatabase.child("list");

        mButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String value = mText.getText().toString();

                newRef = mDatabase.push();
                newRef.child("item").setValue(value);

                mText.setText("");

                Toast.makeText(getApplicationContext(),
                       "'"+ value+ "'" + " has been added as a job", Toast.LENGTH_SHORT).show();
            }
        });

       /* mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childData : dataSnapshot.getChildren()) {
                    jobs.add((String) childData.child("item").getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = (String) dataSnapshot.child("item").getValue();

                jobs.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
