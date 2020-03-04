package com.informatica.tutorialfirebase;

public class alumno {
    private String nombre;
    private String division;
    private int calificacion;
    private String foto;

    public alumno() {
    }

    public alumno(String nombre, String division, int calificacion, String foto) {
        this.nombre = nombre;
        this.division = division;
        this.calificacion = calificacion;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}