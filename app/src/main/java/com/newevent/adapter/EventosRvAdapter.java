package com.newevent.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.newevent.R;
import com.newevent.model.Evento;
import com.newevent.utils.DataSnapshotToEvento;

import java.util.ArrayList;
import java.util.List;

public class EventosRvAdapter extends RecyclerView.Adapter<EventosRvAdapter.EventoHolder> {

    private DatabaseReference eventosBD;
    private List<Evento> eventos;

    public EventosRvAdapter() {
        eventosBD = FirebaseDatabase.getInstance().getReference("eventos");
        povoar();
    }

    private void povoar(){
        eventos = new ArrayList<>();
        eventosBD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventos.clear();
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    eventos.add(DataSnapshotToEvento.get(d));
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
        holder.txtNome.setText(eventos.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    class EventoHolder extends RecyclerView.ViewHolder{

        TextView txtNome;

        EventoHolder(View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.eventos_rv_cv_nome);
        }
    }
}
