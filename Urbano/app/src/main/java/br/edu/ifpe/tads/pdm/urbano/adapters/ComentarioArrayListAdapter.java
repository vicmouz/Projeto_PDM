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
import br.edu.ifpe.tads.pdm.urbano.entidades.Comentario;
import br.edu.ifpe.tads.pdm.urbano.entidades.Denuncia;

public class ComentarioArrayListAdapter extends ArrayAdapter<Comentario> {

    Context context;
    int resourceId;
    LinearLayout linearLayout;
    ArrayList<Comentario> comentarios = new ArrayList<Comentario>();

    public ComentarioArrayListAdapter(Context context, int resource, ArrayList<Comentario> comentarios) {
        super(context, resource, comentarios);
        this.resourceId = resource;
        this.context = context;
        this.comentarios = comentarios;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View listItem = null;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            listItem = inflater.inflate(R.layout.comentarios_listitem, null, true);
        } else {
            listItem = view;
        }

        TextView view_nome_usuario_comentario = (TextView) listItem.findViewById(R.id.nome_usuario_comentario);
        TextView view_comentario_usuario = (TextView) listItem.findViewById(R.id.comentario_usuario);

        view_comentario_usuario.setText(comentarios.get(position).getComentario());
        view_nome_usuario_comentario.setText(comentarios.get(position).getUsuario().getNome());

        /*View row = null;
        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resourceId, parent, false);
        }   else {
            row = view;
        }*/



        /*TextView tituloDenuncia = (TextView) row.findViewById(R.id.titulo_denuncia);
        TextView descricaoDenuncia = (TextView) row.findViewById(R.id.info_local);

        tituloDenuncia.setText(denuncia.getTitulo());
        descricaoDenuncia.setText(denuncia.getDescricao());*/

        return listItem;
    }
}
