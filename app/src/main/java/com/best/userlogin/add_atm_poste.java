package com.best.userlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class add_atm_poste extends AppCompatActivity {
    ImageView atm;
    ImageView poste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_atm_poste);
        atm = findViewById(R.id.atm);
        poste = findViewById(R.id.poste);
    }
    public void addposte(View view){
        Intent intent = new Intent(add_atm_poste.this,add_post.class);
        startActivity(intent);
    }
    public void addatm(View view){
        Intent intent = new Intent(add_atm_poste.this,add_atm.class);
        startActivity(intent);
    }
}