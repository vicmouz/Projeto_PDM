package br.edu.ifpe.tads.pdm.urbano.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import br.edu.ifpe.tads.pdm.urbano.DetalheDenunciaActivity;
import br.edu.ifpe.tads.pdm.urbano.R;
import br.edu.ifpe.tads.pdm.urbano.entidades.Denuncia;

public class DenunciaListAdapter extends RecyclerView.Adapter<DenunciaListAdapter.DenunciaHolder>{

    ArrayList<Denuncia> denuncias = new ArrayList<Denuncia>();
    private Context mContext;

    public DenunciaListAdapter(Context context, ArrayList<Denuncia> denuncias){
        this.mContext = context;
        this.denuncias = denuncias;

    }

    @Override
    public DenunciaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.denuncia_listitem, parent, false);
        return new DenunciaHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(DenunciaHolder holder, int position) {
        final int pos = position;
        holder.bindDenuncia(denuncias.get(pos));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetalheDenunciaActivity.class);
                intent.putExtra("titulo_denuncia", denuncias.get(pos).getTitulo());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return denuncias.size();
    }


    public class DenunciaHolder extends RecyclerView.ViewHolder{

        TextView titulo;
        TextView descricao;
        LinearLayout parentLayout;

        private Denuncia denuncia;

        public DenunciaHolder(View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.titulo_denuncia);
            descricao = itemView.findViewById(R.id.info_local);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }

        public void bindDenuncia(Denuncia denuncia){
            this.denuncia = denuncia;

            titulo.setText(denuncia.getTitulo());
            descricao.setText(denuncia.getDescricao());

        }
    }
}
