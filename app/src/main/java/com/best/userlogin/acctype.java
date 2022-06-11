package com.best.userlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class acctype extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_acctype);
    }
    public void user_reg(View view){
        Intent intent = new Intent(this, user_regster.class);
        startActivity(intent);
    }

    public void admin_reg(View view){
        Intent intent = new Intent(this, admin_regster.class);
        startActivity(intent);
    }
}