package PROG09;

import PROG09.Utilidades.CalculaCuentaBancaria;


public class CuentaCorrienteEmpresa extends CuentaCorriente {
    private double interesDescubierto, maximoDescubierto, comisionFijaDescubierto;

    public CuentaCorrienteEmpresa(Persona titular, String numeroCuenta, double saldoInicial, double interesDescubierto, double maximoDescubierto, double comisionFijaDescubierto) throws CalculaCuentaBancaria.ExcepcionCuentaBancaria {
        super(titular, numeroCuenta, saldoInicial);
        this.interesDescubierto = interesDescubierto;
        this.maximoDescubierto = maximoDescubierto;
        this.comisionFijaDescubierto = comisionFijaDescubierto;
    }

    public double getInteresDescubierto() {
        return interesDescubierto;
    }

    public void setInteresDescubierto(double interesDescubierto) {
        this.interesDescubierto = interesDescubierto;
    }

    public double getMaximoDescubierto() {
        return maximoDescubierto;
    }

    public void setMaximoDescubierto(double maximoDescubierto) {
        this.maximoDescubierto = maximoDescubierto;
    }

    public double getComisionFijaDescubierto() {
        return comisionFijaDescubierto;
    }

    public void setComisionFijaDescubierto(double comisionFijaDescubierto) {
        this.comisionFijaDescubierto = comisionFijaDescubierto;
    }
    @Override
    public String retiraSaldo(double red){ //Sobreescribo el método de Cuenta Bancaria para tener en cuenta el máximo de descubierto
        if (red > 0.0) {//La cantidad debe ser un nº positivo mayor a 0
            if ((saldo-red) >= -maximoDescubierto) { //La cantidad a retirar debe ser igual o inferior al máximo permitido de descubierto
                saldo -= red;
                return "Se han retirado " + red + "€ de la cuenta.";
            }else{
                return "Imposible retirar saldo. La retirada supera el máximo de descubierto permitido.";
            }
        }else {
            return "Cantidad no válida.";
        }
    }

}
