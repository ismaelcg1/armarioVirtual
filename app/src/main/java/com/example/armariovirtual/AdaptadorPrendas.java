package com.example.armariovirtual;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorPrendas extends RecyclerView.Adapter<AdaptadorPrendas.HolderPrenda> {

    private ArrayList<Prenda> prendas;
    private Context contexto;


    public static class HolderPrenda extends RecyclerView.ViewHolder {
        TextView talla, estilo, subcategoria, marca;
        ImageView imagenPrenda;

        HolderPrenda(View itemView) {
            super(itemView);
            conectarVariablesConVista(itemView);
        }

        private void conectarVariablesConVista(View itemView) {
            talla = itemView.findViewById(R.id.tallaPrenda);
            estilo = itemView.findViewById(R.id.estiloPrenda);
            subcategoria = itemView.findViewById(R.id.subcategoriaPrenda);
            imagenPrenda = itemView.findViewById(R.id.imagenPrenda);
            marca = itemView.findViewById(R.id.marcaDePrenda);
        }
    }

    public AdaptadorPrendas(Context contexto, ArrayList<Prenda> prendas) {
        this.contexto = contexto;
        this.prendas = prendas;
    }

    /**
     * Agrega los datos que queramos mostrar
     *
     * @param datos Datos a mostrar, prendas
     */
    public void add(ArrayList<Prenda> datos) {
        prendas.clear();
        prendas.addAll(datos);
    }

    /**
     * Actualiza los datos del ReciclerView
     */
    public void refrescar() {
        notifyDataSetChanged();
    }


    @Override
    public HolderPrenda onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_prendas, parent, false);
        HolderPrenda pvh = new HolderPrenda(v);
        return pvh;
    }


    @Override
    public void onBindViewHolder(final HolderPrenda prendaDeTurno, final int position) {

        final int idPrendaTurno = prendas.get(position).getId();
        final int cantidadPrendaTurno = prendas.get(position).getCantidadPrenda();
        final String tallaPrendaTurno = prendas.get(position).getTallaPrenda();
        final String estiloPrendaTurno = prendas.get(position).getEstiloPrenda();
        final String colorPrendaTurno = prendas.get(position).getColor();
        final String epocaPrendaTurno = prendas.get(position).getEpocaPrenda();
        final String categoriaPrendaTurno = prendas.get(position).getCategoriaPrenda();
        final String subcategoriaPrendaTurno = prendas.get(position).getSubcategoriaPrenda();
        final boolean estadoPrendaTurno = prendas.get(position).isEstado_limpio();
        final int estadoIntercambio = prendas.get(position).getEs_intercambio();
        final Bitmap imagenPrendaTurno  = prendas.get(position).getImagenPrenda();
        final String marcaPrendaTurno   = prendas.get(position).getMarca();

        prendaDeTurno.imagenPrenda.setImageBitmap(imagenPrendaTurno);
        prendaDeTurno.marca.setText(marcaPrendaTurno);
        prendaDeTurno.talla.setText(tallaPrendaTurno);
        prendaDeTurno.estilo.setText(estiloPrendaTurno);
        prendaDeTurno.subcategoria.setText(subcategoriaPrendaTurno);

        prendaDeTurno.imagenPrenda.setOnClickListener(new View.OnClickListener(){
            // Hacemos View.OnClickListener() y nos referimos a cuando se va a 'seleccionar un elemento'
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {

                    case R.id.imagenPrenda:

                        intent = new Intent(v.getContext(), ActividadInfoPrendaCompleta.class);
                        intent.putExtra("idPrenda",idPrendaTurno);
                        intent.putExtra("tallaPrenda",tallaPrendaTurno);
                        intent.putExtra("estiloPrenda",estiloPrendaTurno);
                        intent.putExtra("colorPrenda",colorPrendaTurno);
                        intent.putExtra("epocaPrenda",epocaPrendaTurno);
                        intent.putExtra("categoriaPrenda",categoriaPrendaTurno);
                        intent.putExtra("subcategoriaPrenda",subcategoriaPrendaTurno);
                        intent.putExtra("estadoPrenda",estadoPrendaTurno);
                        intent.putExtra("imagenPrenda",imagenPrendaTurno);
                        intent.putExtra("cantidadPrenda",cantidadPrendaTurno);
                        intent.putExtra("marcaPrenda",marcaPrendaTurno);
                        intent.putExtra("estado_intercambio",estadoIntercambio);

                        contexto.startActivity(intent);

                        break;
                }
            }
        });
    }

    /**
     *
     * @return Tamaño del array de prendas
     */
    @Override
    public int getItemCount() {
        return prendas.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
