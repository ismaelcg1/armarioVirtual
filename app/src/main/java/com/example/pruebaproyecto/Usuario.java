package com.example.pruebaproyecto;

public class Usuario {

    private String nickName, email, password, fechaNacimiento;
    private Sexo sexo;
    private int altura, peso;

    public Usuario (String nickName, String email, String password, Sexo sexo, String fechaNacimiento, int altura, int peso) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.altura = altura;
        this.peso = peso;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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



    public void guardarDatos () {
        // TODO
        // HACER INSERT BBDD

    }


}
