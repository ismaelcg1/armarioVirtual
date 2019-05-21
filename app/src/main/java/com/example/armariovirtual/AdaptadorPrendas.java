package com.example.armariovirtual;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
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
        TextView nombre, talla, estilo, subcategoria;
        ImageView imagenPrenda;

        HolderPrenda(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombrePrenda);
            talla = itemView.findViewById(R.id.tallaPrenda);
            estilo = itemView.findViewById(R.id.estiloPrenda);
            subcategoria = itemView.findViewById(R.id.subcategoriaPrenda);
            imagenPrenda = itemView.findViewById(R.id.imagenPrenda);
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
    public void onBindViewHolder(final HolderPrenda prendaDeTurno, int position) {
        // Ponemos los datos correspondientes a las prendas de posicion position
        prendaDeTurno.nombre.setText(prendas.get(position).getNombrePrenda());
        prendaDeTurno.talla.setText(prendas.get(position).getTallaPrenda());
        prendaDeTurno.estilo.setText(prendas.get(position).getEstiloPrenda());
        prendaDeTurno.subcategoria.setText(prendas.get(position).getSubcategoriaPrenda());

        prendaDeTurno.imagenPrenda.setImageResource(prendas.get(position).getImagenPrenda());

        prendaDeTurno.imagenPrenda.setOnClickListener(new View.OnClickListener(){
            // Hacemos View.OnClickListener() y nos referimos a cuando se va a 'seleccionar un elemento'
            @Override
            public void onClick(View v) {

                switch (v.getId()) {

                    case R.id.imagenPrenda: // Aquí es cuando se pulsa la imagen de la prenda

                            Snackbar snackbar1 = Snackbar.make (v, "Has pulsado "+prendaDeTurno.nombre.getText(), Snackbar.LENGTH_SHORT);
                            snackbar1.show ();
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
