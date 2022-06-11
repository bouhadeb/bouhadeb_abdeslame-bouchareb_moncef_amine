package com.best.userlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class feedback extends AppCompatActivity {
    EditText postename;
    EditText feeddback1;
    String feedid;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        postename = findViewById(R.id.postenamefeed);
        feeddback1 = findViewById(R.id.feedback);
    }
    public void send(View v){
        String dpostesend = postename.getText().toString();
        String dfeedback = feeddback1.getText().toString();
        feedid = auth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("feed").document(feedid);
        Map<String,Object> postefeed = new HashMap<>();
        postefeed.put("poste name",dpostesend);
        postefeed.put("feed back",dfeedback);
        documentReference.set(postefeed)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(feedback.this, "mecha", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(feedback.this, "Error !", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public void cancel_feed(View v){
        Intent i = new Intent(this,userpage.class);
        startActivity(i);
    }
}