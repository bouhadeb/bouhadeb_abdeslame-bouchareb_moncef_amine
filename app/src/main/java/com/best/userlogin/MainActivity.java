package com.best.userlogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.auth.User;
import com.google.maps.GeoApiContext;
import com.google.protobuf.DescriptorProtos;

public class MainActivity extends AppCompatActivity {
    private TextView newacc;
    EditText password;
    EditText email;
    FirebaseAuth fauth;
    FirebaseFirestore fStore;
    Boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mf;


    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9002;
    public static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9003;
    public static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //tenahi 3afs ata3 fog

        setContentView(R.layout.activity_main);

        //Var (INPUTS FROM USER)
        email = findViewById(R.id.name);
        password = findViewById(R.id.passedit);
        fauth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        //if user already logged in
        if (fauth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),maps.class));
            finish();
        }

        newacc = findViewById(R.id.loginp);
        newacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openregister();
            }
        });
    }

    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }
    //DIR ALERT LAKAN MECHO HAL GPS
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    // GPS YEMCHI
    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            Intent i = new Intent(this,maps.class);
            startActivity(i);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    //GOOGLE SERVICES MAKTIVI F PHONE
    public boolean isServicesOK(){

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }
    // TECHOF LAKAN USER MED PERMISSION WELA LA
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionGranted){
                    Intent i = new Intent(this,maps.class);
                    startActivity(i);
                }
                else{
                    getLocationPermission();
                }
            }
        }

    }
    //new user/admin (bah dir sign up) thal compte jdid
    public void openregister() {
        Intent intent = new Intent(this, acctype.class);
        startActivity(intent);
    }

    public void Login(View view) {
        String demail = email.getText().toString().trim();
        String dpassword = password.getText().toString().trim();
        if (password.length() < 6) {
            password.setError("Password need to be 6 or more car");
            return;
        }
        if (email.length() == 0) {
            email.setError("enter user name");
            return;
        }
        //check if user/admin exist in data base (login) la pae ta3 signin
        fauth.signInWithEmailAndPassword(demail, dpassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, "Logged Successfuly", Toast.LENGTH_SHORT).show();
                checkuser(authResult.getUser().getUid());
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkuser(String uid){
        DocumentReference df = fStore.collection("user").document (uid);
// extract the data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " + documentSnapshot.getData());
                // identify the user access Level
                if (documentSnapshot.getString("admin") != null) {
                    // user is admin teddih ll  admin page
                    if (checkMapServices()){
                        if (mLocationPermissionGranted){
                            startActivity(new Intent(getApplicationContext(), maps.class));
                            finish();
                        }else {getLocationPermission();}
                    }

                }
                if (documentSnapshot.getString("user") != null) {
                    //user is user teeddih tol ll map
                    if (checkMapServices()){
                        if (mLocationPermissionGranted){
                            startActivity(new Intent(getApplicationContext(), maps.class));
                            finish();
                        }else {getLocationPermission();}
                    }

                }

            }
        });
    }
}