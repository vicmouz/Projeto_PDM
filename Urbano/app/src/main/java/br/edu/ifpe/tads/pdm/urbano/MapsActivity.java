package br.edu.ifpe.tads.pdm.urbano;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;
    private int FINE_LOCATION_REQUEST;
    private boolean fine_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng recife = new LatLng(-8.05, -34.9);
        LatLng caruaru = new LatLng(-8.27, -35.98);
        LatLng joaopessoa = new LatLng(-7.12, -34.84);


        mMap.addMarker( new MarkerOptions().
                position(recife).
                title("Recife").
                icon(BitmapDescriptorFactory.defaultMarker(35)));

        mMap.addMarker( new MarkerOptions().
                position(caruaru).
                title("Caruaru").
                icon(BitmapDescriptorFactory.defaultMarker(120)));

        mMap.addMarker( new MarkerOptions().
                position(joaopessoa).
                title("João Pessoa").

                icon(BitmapDescriptorFactory.defaultMarker(230)));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(recife));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(MapsActivity.this,
                        "Você clicou em " + marker.getTitle(),
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().
                        position(latLng).
                        title("Adiconado em " + new Date()).
                        icon(BitmapDescriptorFactory.defaultMarker(0)));
            }
        });
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
        boolean granted = (grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED);
        fine_location = (requestCode == FINE_LOCATION_REQUEST) && granted;

        mMap.setMyLocationEnabled(fine_location);
    }

    @Override
    public  void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Você está aqui! ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Indo para a sua localização.", Toast.LENGTH_SHORT).show();
        return false;
    }

}
