package com.best.userlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class userpage extends AppCompatActivity {
    FirebaseAuth fauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);
        fauth = FirebaseAuth.getInstance();
    }

    public void userlogout(View v){
        fauth.signOut();
        Intent d = new Intent(this,MainActivity.class);
        startActivity(d);
    }
    public void userfeed(View v){Intent g = new Intent(this,feedback.class);
        startActivity(g);}

    public void usermap ( View view ) {
        Intent c = new Intent(this,maps.class);
        startActivity(c);
    }
}
