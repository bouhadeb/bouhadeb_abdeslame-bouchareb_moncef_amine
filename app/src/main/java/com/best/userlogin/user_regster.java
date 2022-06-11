package com.best.userlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class user_regster extends AppCompatActivity {
    public static final String TAG = "TAG";
    //vars
    TextView loginbtn;
    EditText password;
    EditText name;
    EditText email;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_regster);
        //cars value
        name = findViewById(R.id.name);
        password = findViewById(R.id.passedit);
        //loginbtn = findViewById(R.id.login);
        email = findViewById(R.id.emailedit);
        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


    }

    public void logpage(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    // login errors
    public void Login(View view){
        String demail = email.getText().toString().trim();
        String dpassword = password.getText().toString().trim();
        String dname = name.getText().toString();
        if(name.length() == 0){
            name.setError("enter user name");
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
                    Toast.makeText(user_regster.this, "account created", Toast.LENGTH_SHORT).show();
                    userID = fauth.getCurrentUser().getUid();
                    DocumentReference documentReference = fstore.collection("user").document(userID);
                    Map<String,Object> user = new  HashMap<>();
                    user.put("name",dname);
                    user.put("email",demail);
                    user.put("user","1");
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG,"user created for :"+ userID);
                        }
                    });
                    startActivity(new Intent(getApplicationContext(),maps.class));
                } else {
                    Toast.makeText(user_regster.this, "Error !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}