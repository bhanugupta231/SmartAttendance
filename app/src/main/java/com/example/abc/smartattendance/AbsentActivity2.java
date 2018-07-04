package com.example.abc.smartattendance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AbsentActivity2 extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private TextView tvscore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absent2);
        databaseReference= FirebaseDatabase.getInstance().getReference("data2");
        tvscore=(TextView)findViewById(R.id.tvscoreabsent2);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer adc = dataSnapshot.getValue(Integer.class);
                tvscore.setText("Score:"+  adc);
             //   System.out.println(adc+"=========================================");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

    }}
