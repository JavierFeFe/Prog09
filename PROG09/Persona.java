package PROG09;

import PROG09.Utilidades.CalculaNIF;

import java.time.LocalDate;

public class Persona {
    private String nombre, apellidos, NIF;
    private LocalDate fechaNacimiento;

    public Persona(String nombre, String apellidos, String NIF, LocalDate fechaNacimiento) throws CalculaNIF.ExcepcionNIF {
        this.nombre = nombre;
        this.apellidos = apellidos;
        if (CalculaNIF.isValido(NIF) != null) {
            this.NIF = NIF;
        }
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNIF() {
        return NIF;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
}
