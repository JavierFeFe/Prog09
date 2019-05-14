package PROG09;

import PROG09.Utilidades.CalculaCuentaBancaria;
import PROG09.Utilidades.CalculaNIF;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

public class AplicacionCuentaBancaria {
    private static final List<CuentaBancaria> listaCuentas = new ArrayList<>();
    private static final String menuPrincipal = //Menú principal
            "Opciones disponibles: \n\n" +
                    "1 - Abrir una nueva cuenta.\n" +
                    "2 - Ver un listado de las cuentas disponibles (código de cuenta, titular y saldo actual).\n" +
                    "3 - Operar cuenta.\n" +
                    "0 - Salir de la aplicación.\n\n" +
                    "Seleccione una opción: ";

    private static final String menuCrearCuenta = //Menú para crear cuenta
            "Opciones de Creación de cuentas: \n\n" +
                    "1 - Cuenta de ahorro.\n" +
                    "2 - Cuenta Corriente.\n" +
                    "0 - Volver atrás.\n\n" +
                    "Seleccione una opción: ";

    private static final String menuCrearCuentaCorriente = //Menú para crear cuenta corriente
            "Opciones disponibles: \n\n" +
                    "1 - Cuenta Personal.\n" +
                    "2 - Cuenta de Empresa.\n" +
                    "0 - Volver atrás.\n\n" +
                    "Seleccione una opción: ";


    private static final String menuOperarCuentaAhorro = //Menú para operar cuenta de ahorro
            "Opciones disponibles: \n\n" +
                    "1 - Realizar un ingreso.\n" +
                    "2 - Retirar efectivo.\n" +
                    "3 - Consultar el saldo.\n" +
                    "0 - Volver atrás.\n\n" +
                    "Seleccione una opción: ";
    private static final String menuOperarCuentaCorriente = //Menú para operar cuenta corriente
            "Opciones disponibles: \n\n" +
                    "1 - Realizar un ingreso.\n" +
                    "2 - Retirar efectivo.\n" +
                    "3 - Consultar el saldo.\n" +
                    "4 - Mostrar entidades autorizadas.\n" +
                    "5 - Añadir entidad autorizada.\n" +
                    "6 - Eliminar entidad autorizada.\n" +
                    "0 - Volver atrás.\n\n" +
                    "Seleccione una opción: ";
    public static void main(String[] args) {
        menuPrincipal();
    }
    private static void menuPrincipal(){ //Selecciono la opción del menú principal
        if (listaCuentas.size() > 0){

            boolean dentro = true;
            while (dentro) {
                System.out.print(menuPrincipal);
                Scanner sc = new Scanner(System.in);
                String opcion = sc.nextLine();
                switch (opcion) { //Switch para las opciones del menú.
                    case "1":
                        crearCuenta();
                        break;
                    case "2":
                        listaCuentas();
                        pausa();
                        break;
                    case "3":
                        seleccionarCuenta();
                        break;
                    case "0":
                        dentro = false;
                        break;
                    default:
                        System.out.println("Opción inválida." + "\n");
                        pausa();
                        break;
                }
            }
        }else {
            System.out.println("No existen cuentas creadas... ");
            crearCuenta();
            menuPrincipal();
        }
    }
    private static void crearCuenta(){

        boolean dentro = true;
        while (dentro) {
            System.out.print(menuCrearCuenta);
            Scanner sc = new Scanner(System.in);
            String opcion = sc.nextLine();
            switch (opcion) { //Switch para las opciones del menú.
                case "1":
                    crearCuentaAhorro();
                    break;
                case "2":
                    crearCuentaCorriente();
                    break;
                case "0":
                    dentro = false;
                    break;
                default:
                    System.out.println("Opción inválida." + "\n");
                    pausa();
                    break;
            }
        }
    }
    private static void crearCuentaCorriente(){ //Submenu para crear cuenta corriente
        boolean dentro = true;
        while (dentro) {
            System.out.print(menuCrearCuentaCorriente);
            Scanner sc = new Scanner(System.in);
            String opcion = sc.nextLine();
            switch (opcion) { //Switch para las opciones del menú.
                case "1":
                    crearCuentaCorrientePersonal();
                    break;
                case "2":
                    crearCuentaCorrienteEmpresa();
                    break;

                case "0":
                    dentro = false;
                    break;
                default:
                    System.out.println("Opción inválida." + "\n");
                    pausa();
                    break;
            }
        }
    }
    private static void crearCuentaAhorro(){ //Método para crear una cuenta de ahorro
        Persona titular = creaTitular();
        String cuentaBancaria = creaCuentaBancaria();
        double saldoInicial = creaDouble("Introduce el saldo inicial: ");
        double tipoInteres = creaDouble("Introduce el tipo de interés: ");
        try {
            CuentaAhorro cuenta = new CuentaAhorro(titular,cuentaBancaria,tipoInteres,saldoInicial);
            listaCuentas.add(cuenta);
            System.out.println("Cuenta de ahorro creada con éxito... ");
            pausa();
        } catch (CalculaCuentaBancaria.ExcepcionCuentaBancaria excepcionCuentaBancaria) {

        }
    }
    private static void crearCuentaCorrientePersonal(){ //Método para crear una cuenta corriente personal
        Persona titular = creaTitular();
        String cuentaBancaria = creaCuentaBancaria();
        double saldoInicial = creaDouble("Introduce el saldo inicial: ");
        double comision = creaDouble("Introduce la comisión: ");
        try {
            CuentaCorrientePersonal cuenta = new CuentaCorrientePersonal(titular,cuentaBancaria,saldoInicial,comision);
            listaCuentas.add(cuenta);
            System.out.println("Cuenta corriente de personal creada con éxito... ");
            pausa();
        } catch (CalculaCuentaBancaria.ExcepcionCuentaBancaria excepcionCuentaBancaria) {

        }
    }
    private static void crearCuentaCorrienteEmpresa(){ //Método para crear cuenta corriente de empresa
        Persona titular = creaTitular();
        String cuentaBancaria = creaCuentaBancaria();
        double saldoInicial = creaDouble("Introduce el saldo inicial: ");
        double interesDescubierto = creaDouble("Introduce los intereses por descubierto: ");
        double importeMaximoDescubierto = creaDouble("Introduce el importe máximo de descubierto permitido: ");
        double comisionFijaDescubierto = creaDouble("Importe de comisión máxima por descubierto: ");
        try {
            CuentaCorrienteEmpresa cuenta = new CuentaCorrienteEmpresa(titular,cuentaBancaria,saldoInicial,interesDescubierto, importeMaximoDescubierto, comisionFijaDescubierto);
            listaCuentas.add(cuenta);
            System.out.println("Cuenta corriente de empresa creada con éxito... ");
            pausa();
        } catch (CalculaCuentaBancaria.ExcepcionCuentaBancaria excepcionCuentaBancaria) {

        }
    }
    private static void seleccionarCuenta(){ //Menu de selección de cuentas a operar
        boolean dentro=true;
        while (dentro) {
            listaCuentas();
            System.out.print("Seleccione cuenta a operar: ");

            Scanner cuenta = new Scanner(System.in);
            String seleccionada = cuenta.nextLine();
            if (seleccionada.matches("\\d") && Integer.parseInt(seleccionada) <= listaCuentas.size()) {
                dentro = false;
                CuentaBancaria c =listaCuentas.get(Integer.parseInt(seleccionada)-1);
                operarCuenta(c,c.getTitular().getNIF() + " - " + c.getNumeroCuenta());
            } else {
                System.out.println("Número de cuenta no valido...");
            }
        }
    }
    private static void operarCuenta(CuentaBancaria cuenta, String titular){ //Interpreto el tipo de cuenta para mostrar el menú adecuado
        if (cuenta instanceof CuentaAhorro){
            operarCuentaAhorro((CuentaAhorro)cuenta,titular);
        }else{
            operarCuentaCorriente((CuentaCorriente)cuenta,titular);
        }
    }
    
