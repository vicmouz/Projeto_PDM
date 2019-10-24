package br.edu.ifpe.tads.pdm.urbano.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.ifpe.tads.pdm.urbano.R;
import br.edu.ifpe.tads.pdm.urbano.entidades.Denuncia;

public class DenunciaArrayListAdapter extends ArrayAdapter<Denuncia> {

    private ArrayList<Denuncia> denuncias;

    public DenunciaArrayListAdapter(Context context, int resource, ArrayList<Denuncia> denuncias) {
        super(context, resource, denuncias);
        this.denuncias = denuncias;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View listItem = inflater.inflate(R.layout.denuncia_listitem, null, true);

        TextView tituloDenuncia = (TextView) listItem.findViewById(R.id.titulo_denuncia);
        TextView descricaoDenuncia = (TextView) listItem.findViewById(R.id.info_local);
        tituloDenuncia.setText(denuncias.get(position).getTitulo());
        descricaoDenuncia.setText(denuncias.get(position).getDescricao());

        return listItem;
    }
}
