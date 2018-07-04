package com.example.abc.smartattendance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PresentActivity2 extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private TextView tvscore2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present2);
        databaseReference= FirebaseDatabase.getInstance().getReference("data2");
        tvscore2=(TextView)findViewById(R.id.tvscorepresent2);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer adc = dataSnapshot.getValue(Integer.class);
                tvscore2.setText("Score:"+  adc);
               // System.out.println(adc+"=========================================");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

    }}
