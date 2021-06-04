package Entidades;

import java.util.List;

public class Alumno {
    private int legajo;
    private String nombre;
    private String apellido;

    public Alumno() {

    }

    public Alumno(int legajo, String nombre, String apellido)
    {
        this.legajo = legajo;
        this.nombre = nombre;
        this.apellido = apellido;

    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }




}
