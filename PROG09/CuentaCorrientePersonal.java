package PROG09;

import PROG09.Utilidades.CalculaCuentaBancaria;


public class CuentaCorrientePersonal extends CuentaCorriente {

    double comision;
    public CuentaCorrientePersonal(Persona titular, String numeroCuenta, double saldoInicial, double comision) throws CalculaCuentaBancaria.ExcepcionCuentaBancaria {
        super(titular, numeroCuenta, saldoInicial);
        this.comision=comision;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }
}
