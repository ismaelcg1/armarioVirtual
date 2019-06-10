package com.example.armariovirtual;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.HolderUsuario>{

    private ArrayList<Usuario> usuarios;
    private Context contexto;


    public static class HolderUsuario extends RecyclerView.ViewHolder {
        TextView nick, fechaNacimiento, genero;
        ImageView imagenUsuario;

        HolderUsuario(View itemView) {
            super(itemView);
            conectarVariablesConVista(itemView);
        }

        private void conectarVariablesConVista(View itemView) {
            nick = itemView.findViewById(R.id.tvNickUsuario);
            fechaNacimiento = itemView.findViewById(R.id.tvFechaNacimientoUsuario);
            genero = itemView.findViewById(R.id.tvGeneroUsuario);
            imagenUsuario = itemView.findViewById(R.id.iconoUsuario);
        }
    }

    public AdaptadorUsuarios(Context contexto, ArrayList<Usuario> usuarios) {
        this.contexto = contexto;
        this.usuarios = usuarios;
    }

    /**
     * Agrega los datos que queramos mostrar
     *
     * @param datos Datos a mostrar, prendas
     */
    public void add(ArrayList<Usuario> datos) {
        usuarios.clear();
        usuarios.addAll(datos);
    }

    /**
     * Actualiza los datos del ReciclerView
     */
    public void refrescar() {
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public HolderUsuario onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_usuarios, parent, false);
        HolderUsuario pvh = new HolderUsuario(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final HolderUsuario usuarioDeTurno, final int position) {

        final String uidUsuarioTurno = usuarios.get(position).getUid();
        final String nickUsuarioTurno = usuarios.get(position).getNickName();
        final String tallaTurno = usuarios.get(position).getTallaPorDefecto();
        final String fechaNacimientoTurno = usuarios.get(position).getFechaNacimiento();
        final int alturaTurno = usuarios.get(position).getAltura();
        final int pesoTurno = usuarios.get(position).getPeso();
        final Sexo sexoTurno = usuarios.get(position).getSexo();

        usuarioDeTurno.nick.setText(nickUsuarioTurno);
        usuarioDeTurno.fechaNacimiento.setText(fechaNacimientoTurno);
        usuarioDeTurno.genero.setText(sexoTurno.toString());

        usuarioDeTurno.imagenUsuario.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {

                    case R.id.iconoUsuario:

                        intent = new Intent(v.getContext(), ActividadInfoUsuarioCompleto.class);
                        intent.putExtra("uidUsuarioTurno",uidUsuarioTurno);
                        intent.putExtra("nickUsuarioTurno",nickUsuarioTurno);
                        intent.putExtra("tallaTurno",tallaTurno);
                        intent.putExtra("fechaNacimientoTurno",fechaNacimientoTurno);
                        intent.putExtra("alturaTurno",alturaTurno);
                        intent.putExtra("pesoTurno",pesoTurno);
                        intent.putExtra("sexoTurno",sexoTurno.toString());

                        contexto.startActivity(intent);

                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
