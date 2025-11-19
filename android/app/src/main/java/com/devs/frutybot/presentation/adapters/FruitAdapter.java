package com.devs.frutybot.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devs.frutybot.R;
import com.devs.frutybot.domain.model.FruitDomain;

import java.util.ArrayList;
import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.FrutaViewHolder> {
    private List<FruitDomain> frutas = new ArrayList<>();

    // Ahora recibe directamente List<FruitDomain>
    public void setFrutas(List<FruitDomain> frutas) {
        this.frutas = frutas != null ? frutas : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FrutaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fruit, parent, false);
        return new FrutaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FrutaViewHolder holder, int position) {
        FruitDomain fruta = frutas.get(position);
        holder.nombre.setText(fruta.getNombre());
        holder.descripcion.setText(fruta.getDescripcion());
        Glide.with(holder.itemView.getContext())
                .load(fruta.getImagen())
                .into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return frutas.size();
    }

    static class FrutaViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, descripcion;
        ImageView imagen;

        FrutaViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.textNombre);
            descripcion = itemView.findViewById(R.id.textDescripcion);
            imagen = itemView.findViewById(R.id.imageFruta);
        }
    }
}
