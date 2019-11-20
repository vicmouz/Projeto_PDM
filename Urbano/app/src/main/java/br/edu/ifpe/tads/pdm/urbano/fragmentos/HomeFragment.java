package br.edu.ifpe.tads.pdm.urbano.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.tads.pdm.urbano.DetalheDenunciaActivity;
import br.edu.ifpe.tads.pdm.urbano.R;
import br.edu.ifpe.tads.pdm.urbano.adapters.DenunciaArrayListAdapter;
import br.edu.ifpe.tads.pdm.urbano.adapters.DenunciaListAdapter;
import br.edu.ifpe.tads.pdm.urbano.entidades.Denuncia;


public class HomeFragment extends Fragment{


    private static final List<Denuncia> denuncias = new ArrayList<Denuncia>();
    Button adicionarComentario;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("denuncias");
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                denuncias.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Denuncia denuncia = postSnapshot.getValue(Denuncia.class);
                    denuncias.add(denuncia);
                }

                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lista_denuncias2);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(new DenunciaListAdapter(getActivity(), denuncias));



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });





        //Buscar nome do usuario da denuncia

        //Buscar os comentarios e nome de usuarios



        
    }



        /*Fragment fragment = new DenunciasFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.visualisar_listas, fragment).commit();*/

}
