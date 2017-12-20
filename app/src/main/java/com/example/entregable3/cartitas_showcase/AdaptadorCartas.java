package com.example.entregable3.cartitas_showcase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.entregable3.R;

import java.util.List;

/**
 * Created by Carlex on 11/12/2017.
 */

public class AdaptadorCartas extends RecyclerView.Adapter<AdaptadorCartas.ViewHolder> {

    private final LayoutInflater inflador;
    private final List<Carta> listaCartas;
    private View.OnClickListener onClickListener;

    AdaptadorCartas(Context context, List<Carta> listaCarta) {
        inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listaCartas = listaCarta;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.selector_elemento, null);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Carta c = listaCartas.get(position);
        if (!c.isBloqueada())
            holder.imagen.setImageResource(c.recursoImagen);
        else
            holder.imagen.setImageResource(R.drawable.bloqueado);
    }

    @Override
    public int getItemCount() {
        return listaCartas.size();
    }

    void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imagen;

        ViewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen);

        }
    }
}
