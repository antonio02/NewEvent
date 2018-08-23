package com.newevent.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.newevent.R;
import com.newevent.controller.DetalhesEvento;
import com.newevent.controller.MeuEvento;
import com.newevent.model.Evento;
import com.newevent.utils.DataSnapToEvento;
import com.newevent.utils.DataUtil;
import com.newevent.utils.UidUtil;
import com.newevent.utils.UsuarioUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EventosRvAdapter extends RecyclerView.Adapter<EventosRvAdapter.EventoHolder> {

    private DatabaseReference eventosBD;
    private List<Evento> eventos;
    private Context contexto;

    public EventosRvAdapter(Context contexto) {
        eventosBD = FirebaseDatabase.getInstance().getReference("eventos");
        this.contexto = contexto;
        povoar();
    }

    private void povoar(){
        eventos = new ArrayList<>();
        eventosBD
                .orderByChild("data_inicio")
                .startAt(DataUtil.getAtual().getTime())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventos.clear();
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    eventos.add(DataSnapToEvento.get(d));
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @NonNull
    @Override
    public EventoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.eventos_rv_cardview, parent, false);
        return new EventoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoHolder holder, int position) {
        final SimpleDateFormat formatData = new SimpleDateFormat(
                "dd - MMMM - yyyy", Locale.getDefault());
        final SimpleDateFormat formatHora = new SimpleDateFormat(
                "HH:mm", Locale.getDefault());

        holder.txtNome.setText(eventos.get(position).getNome());
        holder.txtTipo.setText(eventos.get(position).getTipo());
        holder.txtData.setText("Data: "+ formatData.format(eventos.get(position).getDataInicio()));
        holder.txtHora.setText("Início as "+ formatHora.format(eventos.get(position).getDataInicio()));

        atribuirEscultadorAoCard(holder, position);

        if (UsuarioUtils.isLogado() && UsuarioUtils.getUid().equals(UsuarioUtils.getUid())) {
            holder.btnInscrever.setVisibility(View.INVISIBLE);
        }
    }

    private void atribuirEscultadorAoCard(EventoHolder holder, int position) {
        holder.card.setOnClickListener(v -> {
            String usuarioDonoUid = eventos.get(position).getDonoUid();
            Intent it;

            if (UsuarioUtils.isLogado() && usuarioDonoUid.equals(UsuarioUtils.getUid())) {
                it = new Intent(contexto, MeuEvento.class);
                it.putExtra("evento_uid", eventos.get(position).getUid());
                contexto.startActivity(it);

            } else {
                it = new Intent(contexto, DetalhesEvento.class);
                it.putExtra("evento_uid", eventos.get(position).getUid());
                contexto.startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    class EventoHolder extends RecyclerView.ViewHolder{

        CardView card;
        TextView txtNome;
        TextView txtTipo;
        TextView txtData;
        TextView txtHora;
        TextView btnInscrever;

        EventoHolder(View itemView) {
            super(itemView);
            btnInscrever = itemView.findViewById(R.id.btn_eventos_rv_cv_increver);
            card = itemView.findViewById(R.id.card_eventos_rv);
            txtNome = itemView.findViewById(R.id.txt_eventos_rv_cv_nome);
            txtTipo = itemView.findViewById(R.id.txt_eventos_rv_cv_tipo);
            txtData = itemView.findViewById(R.id.txt_eventos_rv_cv_data);
            txtHora = itemView.findViewById(R.id.txt_eventos_rv_cv_hora);
        }
    }
}
