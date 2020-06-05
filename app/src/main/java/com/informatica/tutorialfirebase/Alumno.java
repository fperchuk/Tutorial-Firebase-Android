package com.informatica.tutorialfirebase;

import java.util.HashMap;
import java.util.Map;

public class Alumno {
    private String id;
    private String nombre;
    private String division;
    private int calificacion;
    private String foto;

    // En caso de tener foto, podés utilizar este constructor
    /*public Alumno(String nombre, String division, int calificacion, String foto) {
        this.nombre = nombre;
        this.division = division;
        this.calificacion = calificacion;
        this.foto = foto;
    }*/
    public Alumno(){}


    public Alumno(String id, String nombre, String division, int calificacion) {
        this.id=id;
        this.nombre=nombre;
        this.division=division;
        this.calificacion=calificacion;
    }
    public Alumno(String nombre, String division, int calificacion) {
        this.nombre=nombre;
        this.division=division;
        this.calificacion=calificacion;
    }

    public String getId(){return  id;}

    public void setId(String id){this.id=id;}

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

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("nombre", this.nombre);
        result.put("division", this.division);
        result.put("calificacion", this.calificacion);
        return result;
    }
}