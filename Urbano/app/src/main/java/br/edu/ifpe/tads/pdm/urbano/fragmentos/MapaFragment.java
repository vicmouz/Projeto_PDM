package br.edu.ifpe.tads.pdm.urbano.fragmentos;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

import br.edu.ifpe.tads.pdm.urbano.MapsActivity;
import br.edu.ifpe.tads.pdm.urbano.R;
import br.edu.ifpe.tads.pdm.urbano.auth.FirebaseAuthListener;
import com.google.android.gms.maps.OnMapReadyCallback;


public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int FINE_LOCATION_REQUEST;
    private boolean fine_location;
    FirebaseAuth mAuth;
    FirebaseAuthListener authListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa, null);

        requestPermission(view);

currentLocation(view, this);
        return view;
    }

    protected  void requestPermission(View view){
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
    }

    @Nullable
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //mMap = googleMap;

        LatLng recife = new LatLng(-8.05, -34.9);


        googleMap.addMarker(new MarkerOptions().
                position(recife).
                title("Recife").
                icon(BitmapDescriptorFactory.defaultMarker(35)));


        googleMap.moveCamera(CameraUpdateFactory.newLatLng(recife));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(12));
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        boolean granted = (grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED);
        fine_location = (requestCode == FINE_LOCATION_REQUEST) && granted;

        mMap.setMyLocationEnabled(fine_location);
        getActivity().findViewById(R.id.button_location).setEnabled(fine_location);
    }

    /*@Override
    public  void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getActivity(), "Você está aqui! ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getActivity(), "Indo para a sua localização.", Toast.LENGTH_SHORT).show();
        return false;
    }*/

    public void currentLocation(View view, MapaFragment context) {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null) {
                    Toast.makeText(getActivity(), "Localização atual: Latitude = " +
                            location.getLatitude() + " Longitude = " +
                            location.getLongitude(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }





    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
