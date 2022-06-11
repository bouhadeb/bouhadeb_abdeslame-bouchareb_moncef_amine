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

public class add_atm extends AppCompatActivity {
    EditText nameatm;
    EditText addratm;
    EditText codeatm;
    EditText idatm;
    FirebaseFirestore dbatm = FirebaseFirestore.getInstance();
    String atm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_atm);

        nameatm = findViewById(R.id.nameatm);
        addratm = findViewById(R.id.addratm);
        codeatm = findViewById(R.id.codeatm);
        idatm = findViewById(R.id.idatm);
    }

    public void addatm(View view){
        String dname = nameatm.getText().toString();
        String daddr = addratm.getText().toString();
        String dcode = codeatm.getText().toString();
        String did = idatm.getText().toString();
        atm = nameatm.getText().toString();
        DocumentReference documentReference = dbatm.collection("atm").document(atm);
        Map<String,Object> poste = new HashMap<>();
        poste.put("name",dname);
        poste.put("address",daddr);
        poste.put("code",dcode);
        poste.put("ID",did);
        documentReference.set(poste)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(add_atm.this, "mecha", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(add_atm.this, "Error !", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}