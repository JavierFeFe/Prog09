# Tarea para PROG09.

## Detalles de la tarea de esta unidad.

A lo largo de esta unidad has terminado de familiarizarte con el resto de conceptos relacionados con la Programación Orientada a Objetos que faltaban por ver de una manera más formal y con ejemplos explícitos: composición; herencia; clases y métodos abstractos; sobrescritura de métodos; interfaces; polimorfismo; ligadura dinámica, etc.

Has experimentando con todos estos conceptos y los has utilizado en pequeñas aplicaciones para comprobar su funcionamiento y su utilidad.

Una vez finalizada la unidad se puede decir que tienes ya un dominio adecuado del lenguaje Java como un lenguaje que permite aplicar todas las posibilidades de la Programación Orientada a Objetos. Dado ese supuesto, esta tarea tendrá como objetivo escribir una pequeña aplicación en Java empleando algunas de las construcciones que has aprendido a utilizar.

Se trata de desarrollar una aplicación Java que permita gestionar varios tipos de cuentas bancarias. Mediante un menú se podrán elegir determinas operaciones:

Abrir una nueva cuenta.
Ver un listado de las cuentas disponibles (código de cuenta, titular y saldo actual).
Obtener los datos de una cuenta concreta.
Realizar un ingreso en una cuenta.
Retirar efectivo de una cuenta.
Consultar el saldo actual de una cuenta.
Salir de la aplicación.
Las cuentas se irán almacenando en alguna estructura en memoria según vayan siendo creadas. Cada cuenta será un objeto de una clase que contendrá la siguiente información:

Titular de la cuenta (un objeto de la clase Persona, la cual contendrá información sobre el titular: nombre, apellidos, fecha de nacimiento).
Saldo actual de la cuenta (número real).
Número de cuenta (CCC - Código Cuenta Cliente).
Tipo de interés anual (si se trata de una cuenta de ahorro).
Lista de entidades autorizadas para cobrar recibos de la cuenta (si se trata de una cuenta corriente).
Comisión de mantenimiento (para el caso de una cuenta corriente personal).
Tipo de interés por descubierto (si es una cuenta corriente de empresa).
Máximo descubierto permitido (si se trata de una cuenta corriente de empresa)
Las cuentas bancarias pueden ser de dos tipos: cuentas de ahorro o bien cuentas corrientes. Las cuentas de ahorro son remuneradas y tienen un determinado tipo de interés. Las cuentas corrientes no son remuneradas, pero tienen asociada una lista de entidades autorizadas para cobrar recibos domiciliados en la cuenta.

Dentro de las cuentas corrientes podemos encontrar a su vez otros dos tipos: las cuentas corrientes personales, que tienen una comisión de mantenimiento (una cantidad fija anual) y las cuentas corrientes de empresa, que no la tienen. Además, las cuentas de empresa permiten tener una cierta cantidad de descubierto (máximo descubierto permitido) y por tanto un tipo de interés por descubierto y una comisión fija por cada descubierto que se tenga. Es el único tipo de cuenta que permite tener descubiertos.

Cuando se vaya a abrir una nueva cuenta bancaria, el usuario de la aplicación (empleado del banco) tendrá que solicitar al cliente:

Datos personales: nombre, apellidos, fecha de nacimiento.
Tipo de cuenta que desea abrir: cuenta de ahorro, cuenta corriente personal o cuenta corriente de empresa.
Saldo inicial.
Además de esa información, el usuario de la aplicación deberá también incluir:

Número de cuenta (CCC) de la nueva cuenta. Debe ser válido (habrá que comprobarlo).
Tipo de interés de remuneración, si se trata de una cuenta de ahorro.
de mantenimiento, si es una cuenta corriente personal.
Máximo descubierto permitido, si se trata de una cuenta corriente de empresa.
Tipo de interés por descubierto, en el caso de una cuenta corriente de empresa.
Comisión fija por cada descubierto, también para el caso de una cuenta corriente de empresa.
La aplicación deberá asegurarse que la información introducida sea válida y coherente (CCC válido; saldos, comisiones y tipos de interés positivos, etc.).

El programa que escribas debe cumplir al menos los siguientes requisitos:

Para almacenar los objetos de tipo cuenta podrás utilizar cualquier estructura de almacenamiento que consideres oportuna (ArrayList, Hashtable, etc.).
Para trabajar con los datos personales, debes utilizar una clase Persona que contenga la información sobre los datos personales básicos del cliente (nombre, apellidos, fecha de nacimiento).
Para trabajar con el número de cuenta debes utilizar el modelo de Código Cuenta Cliente (CCC), que es posible que también la ya hayas usado en otras unidades.
Para guardar las entidades autorizadas a cobrar recibos debes utilizar una Hashtable que contenga pares de tipo (código de entidad (String), máxima cantidad autorizada para un recibo).
Aquí tienes un ejemplo de una posible estructura de clases para llevar a cabo la aplicación:

Diagrama de clases general para una posible solución de la tarea: relaciones de herencia y composición entre clases que representan cuentas bancarias: CuentaBancaria, CuentaAhorro, CuentaCorriente, etc. Implementación de la interfaz Imprimible.
El código fuente Java de cada clase debería incluir comentarios en cada atributo (o en cada conjunto de atributos) y método (o en cada conjunto de métodos del mismo tipo) indicando su utilidad. El programa principal (clase principal) también debería incluir algunos comentarios explicativos sobre su funcionamiento y la utilización de objetos de las distintas clases utilizadas.

```Java
package PROG09;

import java.time.LocalDate;

public interface Imprimible {
    public Persona getTitular();
    public void setTitular(Persona titular);
}
```  
*Código de la interface Imprimible que enlaza la clase CuentaBancaria con la clase Persona*  
```Java
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
```
*Códgo de la que será la superclase principal para todos los tipos de cuenta bancaria*  

```Java
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
```
*Clase en la que se almacenan los datos del titular*
```Java
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
```
*Una de las 2 ramificaciones que heredan de Cuenta Bancaria*
```Java
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
```
*La otra ramificación de la clase CuentaBancaria*
```Java
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
```
*Una de las clases que heredan de la clase CuentaCorriente*
```Java
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
```
*La otra clase que hereda de CuentaCorriente*
```Java
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
```
*Clase en la que se incluye el método main y la visualización de todos los menús*
