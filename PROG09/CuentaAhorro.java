package PROG09;

import PROG09.Utilidades.CalculaCuentaBancaria;

public class CuentaAhorro extends CuentaBancaria {
    private double tipoInteres;

    public CuentaAhorro(Persona titular, String numeroCuenta, double tipoInteres, double saldoInicial) throws CalculaCuentaBancaria.ExcepcionCuentaBancaria {
        super(titular, numeroCuenta, saldoInicial);
        this.tipoInteres=tipoInteres;
    }

    public double getTipoInteres() {
        return tipoInteres;
    }

    public void setTipoInteres(double tipoInteres) {
        this.tipoInteres = tipoInteres;
    }
}
