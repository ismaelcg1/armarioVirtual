package com.example.armariovirtual;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

public class Prenda implements Parcelable {

    private String tallaPrenda, estiloPrenda, color, epocaPrenda, categoriaPrenda, subcategoriaPrenda, marca;
    private int id, cantidadPrenda, es_intercambio;
    private Bitmap imagenPrenda;
    private boolean estado_limpio;

    public Prenda (int id, String tallaPrenda, String estiloPrenda, String color, String epocaPrenda, String categoriaPrenda,
                   String subcategoriaPrenda, Bitmap imagenPrenda, int cantidadPrenda, String marca, boolean estado_limpio, int es_intercambio) {
        this.id = id;
        this.tallaPrenda = tallaPrenda;
        this.estiloPrenda = estiloPrenda;
        this.color = color;
        this.epocaPrenda = epocaPrenda;
        this.categoriaPrenda = categoriaPrenda;
        this.subcategoriaPrenda = subcategoriaPrenda;
        this.imagenPrenda = imagenPrenda;
        this.cantidadPrenda = cantidadPrenda;
        this.marca = marca;
        this.estado_limpio = estado_limpio;
        this.es_intercambio = es_intercambio;
    }

    protected Prenda(Parcel in) {
        tallaPrenda = in.readString();
        estiloPrenda = in.readString();
        color = in.readString();
        epocaPrenda = in.readString();
        categoriaPrenda = in.readString();
        subcategoriaPrenda = in.readString();
        marca = in.readString();
        id = in.readInt();
        cantidadPrenda = in.readInt();
        es_intercambio = in.readInt();
        imagenPrenda = in.readParcelable(Bitmap.class.getClassLoader());
        estado_limpio = in.readByte() != 0;
    }

    public static final Creator<Prenda> CREATOR = new Creator<Prenda>() {
        @Override
        public Prenda createFromParcel(Parcel in) {
            return new Prenda(in);
        }

        @Override
        public Prenda[] newArray(int size) {
            return new Prenda[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTallaPrenda() {
        return tallaPrenda;
    }

    public void setTallaPrenda(String tallaPrenda) {
        this.tallaPrenda = tallaPrenda;
    }

    public String getEstiloPrenda() {
        return estiloPrenda;
    }

    public void setEstiloPrenda(String estiloPrenda) {
        this.estiloPrenda = estiloPrenda;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEpocaPrenda() {
        return epocaPrenda;
    }

    public void setEpocaPrenda(String epocaPrenda) {
        this.epocaPrenda = epocaPrenda;
    }

    public String getCategoriaPrenda() {
        return categoriaPrenda;
    }

    public void setCategoriaPrenda(String categoriaPrenda) {
        this.categoriaPrenda = categoriaPrenda;
    }

    public String getSubcategoriaPrenda() {
        return subcategoriaPrenda;
    }

    public void setSubcategoriaPrenda(String subcategoriaPrenda) {
        this.subcategoriaPrenda = subcategoriaPrenda;
    }

    public Bitmap getImagenPrenda() {
        return imagenPrenda;
    }

    public void setImagenPrenda(Bitmap imagenPrenda) {
        this.imagenPrenda = imagenPrenda;
    }

    public boolean isEstado_limpio() {
        return estado_limpio;
    }

    public void setEstado_limpio(boolean estado_limpio) {
        this.estado_limpio = estado_limpio;
    }

    public int getCantidadPrenda() {
        return cantidadPrenda;
    }

    public void setCantidadPrenda(int cantidadPrenda) {
        this.cantidadPrenda = cantidadPrenda;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public static Bitmap convertirStringABitmap(String fotoString) {
        try{
            byte [] encodeByte = Base64.decode(fotoString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public int getEs_intercambio() {
        return es_intercambio;
    }

    public void setEs_intercambio(int es_intercambio) {
        this.es_intercambio = es_intercambio;
    }

    @Override
    public String toString() {
        return "Prenda{" +
                "tallaPrenda='" + tallaPrenda + '\'' +
                ", estiloPrenda='" + estiloPrenda + '\'' +
                ", color='" + color + '\'' +
                ", epocaPrenda='" + epocaPrenda + '\'' +
                ", categoriaPrenda='" + categoriaPrenda + '\'' +
                ", subcategoriaPrenda='" + subcategoriaPrenda + '\'' +
                ", marca='" + marca + '\'' +
                ", id=" + id +
                ", cantidadPrenda=" + cantidadPrenda +
                ", es_intercambio=" + es_intercambio +
                ", imagenPrenda=" + imagenPrenda +
                ", estado_limpio=" + estado_limpio +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tallaPrenda);
        dest.writeString(estiloPrenda);
        dest.writeString(color);
        dest.writeString(epocaPrenda);
        dest.writeString(categoriaPrenda);
        dest.writeString(subcategoriaPrenda);
        dest.writeString(marca);
        dest.writeInt(id);
        dest.writeInt(cantidadPrenda);
        dest.writeInt(es_intercambio);
        dest.writeParcelable(imagenPrenda, flags);
        dest.writeByte((byte) (estado_limpio ? 1 : 0));
    }
}
