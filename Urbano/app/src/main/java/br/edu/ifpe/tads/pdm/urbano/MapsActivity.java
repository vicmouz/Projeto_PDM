package br.edu.ifpe.tads.pdm.urbano;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import br.edu.ifpe.tads.pdm.urbano.auth.FirebaseAuthListener;
import br.edu.ifpe.tads.pdm.urbano.entidades.Denuncia;
import br.edu.ifpe.tads.pdm.urbano.fragmentos.HomeFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener{


    private GoogleMap mMap;
    private int FINE_LOCATION_REQUEST;
    private boolean fine_location;
    String titulo_denuncia;
    Double lat_denuncia;
    Double lng_denuncia;
    FirebaseAuth mAuth;
    private FusedLocationProviderClient fusedLocationProviderClient;
    FirebaseAuthListener authListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        this.mAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        requestPermission();
        }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                double latitude;
                double longitude;

                if(location!=null) {

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    LatLng userLocation = new LatLng(latitude, longitude);

                    /*mMap.addMarker( new MarkerOptions().
                            position(userLocation).
                            title("Sua localização").
                            icon(BitmapDescriptorFactory.defaultMarker(35)));*/

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
                }
            }


        });


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(MapsActivity.this,
                        "Você clicou em " + marker.getTitle(),

                        Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //System.out.println("Latitude: "+latLng.latitude);
                //System.out.println("Longitude: " + latLng.longitude);
                Intent intent = new Intent(MapsActivity.this, AdicionarDenunciaActivity.class);
                intent.putExtra("latitude", latLng.latitude);
                intent.putExtra("longitude", latLng.longitude);
                startActivity(intent);
            }
        });

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        DatabaseReference drDenuncias = FirebaseDatabase.getInstance().getReference("denuncias");
        drDenuncias.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                   Denuncia currDenuncia = postSnapshot.getValue(Denuncia.class);
                    System.out.println(currDenuncia.getTitulo());
                    LatLng marker = new LatLng(currDenuncia.getLatitude(),currDenuncia.getLongitude());
                    mMap.addMarker( new MarkerOptions().
                            position(marker).
                            title(currDenuncia.getTitulo()).
                            icon(BitmapDescriptorFactory.defaultMarker(0)));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
   }

    private void requestPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        boolean hasPermission = (permissionCheck == PackageManager.PERMISSION_GRANTED);

        if (hasPermission) return;

        ActivityCompat.requestPermissions(this,
                new  String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                FINE_LOCATION_REQUEST);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {


        boolean granted = (grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED);
        fine_location = (requestCode == FINE_LOCATION_REQUEST) && granted;

        mMap.setMyLocationEnabled(fine_location);
    }



    @Override
    public void onMyLocationClick(@NonNull Location location) {
        System.out.println("Indo para a localização do usuário");
    }
    @Override
    public boolean onMyLocationButtonClick() {

        return false;
    }

    public void redirectHome(View view) {
        Intent homePage = new Intent(MapsActivity.this, HomeActivity.class);
        startActivity(homePage);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authListener);
    }
}
