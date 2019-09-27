package br.edu.ifpe.tads.pdm.urbano.fragmentos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.edu.ifpe.tads.pdm.urbano.AdicionarDenunciaActivity;
import br.edu.ifpe.tads.pdm.urbano.HomeActivity;
import br.edu.ifpe.tads.pdm.urbano.R;
import br.edu.ifpe.tads.pdm.urbano.auth.SignInActivity;


public class PerfilFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, null);



        return view;
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void buttonSignOutClick(View view) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            mAuth.signOut();
            Intent intent = new Intent(getActivity(), SignInActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
        }
    }

    public void adicionarNovaDenuncia(View view){
        Intent intent = new Intent(getActivity(), AdicionarDenunciaActivity.class);
        startActivity(intent);
    }


}
