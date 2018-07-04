package com.example.abc.smartattendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeesList extends AppCompatActivity {
private LinearLayout linearlay1,linearlay2;
    private FirebaseAuth firebaseAuth;
    private Button btnlogout;
    private DatabaseReference databaseReference1,databaseReference2;

    private TextView tvscoreemp1,tvscoreemp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_list);
        firebaseAuth=FirebaseAuth.getInstance();
       /* databaseReference1= FirebaseDatabase.getInstance().getReference("data");
        databaseReference2= FirebaseDatabase.getInstance().getReference("data2");
tvscoreemp1=(TextView)findViewById(R.id.tvscoreemp1);
tvscoreemp2=(TextView)findViewById(R.id.tvscoreemp2);*/
        btnlogout=(Button)findViewById(R.id.btnlogout);
        linearlay1=(LinearLayout)findViewById(R.id.linearlay1);
        linearlay2=(LinearLayout)findViewById(R.id.linearlay2);
        linearlay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(EmployeesList.this,AttendanceTaker.class);
                startActivity(i);
            }
        });
        linearlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(EmployeesList.this,AttendanceTaker2.class);
                startActivity(i);
            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                Intent i=new Intent(EmployeesList.this,LoginActivity.class);
                startActivity(i);
            }
        });


    }
}
