package com.example.armariovirtual;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String uid, nickName, email, fechaNacimiento, tallaPorDefecto;
    private Sexo sexo;
    private int altura, peso;

    public Usuario(){}

    public Usuario (String nickName, Sexo sexo, String tallaPorDefecto, String fechaNacimiento, int altura, int peso) {
        this.nickName = nickName;
        this.sexo = sexo;
        this.tallaPorDefecto = tallaPorDefecto;
        this.fechaNacimiento = fechaNacimiento;
        this.altura = altura;
        this.peso = peso;
    }

    public Usuario (String nickName, Sexo sexo, String tallaPorDefecto, String fechaNacimiento, int altura, int peso, String uid) {
        this.nickName = nickName;
        this.sexo = sexo;
        this.tallaPorDefecto = tallaPorDefecto;
        this.fechaNacimiento = fechaNacimiento;
        this.altura = altura;
        this.peso = peso;
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getTallaPorDefecto() {
        return tallaPorDefecto;
    }

    public void setTallaPorDefecto(String tallaPorDefecto) {
        this.tallaPorDefecto = tallaPorDefecto;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "uid='" + uid + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", tallaPorDefecto='" + tallaPorDefecto + '\'' +
                ", sexo=" + sexo +
                ", altura=" + altura +
                ", peso=" + peso +
                '}';
    }
}
