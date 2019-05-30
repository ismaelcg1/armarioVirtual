package com.example.armariovirtual;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
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
        TextView cantidad, talla, estilo, subcategoria;
        ImageView imagenPrenda;

        HolderPrenda(View itemView) {
            super(itemView);
            talla = itemView.findViewById(R.id.tallaPrenda);
            estilo = itemView.findViewById(R.id.estiloPrenda);
            subcategoria = itemView.findViewById(R.id.subcategoriaPrenda);
            imagenPrenda = itemView.findViewById(R.id.imagenPrenda);
            cantidad = itemView.findViewById(R.id.cantidadDePrendas);
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
        // TODO Le paso el objeto completo, para hacerlo bien, se suele usar Parserable o algo así
        final int cantidadPrendaTurno = prendas.get(position).getCantidadPrenda();
        final String tallaPrendaTurno = prendas.get(position).getTallaPrenda();
        final String estiloPrendaTurno = prendas.get(position).getEstiloPrenda();
        final String colorPrendaTurno = prendas.get(position).getColor();
        final String epocaPrendaTurno = prendas.get(position).getEpocaPrenda();
        final String categoriaPrendaTurno = prendas.get(position).getCategoriaPrenda();
        final String subcategoriaPrendaTurno = prendas.get(position).getSubcategoriaPrenda();
        final boolean estadoPrendaTurno = prendas.get(position).isEstado_limpio();
        final int imagenPrendaTurno    = prendas.get(position).getImagenPrenda();

        /*
        final Prenda prendaElegida = new Prenda(prendas.get(position).getId(),
                                          prendas.get(position).getNombrePrenda(),
                                          prendas.get(position).getTallaPrenda(),
                                          prendas.get(position).getEstiloPrenda(),
                                          prendas.get(position).getColor(),
                                          prendas.get(position).getEpocaPrenda(),
                                          prendas.get(position).getCategoriaPrenda(),
                                          prendas.get(position).getSubcategoriaPrenda(),
                                          prendas.get(position).getImagenPrenda());
        */

        // -----

        // Ponemos los datos correspondientes a las prendas de posicion position
        prendaDeTurno.cantidad.setText(""+prendas.get(position).getCantidadPrenda());
        prendaDeTurno.talla.setText(prendas.get(position).getTallaPrenda());
        prendaDeTurno.estilo.setText(prendas.get(position).getEstiloPrenda());
        prendaDeTurno.subcategoria.setText(prendas.get(position).getSubcategoriaPrenda());

        prendaDeTurno.imagenPrenda.setImageResource(prendas.get(position).getImagenPrenda());

        prendaDeTurno.imagenPrenda.setOnClickListener(new View.OnClickListener(){
            // Hacemos View.OnClickListener() y nos referimos a cuando se va a 'seleccionar un elemento'
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {

                    case R.id.imagenPrenda: // Aquí es cuando se pulsa la imagen de la prenda

                        // TODO Prueba, hacer con id, no con nombre
                        intent = new Intent(v.getContext(), ActividadInfoPrendaCompleta.class);
                        intent.putExtra("tallaPrenda",tallaPrendaTurno);
                        intent.putExtra("estiloPrenda",estiloPrendaTurno);
                        intent.putExtra("colorPrenda",colorPrendaTurno);
                        intent.putExtra("epocaPrenda",epocaPrendaTurno);
                        intent.putExtra("categoriaPrenda",categoriaPrendaTurno);
                        intent.putExtra("subcategoriaPrenda",subcategoriaPrendaTurno);
                        intent.putExtra("estadoPrenda",estadoPrendaTurno);
                        intent.putExtra("imagenPrenda",imagenPrendaTurno);
                        intent.putExtra("cantidadPrenda",cantidadPrendaTurno);
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
