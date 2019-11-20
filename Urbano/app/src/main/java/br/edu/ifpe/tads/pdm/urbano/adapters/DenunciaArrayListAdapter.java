package br.edu.ifpe.tads.pdm.urbano.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.ifpe.tads.pdm.urbano.R;
import br.edu.ifpe.tads.pdm.urbano.entidades.Denuncia;

public class DenunciaArrayListAdapter extends ArrayAdapter<Denuncia> {

    Context context;
    int resourceId;
    LinearLayout linearLayout;
    ArrayList<Denuncia> denuncias = new ArrayList<Denuncia>();

    public DenunciaArrayListAdapter(Context context, int resource, ArrayList<Denuncia> denuncias) {
        super(context, resource, denuncias);
        this.resourceId = resource;
        this.context = context;
        this.denuncias = denuncias;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View row = null;
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resourceId, parent, false);
        }   else {
            row = view;
        }
    Denuncia denuncia = denuncias.get(position);
        TextView tituloDenuncia = (TextView) row.findViewById(R.id.titulo_denuncia);
        TextView descricaoDenuncia = (TextView) row.findViewById(R.id.info_local);

        tituloDenuncia.setText(denuncia.getTitulo());
        descricaoDenuncia.setText(denuncia.getDescricao());

        return row;
    }
}
