package com.newevent.adapter;

import android.content.Context;
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
import com.newevent.model.Atividade;
import com.newevent.utils.DataSnapToAtividade;

import java.util.ArrayList;
import java.util.List;

public class AtividadesRvAdapter extends RecyclerView.Adapter<AtividadesRvAdapter.ViewHolder> {


    private Context contexto;
    DatabaseReference atividadesBD;
    List<Atividade> atividades;


    public AtividadesRvAdapter(Context contexto) {
        this.contexto = contexto;
        atividadesBD = FirebaseDatabase.getInstance().getReference("atividades");
        povoar();
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
        holder.txtNome.setText(atividades.get(position).getNome());
    }


    @Override
    public int getItemCount() {
        return atividades.size();
    }


    private void povoar() {
        atividades = new ArrayList<>();
        atividadesBD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                atividades.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    atividades.add(DataSnapToAtividade.get(d));
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNome;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txt_atividade_rv_cv_nome);
        }
    }
}
