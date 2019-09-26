package br.edu.ifpe.tads.pdm.urbano.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.edu.ifpe.tads.pdm.urbano.R;
import br.edu.ifpe.tads.pdm.urbano.entidades.Denuncia;

public class DenunciaArrayListAdapter extends ArrayAdapter<Denuncia> {

    private Denuncia [] denuncias;

    public DenunciaArrayListAdapter(Context context, int resource, Denuncia[] denuncias) {
        super(context, resource, denuncias);
        this.denuncias = denuncias;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View listItem = inflater.inflate(R.layout.denuncia_listitem, null, true);

        TextView tituloDenuncia = (TextView) listItem.findViewById(R.id.titulo_denuncia);
        TextView descricaoDenuncia = (TextView) listItem.findViewById(R.id.info_local);
        tituloDenuncia.setText(denuncias[position].getTitulo());
        descricaoDenuncia.setText(denuncias[position].getDescricao());

        return listItem;
    }
}
