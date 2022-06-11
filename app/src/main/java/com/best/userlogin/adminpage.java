package com.best.userlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class adminpage extends AppCompatActivity {
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);
        fauth = FirebaseAuth.getInstance();
    }

    public void admin(View v){
        Intent i = new Intent(this,admin.class);
        startActivity(i);
    }
    public void add(View v){
        Intent t = new Intent(this,add_atm_poste.class);
        startActivity(t);
    }
    public void logout(View v){
        fauth.signOut();
        Intent d = new Intent(this,MainActivity.class);
        startActivity(d);
    }
    public void feed(View v){}
}