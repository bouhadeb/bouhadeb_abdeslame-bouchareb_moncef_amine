package com.best.userlogin;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;



public class atm_poste extends AppCompatActivity {
    ImageView atm;
    ImageView poste;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm_poste);

        atm = findViewById(R.id.atm);
        poste = findViewById(R.id.poste);
    }
    public void poste(View view){
        title = getIntent().getStringExtra("title");
        Intent intent = new Intent(atm_poste.this,Details.class);
        intent.putExtra ("title2",title);
        startActivity(intent);
    }
    public void atm(View view){
        title = getIntent().getStringExtra("title");
        Intent intent = new Intent(atm_poste.this,atm_details.class);
        intent.putExtra ("title3",title);
        startActivity(intent);
    }
}