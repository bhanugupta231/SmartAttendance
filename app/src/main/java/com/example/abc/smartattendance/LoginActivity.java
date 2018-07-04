package com.example.abc.smartattendance;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText etmail,etpassword;
    private Button btnsignin;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();

        init();

    }
    private void init(){
        etmail=(EditText)findViewById(R.id.etemailsignin);
        etpassword=(EditText)findViewById(R.id.etpasswordsignin);
        btnsignin=(Button)findViewById(R.id.btnsignin);
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=etmail.getText().toString().trim();
                final  String password=etpassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this,"Please enter the email  ",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"please enter the password  ",Toast.LENGTH_LONG).show();
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email,password).
                        addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    Intent i=new Intent(LoginActivity.this,EmployeesList.class);
                                    startActivity(i);

                                }
                                else{
                                    Toast.makeText(LoginActivity.this,"Please enter the correct email id and password  ",Toast.LENGTH_LONG).show();

                                }

                            }
                        });
            }
        });

    }
}
