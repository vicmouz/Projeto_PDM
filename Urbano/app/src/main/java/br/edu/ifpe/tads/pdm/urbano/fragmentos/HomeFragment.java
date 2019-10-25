package br.edu.ifpe.tads.pdm.urbano.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.edu.ifpe.tads.pdm.urbano.R;
import br.edu.ifpe.tads.pdm.urbano.adapters.DenunciaArrayListAdapter;
import br.edu.ifpe.tads.pdm.urbano.entidades.Denuncia;


public class HomeFragment extends Fragment{


    private static final ArrayList<Denuncia> denuncias = new ArrayList<Denuncia>();


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

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

                Denuncia denuncia = dataSnapshot.getValue(Denuncia.class);
                denuncias.add(denuncia);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });



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
