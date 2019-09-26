package br.edu.ifpe.tads.pdm.urbano.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;

import br.edu.ifpe.tads.pdm.urbano.R;
import br.edu.ifpe.tads.pdm.urbano.auth.FirebaseAuthListener;


public class MapaFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa, null);



        return view;
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
