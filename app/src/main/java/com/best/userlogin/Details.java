package com.best.userlogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Details extends AppCompatActivity {
    FirebaseFirestore db;
    String title2;
    String latlang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView open =  findViewById(R.id.stat);
        TextView money =  findViewById(R.id.money);
        TextView net = findViewById(R.id.network);
        TextView name = findViewById(R.id.name);
        TextView codepos = findViewById(R.id.codepos);
        TextView add = findViewById(R.id.add);
        TextView time = findViewById(R.id.time);
        TextView turn = findViewById(R.id.turn);
        db = FirebaseFirestore.getInstance();
        title2 = getIntent().getStringExtra("title2");


        DocumentReference poste1 = db.collection("map").document(title2);
        poste1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                open.setText("Poste stat : "+value.getString("stat"));
                money.setText("liquidity stat : "+value.getString("money"));
                net.setText("Network stat : "+value.getString("network"));
                name.setText("Poste : "+value.getString("name"));
                codepos.setText("Code Postal : "+value.getString("code"));
                add.setText("Adresse : "+value.getString("address"));
                time.setText(value.getString("close"));
                turn.setText("turn is : "+value.getString("turn"));
            }
        });
    }

    public void getpostelatlang(){

        switch (title2){
            case "ANNABA-MENADIA" :
                latlang ="36.91215520602915,7.764127570687316";
                break;
            case "EL-BOUNI-ALLELIK" :
                latlang ="36.8518395853976,7.742619238279881";
                break;
            case "Edough" :
                latlang ="36.9109362541349,7.735548455886907";
                break;
            case "Poste Oued Kouba" :
                latlang ="36.92443728579715,7.759960602625391";
                break;
            case "La Grande Poste" :
                latlang ="36.90253223011113,7.759858708581816";
                break;
        }
    }


    public void go ( View view )
     {
         getpostelatlang();
         Intent intent = new Intent(Intent.ACTION_VIEW,
                 Uri.parse("google.navigation:q="+latlang+"&mode=d"));
         intent.setPackage("com.google.android.apps.maps");
         if (intent.resolveActivity(getPackageManager())!= null){
             startActivity(intent);
         }
    }
}