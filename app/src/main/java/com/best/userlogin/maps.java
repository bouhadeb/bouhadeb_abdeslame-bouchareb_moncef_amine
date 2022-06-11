package com.best.userlogin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.util.ArrayList;
import java.util.List;
import java.time.ZonedDateTime;

public class maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseAuth fauth;
    FirebaseFirestore fStore;
    GeoPoint mgeoPoint;
    private GeoApiContext mGeoApiContext = null;
    private FusedLocationProviderClient mf;
    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
    LatLng Edough = new LatLng(36.9109362541349, 7.735548455886907);
    LatLng ANNABA_MENADIA = new LatLng(36.91215520602915, 7.764127570687316);
    LatLng EL_BOUNI_ALLELIK = new LatLng(36.8518395853976, 7.742619238279881);
    LatLng LA_GARMDE_POSTE = new LatLng(36.90253223011113, 7.759858708581816);
    //LatLng POSTE_OFFICE_SERAIDI = new LatLng(36.91370236700372, 7.672967636662945);
    LatLng OUED_KOUBA = new LatLng(36.92443728579715, 7.759960602625391);

    ArrayList<String> title = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mf = LocationServices.getFusedLocationProviderClient(this);

        if(mGeoApiContext==null){
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.google_map_api_key))
                    .build();
        }


        fauth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //postes
        arrayList.add(Edough);
        arrayList.add(ANNABA_MENADIA);
        arrayList.add(EL_BOUNI_ALLELIK);
        arrayList.add(LA_GARMDE_POSTE);
        //arrayList.add(POSTE_OFFICE_SERAIDI);
        arrayList.add(OUED_KOUBA);

        //add titles
        title.add("Edough");
        title.add("ANNABA-MENADIA");
        title.add("EL-BOUNI-ALLELIK");
        title.add("La Grande Poste");
        //title.add("Poste Office Seraidi");
        title.add("Poste Oued Kouba");


    }







    //logout yes
    public void logout(View view) {

        checkuser1(fauth.getCurrentUser().getUid());
    }

    private void checkuser1(String uid) {
        DocumentReference df = fStore.collection("user").document(uid);
// extract the data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: " + documentSnapshot.getData());
                // identify the user access Level
                if (documentSnapshot.getString("admin") != null) {
                    // user is admin
                    startActivity(new Intent(getApplicationContext(), adminpage.class));
                    finish();
                }
                if (documentSnapshot.getString("user") != null) {
                    //user is user
                    startActivity(new Intent(getApplicationContext(), userpage.class));
                    finish();
                }

            }
        });
    }


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < title.size(); j++) {
                mMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title(String.valueOf(title.get(i))).icon(BitmapDescriptorFactory.fromResource(R.drawable.bank)));

            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                String markerTitle = marker.getTitle();
                Intent i= new Intent (maps.this, atm_poste.class);
                i.putExtra ("title",markerTitle);
                startActivity (i);
                return false;
            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }
}