package br.edu.ifpe.tads.pdm.urbano.fragmentos;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import br.edu.ifpe.tads.pdm.urbano.R;
import br.edu.ifpe.tads.pdm.urbano.auth.FirebaseAuthListener;
import com.google.android.gms.maps.OnMapReadyCallback;


public class MapaFragment extends Fragment implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    private FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback mLocationCallback;
    Location mLastLocation;
    double latitude = 0.0;
    double longitude = 0.0;
    FirebaseAuth mAuth;
    FirebaseAuthListener authListener;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mapa, null);

        requestPermission(v);
        if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(v.getContext(), "Permissão não concedida", Toast.LENGTH_LONG).show();
        }else{
            //Toast.makeText(v.getContext(), "Permissão concedida", Toast.LENGTH_LONG).show();
            checkLocation(v, this);
        }

        return  v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng atual = new LatLng(latitude,longitude);
        googleMap.addMarker(new MarkerOptions().position(atual)
                .title("Teste de marcador no IFPE"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(atual));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(12));

    }


    protected  void requestPermission(View view){
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
    }

    protected void checkLocation(final View v, final MapaFragment context){

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mLastLocation = locationResult.getLastLocation();
                Toast.makeText(v.getContext(), "Permissão concedida 2: " + mLastLocation.toString(), Toast.LENGTH_LONG).show();

            }
        };


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(v.getContext());
        if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }else{
            // Write you code here if permission already given.
            View myV = v;
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener ((Activity) getContext(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        String strLocation = "lat: " + location.getLatitude() + " lon: " + location.getLongitude();
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        Toast.makeText(getContext(), "Permissão concedida 2: " + strLocation, Toast.LENGTH_LONG).show();
                        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                        if(mapFragment == null){
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            mapFragment = SupportMapFragment.newInstance();
                            ft.replace(R.id.map, mapFragment).commit();
                        }
                        mapFragment.getMapAsync(context);
                        //Toast toast = Toast.makeText(getContext(), strLocation, Toast.LENGTH_LONG);
                        //toast.show();
                    }
                }
            });
        }
    }

    protected void createLocationRequest(){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


}
