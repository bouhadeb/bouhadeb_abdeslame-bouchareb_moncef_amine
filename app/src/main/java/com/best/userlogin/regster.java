package com.best.userlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class regster extends AppCompatActivity {
    //vars
    TextView loginbtn;
    EditText password;
    EditText user;
    EditText email;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window. FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_regster);
        //cars value
        user = findViewById(R.id.name);
        password = findViewById(R.id.passedit);
        loginbtn = findViewById(R.id.login);
        email = findViewById(R.id.emailedit);
        fauth = FirebaseAuth.getInstance();


    }
    // login errors
    public void login(View view){
        String demail = email.getText().toString().trim();
        String dpassword = password.getText().toString().trim();
        if(user.length() == 0){
            user.setError("enter user name");
            return;
        }
        if(email.length() == 0){
            email.setError("enter your email");
            return;
        }
        if(password.length() == 0){
            password.setError("enter password");
            return;
        }
        if (password.length() < 6){
            password.setError("password need to be more than 6 car");
            return;
        }

        //register user to database
        fauth.createUserWithEmailAndPassword(demail,dpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(regster.this, "account created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),maps.class));
                } else {
                    Toast.makeText(regster.this, "Error !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}