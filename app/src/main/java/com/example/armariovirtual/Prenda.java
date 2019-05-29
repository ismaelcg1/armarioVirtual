package com.example.armariovirtual;

public class Prenda {

    private String tallaPrenda, estiloPrenda, color, epocaPrenda, categoriaPrenda, subcategoriaPrenda;
    private int id, imagenPrenda, cantidadPrenda;
    private boolean estado_limpio;

    public Prenda (int id, String tallaPrenda, String estiloPrenda, String color, String epocaPrenda,
                   String categoriaPrenda, String subcategoriaPrenda, int imagenPrenda, int cantidadPrenda) {
        this.id = id;
        this.tallaPrenda = tallaPrenda;
        this.estiloPrenda = estiloPrenda;
        this.color = color;
        this.epocaPrenda = epocaPrenda;
        this.categoriaPrenda = categoriaPrenda;
        this.subcategoriaPrenda = subcategoriaPrenda;
        this.imagenPrenda = imagenPrenda;
        this.cantidadPrenda = cantidadPrenda;
        estado_limpio = true;
    }

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

    public int getImagenPrenda() {
        return imagenPrenda;
    }

    public void setImagenPrenda(int imagenPrenda) {
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

    @Override
    public String toString() {
        return "Prenda{" +
                "tallaPrenda='" + tallaPrenda + '\'' +
                ", estiloPrenda='" + estiloPrenda + '\'' +
                ", color='" + color + '\'' +
                ", epocaPrenda='" + epocaPrenda + '\'' +
                ", categoriaPrenda='" + categoriaPrenda + '\'' +
                ", subcategoriaPrenda='" + subcategoriaPrenda + '\'' +
                ", id=" + id +
                ", imagenPrenda=" + imagenPrenda +
                ", cantidadPrenda=" + cantidadPrenda +
                ", estado_limpio=" + estado_limpio +
                '}';
    }
}
