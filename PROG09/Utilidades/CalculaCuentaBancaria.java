package PROG09.Utilidades;

public class CalculaCuentaBancaria {


    private static String[] getArrayCuenta(String cuenta){//Método que permite descomponer un String de cuenta bancaria en un array

        String entidad = cuenta.substring(0,4);
        String oficina = cuenta.substring(4,8);
        String dc = cuenta.substring(8,10);
        String num = cuenta.substring(10,20);
        return new String[]{entidad,oficina,dc,num};
    }
    public static boolean isValida(String cuenta) throws ExcepcionCuentaBancaria { //Este método puede ser llamado desde cualquier otra clase para verificar la validez de una cuenta.
        cuenta = cuenta.replaceAll(" ", "");
        if (cuenta.length() < 10){ //Posible cheque
            return true;
        }else if (cuenta.length() ==20){
            return compruebaCuenta(cuenta);
        }else if (cuenta.length() >20){
            if (cuenta.substring(0,4).toUpperCase().matches("^[A-Z]{2}\\d{2}")){
                cuenta = cuenta.substring(4,cuenta.length());
                if (cuenta.length() ==20) {
                    return compruebaCuenta(cuenta);
                }else{
                    throw error();
                }
            }
        }
        throw error();
    }
    private static boolean compruebaCuenta(String cuenta) throws ExcepcionCuentaBancaria {

        String[] arrayCuenta = getArrayCuenta(cuenta); //Llamada al método que descompone la cuenta en un array de Strings
        int[] factores = {4,8,5,10,9,7,3,6}; //Primera comprobación.
        String cuentaTmp = arrayCuenta[0] + arrayCuenta[1];
        String[] digitos = cuentaTmp.split("");
        long suma = 0;
        for ( int i =0 ;i <8; i++ ){
            suma += Integer.parseInt(digitos[i]) * factores[i];
        }
        long digito1 = (11-(suma %11)) < 10 ?11-(suma %11):11-(suma %11)==10?1:0;
        int[] factores2 = {1,2,4,8,5,10,9,7,3,6}; //Segunda comprobación.
        cuentaTmp = arrayCuenta[3];
        digitos = cuentaTmp.split("");
        suma = 0;
        for ( int i =0 ;i <10; i++ ){
            suma += Integer.parseInt(digitos[i]) * factores2[i];
        }
        long digito2 = (11-(suma %11)) < 10 ?11-(suma %11):11-(suma %11)==10?1:0;
        if (arrayCuenta[2].equals(String.valueOf(digito1) + String.valueOf(digito2))){
            return true;
        }
        throw error();
    }
    private static ExcepcionCuentaBancaria error(){
        return new ExcepcionCuentaBancaria("¡Formato de Cuenta Bancaria no válido!");
    }

    public static class ExcepcionCuentaBancaria extends Exception {
        public ExcepcionCuentaBancaria(String msg) {
            super(msg);
        }
    }
}

