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

import java.util.Date;

import br.edu.ifpe.tads.pdm.urbano.auth.FirebaseAuthListener;
import br.edu.ifpe.tads.pdm.urbano.fragmentos.HomeFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener{


    private GoogleMap mMap;
    private int FINE_LOCATION_REQUEST;
    private boolean fine_location;
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
        //currentLocation();
        /*fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            lat = location.getLatitude();
                            lng = location.getLongitude();
                        }
                    }
                });*/






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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                double latitude;
                double longitude;

                if(location!=null) {
                    /*Toast.makeText(MapsActivity.this, "Localização atual: Latitude = " +
                            location.getLatitude() + " Longitude = " +
                            location.getLongitude(), Toast.LENGTH_SHORT).show();*/
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    LatLng userLocation = new LatLng(latitude, longitude);

                    mMap.addMarker( new MarkerOptions().
                            position(userLocation).
                            title("Sua localização").
                            icon(BitmapDescriptorFactory.defaultMarker(35)));

                    mMap.addMarker( new MarkerOptions().
                            position(userLocation).
                            title("Sua localização").
                            icon(BitmapDescriptorFactory.defaultMarker(35)));

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
                mMap.addMarker(new MarkerOptions().
                        position(latLng).
                        title("Adicionado em " + new Date()).

                        icon(BitmapDescriptorFactory.defaultMarker(0)));

            }
        });

        //mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

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

        findViewById(R.id.button_current_location).setEnabled(fine_location);

        boolean granted = (grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED);
        fine_location = (requestCode == FINE_LOCATION_REQUEST) && granted;

        mMap.setMyLocationEnabled(fine_location);
    }

    /*public void currentLocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        double longitude;

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null) {
                    Toast.makeText(MapsActivity.this, "Localização atual: Latitude = " +
                            location.getLatitude() + " Longitude = " +
                            location.getLongitude(), Toast.LENGTH_SHORT).show();
                    this.lat = location.getLatitude();
                    this.lng = location.getLongitude();
                    System.out.println("Lat: " + lat + " Lng: " + lng);
                }
            }
        });


    }*/

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Você está aqui!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Indo para a sua localização.", Toast.LENGTH_SHORT).show();
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
