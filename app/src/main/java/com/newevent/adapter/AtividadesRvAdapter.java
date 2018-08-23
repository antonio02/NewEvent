package com.newevent.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.newevent.R;
import com.newevent.dao.atividade.GetAtividadesDoEventoRealtime;
import com.newevent.dao.atividade.interfaces.GetAtividadesDoEventoRealTimeListener;
import com.newevent.model.Atividade;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AtividadesRvAdapter extends RecyclerView.Adapter<AtividadesRvAdapter.ViewHolder>
implements GetAtividadesDoEventoRealTimeListener {


    private Context contexto;
    private List<Atividade> atividades;
    private String eventoUid;
    private GetAtividadesDoEventoRealtime getAtividades;


    public AtividadesRvAdapter(Context contexto, String eventoUid) {
        this.contexto = contexto;
        atividades = new ArrayList<>();
        getAtividades = new GetAtividadesDoEventoRealtime(eventoUid, this);
    }

    public void stop(){
        getAtividades.stop();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.atividades_rv_cardview, parent, false);
        return new AtividadesRvAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        holder.txtTipo.setText(atividades.get(position).getTipo());
        holder.txtNome.setText(atividades.get(position).getNome());
        holder.txtDataInicio.setText(format.format(atividades.get(position).getDataInicio()));
        holder.txtDataFim.setText(format.format(atividades.get(position).getDataTermino()));
        holder.txtValor.setText(String.valueOf(atividades.get(position).getValor()));
    }


    @Override
    public int getItemCount() {
        return atividades.size();
    }


    @Override
    public void onUpdate(List<Atividade> atividades) {
        this.atividades = atividades;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {


        TextView txtTipo;
        TextView txtNome;
        TextView txtDataInicio;
        TextView txtDataFim;
        TextView txtQtdVagas;
        TextView txtValor;


        ViewHolder(View itemView) {
            super(itemView);
            txtTipo = itemView.findViewById(R.id.txt_item_atividade_tipo);
            txtNome = itemView.findViewById(R.id.txt_item_atividade_nome);
            txtDataInicio = itemView.findViewById(R.id.txt_item_atividade_data_inicio);
            txtDataFim = itemView.findViewById(R.id.txt_item_atividade_data_fim);
            txtQtdVagas = itemView.findViewById(R.id.txt_item_atividade_vagas);
            txtValor = itemView.findViewById(R.id.txt_item_atividade_valor);
        }


    }
}
