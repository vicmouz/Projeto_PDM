package br.edu.ifpe.tads.pdm.urbano.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import br.edu.ifpe.tads.pdm.urbano.R;
import br.edu.ifpe.tads.pdm.urbano.adapters.DenunciaArrayListAdapter;
import br.edu.ifpe.tads.pdm.urbano.entidades.Comentario;
import br.edu.ifpe.tads.pdm.urbano.entidades.Denuncia;


public class HomeFragment extends Fragment{


    private static final Denuncia [] denuncias = {
            new Denuncia(1, "Falta de sinalização sonora", "Não há sinalização sonora"),
            new Denuncia(2, "Ausência de rampas", "Não há rampas na faixa"),


    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.lista_denuncias);
        listView.setAdapter(new DenunciaArrayListAdapter(getActivity(),
                        R.layout.denuncia_listitem, denuncias
                )
        );
        
    }

    /*public void comentar(View view){
        Fragment fragment = new DetailDenunciaFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.ver_detalhe, fragment)
                .commit();
    }*/

        /*Fragment fragment = new DenunciasFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.visualisar_listas, fragment).commit();*/

}