    private static void operarCuentaAhorro(CuentaAhorro cuenta, String titular){ //Opero con una cuenta de ahorro
        boolean dentro = true;
        while (dentro) {
            System.out.println("\nOperando cuenta: " + titular + "\n");
            System.out.print(menuOperarCuentaAhorro);
            Scanner sc = new Scanner(System.in);
            String opcion = sc.nextLine();
            switch (opcion) { //Switch para las opciones del menú.
                case "1":
                    System.out.print("Introduzca la cantidad a ingresar: ");
                    sc = new Scanner(System.in);
                    System.out.println(cuenta.depositaSaldo(sc.nextLine())+"\n");
                    pausa();
                    break;
                case "2":
                    System.out.print("Introduzca la cantidad a retirar: ");
                    sc = new Scanner(System.in);
                    System.out.println(cuenta.retiraSaldo(sc.nextLine())+"\n");
                    pausa();
                    break;
                case "3":
                    System.out.println("Saldo: " + cuenta.getSaldo()+"€\n");
                    pausa();
                    break;
                case "0":
                    dentro = false;
                    break;
                default:
                    System.out.println("Opción inválida." + "\n");
                    pausa();
                    break;
            }
        }
    }
    private static void operarCuentaCorriente(CuentaCorriente cuenta, String titular){ //Opero con una cuenta corriente
                boolean dentro = true;
        while (dentro) {
            System.out.println("\nOperando cuenta: " + titular + "\n");
            System.out.print(menuOperarCuentaCorriente);
            Scanner sc = new Scanner(System.in);
            String opcion = sc.nextLine();
            switch (opcion) { //Switch para las opciones del menú.
                case "1":
                    System.out.print("Introduzca la cantidad a ingresar: ");
                    sc = new Scanner(System.in);
                    System.out.println(cuenta.depositaSaldo(sc.nextLine())+"\n");
                    pausa();
                    break;
                case "2":
                    System.out.print("Introduzca la cantidad a retirar: ");
                    sc = new Scanner(System.in);
                    System.out.println(cuenta.retiraSaldo(sc.nextLine())+"\n");
                    pausa();
                    break;
                case "3":
                    System.out.println("Saldo: " + cuenta.getSaldo()+"€\n");
                    pausa();
                    break;
                case "4":
                    muestraEntidades(cuenta);
                    pausa();
                    break;
                case "5":
                    System.out.print("Introduzca el código de entidad: ");
                    String clave = sc.nextLine();
                    
                    System.out.print("Introduzca la máxima cantidad autorizada: ");
                    try{
                    double cantidad = Double.parseDouble(sc.nextLine());
                    cuenta.anhadirEntidadAutorizada(clave, cantidad);
                    System.out.println("Entidad añadida con éxito...");
                    }catch(NumberFormatException ex){
                        System.out.println("Error!! Formato introducido no válido...");
                    }
                    pausa();
                    
                    break;
                case "6":
                    muestraEntidades(cuenta);
                    System.out.print("Introduzca el código de entidad a eliminar: ");
                    String entidadR = sc.nextLine();
                    cuenta.eliminarEntidadAutorizada(entidadR);
                    pausa();
                    break;
                case "0":
                    dentro = false;
                    break;
                default:
                    System.out.println("Opción inválida." + "\n");
                    pausa();
                    break;
            }
        }
    }
    private static void muestraEntidades(CuentaCorriente cuenta){ //Muestro un listado de entidades de una cuenta corriente
        Enumeration e = cuenta.getEntidadesAutorizadas().keys();
        Object clave;

        while( e.hasMoreElements() ){
             clave = e.nextElement();
             System.out.println( clave + " : " +  cuenta.getEntidadesAutorizadas().get(clave));

        }
    }
    private static double creaDouble(String texto){ //Método para capturar un valor de tipo double

        Scanner sc = new Scanner(System.in);
        boolean saldoValido = false;
        while (!saldoValido) { //bucle q fuerza a introducir un numero válido
            System.out.print(texto);
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Formato no válido."); //Controla el error en el formato y devuelve un String
            }
        }
        return 0.0;
    }
    private static String creaCuentaBancaria(){ //Método para capturra un nº de cuenta bancaria válido
        String cuentaBancaria = "";
        Scanner sc = new Scanner(System.in);
        boolean cuentValida = false;
        while (!cuentValida) { //bucle q fuerza a introducir un nº de cuenta válido
            System.out.print("Introduce el nº de cuenta: ");
            sc = new Scanner(System.in);
            cuentaBancaria=sc.nextLine();
            try {
                CalculaCuentaBancaria.isValida(cuentaBancaria);
                cuentValida=true;
            } catch (CalculaCuentaBancaria.ExcepcionCuentaBancaria excepcionCuentaBancaria) {
                System.out.println("Cuenta inválida!!");
            }
        }
        return cuentaBancaria;
    }
    private static Persona creaTitular(){ //Método para capturar un objeto de tipo Persona válido
        Scanner sc = new Scanner(System.in);
        String nif = "";
        boolean nifValido = false;
        while (!nifValido) {
            System.out.print("Introduce el NIF del titular: ");
            try {
                nif = sc.nextLine();
                CalculaNIF.isValido(nif);
                nifValido=true;
            } catch (CalculaNIF.ExcepcionNIF excepcionNIF) {
                System.out.println("NIF inválida!!");
            }
        }
        System.out.print("Introduce el nombre de pila del titular: ");
        String nombre = sc.nextLine();
        System.out.print("Introduce los apellidos del titular: ");
        String apellidos = sc.nextLine();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNacimiento = null;
        boolean fechaValida = false;
        while (!fechaValida) { //bucle q fuerza a introducir un nº de cuenta válido
            System.out.print("Introduce la fecha de nacimiento (dd/MM/yyyy\"): ");
            sc = new Scanner(System.in);
            try {
                fechaNacimiento = LocalDate.parse(sc.nextLine(), formato);
                fechaValida = true;
            }catch (DateTimeParseException ex){
                System.out.println("Formato de fecha inválido!!");
            }
        }
        try {
            return new Persona(nombre,apellidos,nif,fechaNacimiento);
        } catch (CalculaNIF.ExcepcionNIF excepcionNIF) {
            return null;
        }
    }

    private static void listaCuentas(){ //Método que muestra un listado de cuentas bancarias
        for (int i = 0; i<listaCuentas.size(); i++){
            CuentaBancaria cuenta = listaCuentas.get(i);
            Persona titular= listaCuentas.get(i).getTitular();
            System.out.println("(" + (i+1) + ") - " + cuenta.getNumeroCuenta() + " - " + titular.getNombre() + " " + titular.getApellidos() + " : " + cuenta.getSaldo());
        }
    }


    private static void pausa(){//Método simple para crear una pausa
        System.out.println("Presione Entrar para continuar...");
        new Scanner(System.in).nextLine();
    }
}