package com.example.abc.smartattendance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText etmail,etpassword;
    private Button btnregister;
    private TextView tvsignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();

        init();

    }
    private void init(){
        etmail=(EditText)findViewById(R.id.etemail);
        etpassword=(EditText)findViewById(R.id.etpassword);
        btnregister=(Button) findViewById(R.id.btnregister);
        tvsignin=(TextView) findViewById(R.id.tvsignin);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regiteruser();
            }
        });
        tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open signin act
                finish();
                Intent i=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });


    }
    private void regiteruser(){
        final String email=etmail.getText().toString().trim();
        final String password=etpassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(MainActivity.this,"please enter the email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(MainActivity.this,"please enter the password",Toast.LENGTH_LONG).show();
            return;
        }
      /*  progressDialog.setMessage("Registering user...........");
        progressDialog.show();*/
        firebaseAuth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            finish();
                            Intent i=new Intent(MainActivity.this,EmployeesList.class);
                            startActivity(i);


                        }
                        else{
                            Toast.makeText(MainActivity.this,"Please check the internet connection",Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }
}
