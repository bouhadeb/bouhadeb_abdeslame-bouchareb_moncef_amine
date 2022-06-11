package com.best.userlogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class admin extends AppCompatActivity {
    String so;
    String sc;
    String sym;
    String snm;
    String syn;
    String snn;
    String snt;
    String sot;
    String asy;
    String asn;
    String aym;
    String anm;
    CheckBox open;
    CheckBox money;
    CheckBox net;
    CheckBox atmstat;
    CheckBox atmmoney;
    FirebaseFirestore db;
    FirebaseAuth fauth;
    String posteid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        open = findViewById(R.id.open);
        money = findViewById(R.id.money);
        net = findViewById(R.id.net);
        atmstat = findViewById(R.id.atmcheck);
        atmmoney = findViewById(R.id.atmmoney);
        Button ok = findViewById(R.id.ok);
        db = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin.this,maps.class);
                getid();
                startActivity(intent);
                finish();
                }
        });
        }

    public void goadd(View view){
            Intent intent1 = new Intent(admin.this,add_atm_poste.class);
            startActivity(intent1);
            finish();
        }
    public void getid(){
        String cradmin = fauth.getCurrentUser().getUid();
        DocumentReference USERID = db.collection("user").document(cradmin);
        USERID.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()){
                        posteid = db.collection("map").document(doc.getString("ID")).toString();
                    }
                        if (open.isChecked()){
                            Map<String,Object> poste = new HashMap<>();
                            so="open";
                            sc = "close";
                            poste.put("stat",so);
                            db.collection("map")
                                    .whereEqualTo("stat",sc)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful() && !task.getResult().isEmpty()){
                                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                        db.collection("map")
                                                .document(doc.getString("ID"))
                                                .update(poste)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(admin.this,"Successful",Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(admin.this,"Failed",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                            });
                        }else if (!open.isChecked()){
                            Map<String,Object> poste = new HashMap<>();
                            sc="open";
                            so = "close";
                            poste.put("stat",so);
                            db.collection("map")
                                    .whereEqualTo("stat",sc)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful() && !task.getResult().isEmpty()){
                                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                        db.collection("map")
                                                .document(doc.getString("ID"))
                                                .update(poste)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(admin.this,"Successful",Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(admin.this,"Failed",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                            });

                        }
                        if (money.isChecked()){
                            Map<String,Object> postemoney = new HashMap<>();
                            sym = "has money";
                            snm = "no money";
                            postemoney.put("money",sym);
                            db.collection("map")
                                    .whereEqualTo("money",snm)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful() && !task.getResult().isEmpty()){
                                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                        db.collection("map")
                                                .document(doc.getString("ID"))
                                                .update(postemoney)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(admin.this,"Successful",Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(admin.this,"Failed",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                            });
                        }else if (!money.isChecked()){
                            Map<String,Object> postemoney = new HashMap<>();
                            sym="no money";
                            snm = "has money";
                            postemoney.put("money",sym);
                            db.collection("map")
                                    .whereEqualTo("money",snm)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful() && !task.getResult().isEmpty()){
                                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                        db.collection("map")
                                                .document(doc.getString("ID"))
                                                .update(postemoney)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(admin.this,"Successful",Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(admin.this,"Failed",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                            });

                        }
                        if (net.isChecked()){
                            Map<String,Object> postemoney = new HashMap<>();
                            syn = "available";
                            snn = "disaible";
                            postemoney.put("network",syn);
                            db.collection("map")
                                    .whereEqualTo("network",snn)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful() && !task.getResult().isEmpty()){
                                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                        db.collection("map")
                                                .document(doc.getString("ID"))
                                                .update(postemoney)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(admin.this,"Successful",Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(admin.this,"Failed",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                            });
                        }else if (!net.isChecked()){
                            Map<String,Object> postenwt = new HashMap<>();
                            syn="disaible";
                            snn = "available";
                            postenwt.put("network",syn);
                            db.collection("map")
                                    .whereEqualTo("network",snn)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful() && !task.getResult().isEmpty()){
                                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                        db.collection("map")
                                                .document(doc.getString("ID"))
                                                .update(postenwt)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(admin.this,"Successful",Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(admin.this,"Failed",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                            });

                        }
                        if (!open.isChecked()){
                            Map<String,Object> postetime = new HashMap<>();
                            snt = "open at 7:00";
                            sot = "close at 5:00";
                            postetime.put("close",snt);
                            db.collection("map")
                                    .whereEqualTo("close",sot)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful() && !task.getResult().isEmpty()){
                                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                        db.collection("map")
                                                .document(doc.getString("ID"))
                                                .update(postetime)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(admin.this,"Successful",Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(admin.this,"Failed",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                            });
                        }else if (open.isChecked()){
                            Map<String,Object> postetime = new HashMap<>();
                            snt = "open at 7:00";
                            sot = "close at 5:00";
                            postetime.put("close",sot);

                            db.collection("map")
                                    .whereEqualTo("close",snt)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful() && !task.getResult().isEmpty()){
                                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                        db.collection("map")
                                                .document(doc.getString("ID"))
                                                .update(postetime)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(admin.this,"Successful",Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(admin.this,"Failed",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                            });

                        }
                    if (atmstat.isChecked()){
                        Map<String,Object> atmstat = new HashMap<>();
                        asy = "work";
                        asn = "broking";
                        atmstat.put("atmstat",asy);
                        db.collection("atm")
                                .whereEqualTo("atmstat",asn)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful() && !task.getResult().isEmpty()){
                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                    db.collection("atm")
                                            .document(doc.getString("ID"))
                                            .update(atmstat)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(admin.this,"Successful",Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(admin.this,"Failed",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }
                        });
                    }else if (!atmstat.isChecked()){
                        Map<String,Object> postemoney = new HashMap<>();
                        asn="broking";
                        asy = "work";
                        postemoney.put("atmstat",asn);
                        db.collection("atm")
                                .whereEqualTo("atmstat",asy)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful() && !task.getResult().isEmpty()){
                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                    db.collection("atm")
                                            .document(doc.getString("ID"))
                                            .update(postemoney)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(admin.this,"Successful",Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(admin.this,"Failed",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }
                        });

                    }
                    if (atmmoney.isChecked()){
                        Map<String,Object> postemoney = new HashMap<>();
                        aym = "has money";
                        anm = "no money";
                        postemoney.put("atmmoney",aym);
                        db.collection("atm")
                                .whereEqualTo("atmmoney",anm)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful() && !task.getResult().isEmpty()){
                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                    db.collection("atm")
                                            .document(doc.getString("ID"))
                                            .update(postemoney)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(admin.this,"Successful",Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(admin.this,"Failed",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }
                        });
                    }else if (!atmmoney.isChecked()){
                        Map<String,Object> postemoney = new HashMap<>();
                        aym="no money";
                        anm = "has money";
                        postemoney.put("atmmoney",aym);
                        db.collection("atm")
                                .whereEqualTo("atmmoney",anm)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful() && !task.getResult().isEmpty()){
                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                    db.collection("atm")
                                            .document(doc.getString("ID"))
                                            .update(postemoney)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(admin.this,"Successful",Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(admin.this,"Failed",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }
                        });

                    }
                }
            }
        });
    }
}

