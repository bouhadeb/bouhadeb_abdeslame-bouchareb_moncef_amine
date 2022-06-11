package com.best.userlogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class atm_details extends AppCompatActivity {
    TextView name,codeatm,addatm,stat,money;
    FirebaseFirestore db;
    String title3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atm_details);

        name = findViewById(R.id.name);
        codeatm = findViewById(R.id.codeatm);
        addatm = findViewById(R.id.addatm);
        stat = findViewById(R.id.stat);
        money = findViewById(R.id.money);
        db = FirebaseFirestore.getInstance();
        title3 = getIntent().getStringExtra("title3");

        DocumentReference atm = db.collection("atm").document(title3);

        atm.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText("ATM : "+value.getString("name"));
                codeatm.setText("Code Postal : "+value.getString("code"));
                addatm.setText("Adresse : "+value.getString("address"));
                stat.setText("ATM stat is : "+value.getString("atmstat"));
                money.setText("liquidity stat : "+value.getString("atmmoney"));
            }
        });
    }
}