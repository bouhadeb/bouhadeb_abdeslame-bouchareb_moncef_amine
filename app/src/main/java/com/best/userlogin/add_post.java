package com.best.userlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class add_post extends AppCompatActivity {
    EditText name;
    EditText addr;
    EditText code;
    EditText id;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String poste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        name = findViewById(R.id.name);
        addr = findViewById(R.id.addr);
        code = findViewById(R.id.code);
        id = findViewById(R.id.id);
    }
    public void add (View view){
        String dname = name.getText().toString();
        String daddr = addr.getText().toString();
        String dcode = code.getText().toString();
        String did = id.getText().toString();
        poste = id.getText().toString();
        DocumentReference documentReference = db.collection("map").document(poste);
        Map<String,Object> poste = new HashMap<>();
        poste.put("name",dname);
        poste.put("address",daddr);
        poste.put("code",dcode);
        poste.put("ID",did);
        documentReference.set(poste)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(add_post.this, "mecha", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(add_post.this, "Error !", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}