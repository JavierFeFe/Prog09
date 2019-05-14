package PROG09;


import PROG09.Utilidades.CalculaCuentaBancaria;

import java.time.LocalDate;

public abstract class CuentaBancaria implements Imprimible{
    private Persona titular;
    private String numeroCuenta;
    protected double saldo;
    public CuentaBancaria(Persona titular, String numeroCuenta, double saldoInicial) throws CalculaCuentaBancaria.ExcepcionCuentaBancaria {
        this.titular=titular;
        if (CalculaCuentaBancaria.isValida(numeroCuenta)) {
            this.numeroCuenta=numeroCuenta;
        }
        this.saldo = saldoInicial;
    }

    public String depositaSaldo(double inc){

        if (inc > 0.0){//La cantidad debe ser un nº positivo mayor a 0
            saldo+=inc;
            return "Se han depositado " + inc + "€ en la cuenta.";
        }else{
            return "Cantidad no válida.";
        }
    }
    public String depositaSaldo(String inc){
        try
        {
            return depositaSaldo(Double.parseDouble(inc));
        }
        catch(NumberFormatException e)
        {
            return "Formato no válido."; //Controla el error en el formato y devuelve un String
        }
    }

    public String retiraSaldo(double red){
        if (red > 0.0) {//La cantidad debe ser un nº positivo mayor a 0
            if (red <= saldo) { //La cantidad a retirar debe ser igual o inferior al saldo total
                saldo -= red;
                return "Se han retirado " + red + "€ de la cuenta.";
            }else{
                return "Saldo insuficiente.";
            }
        }else {
            return "Cantidad no válida.";
        }
    }
    public String retiraSaldo(String red){
        try
        {
            return retiraSaldo(Double.parseDouble(red));
        }
        catch(NumberFormatException e)
        {
            return "Formato no válido.";//Controla el error en el formato y devuelve un String
        }
    }

    public double getSaldo(){
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    @Override
    public Persona getTitular() {
        return titular;
    }

    @Override
    public void setTitular(Persona titular) {
        this.titular=titular;
    }
}
