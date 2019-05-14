package PROG09;

import PROG09.Utilidades.CalculaCuentaBancaria;

import java.util.Hashtable;

public abstract class CuentaCorriente extends CuentaBancaria {
    private Hashtable<String, Double> entidadesAutorizadas;
    public CuentaCorriente(Persona titular, String numeroCuenta, double saldoInicial) throws CalculaCuentaBancaria.ExcepcionCuentaBancaria {
        super(titular, numeroCuenta, saldoInicial);
        this.entidadesAutorizadas = new Hashtable<String, Double>();
    }

    public void anhadirEntidadAutorizada(String codigoEntidad, double candidadMaxima){
        entidadesAutorizadas.put(codigoEntidad,candidadMaxima);
    }

    public void eliminarEntidadAutorizada(String codigoEntidad){
        entidadesAutorizadas.remove(codigoEntidad);
    }

    public void setEntidadesAutorizadas(Hashtable<String, Double> entidadesAutorizadas) {
        this.entidadesAutorizadas = entidadesAutorizadas;
    }

    public Hashtable<String, Double> getEntidadesAutorizadas(){
        return entidadesAutorizadas;
    }
}
