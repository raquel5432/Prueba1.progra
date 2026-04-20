/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package preuba1;

import java.util.Scanner;

/**
 *
 * @author alira
 */
public class JavaLook {

    private static final int MAX_CUENTAS = 100;
    private static EmailAccount[] cuentas = new EmailAccount[MAX_CUENTAS];
    private static int totalCuentas = 0;
    private static EmailAccount cuentaActiva = null;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║   Bienvenido a JavaLook 📧   ║");
        System.out.println("╚══════════════════════════════╝");
        menuInicial();
    }

    private static void menuInicial() {
        int opcion;
        do {
            System.out.println("\n──── MENÚ INICIAL ────");
            System.out.println("1. Login");
            System.out.println("2. Crear cuenta");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> login();
                case 2 -> crearCuenta();
                case 0 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("✘ Opción inválida. Ingresa 0, 1 o 2.");
            }
        } while (opcion != 0);
    }

    private static void login() {
        System.out.print("Correo   : ");
        String correo = sc.nextLine().trim();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine().trim();

        if (!correo.contains("@")) {
            System.out.println("✘ Formato de correo inválido (debe contener '@').");
            return;
        }

        EmailAccount cuenta = buscarCuenta(correo);

        if (cuenta != null && cuenta.getPassword().equals(pass)) {
            cuentaActiva = cuenta;
            System.out.println("✔ Sesion iniciada como " + cuentaActiva.getNombre());
            menuPrincipal();
        } else {
            System.out.println("✘ Correo o contrasena incorrectos.");
        }
    }

    private static void crearCuenta() {
        if (totalCuentas >= MAX_CUENTAS) {
            System.out.println("✘ No hay espacio para nuevas cuentas.");
            return;
        }

        System.out.print("Correo (unico): ");
        String correo = sc.nextLine().trim();

        if (correo.isEmpty() || !correo.contains("@")) {
            System.out.println("✘ Correo invalido (no puede estar vacío y debe contener '@').");
            return;
        }
        if (buscarCuenta(correo) != null) {
            System.out.println("✘ Ese correo ya esta registrado.");
            return;
        }

        System.out.print("Nombre    : ");
        String nombre = sc.nextLine().trim();
        System.out.print("Contrasena: ");
        String pass = sc.nextLine().trim();

        if (nombre.isEmpty() || pass.isEmpty()) {
            System.out.println("✘ Nombre y contrasena no pueden estar vacios.");
            return;
        }

        EmailAccount nueva = new EmailAccount(correo, pass, nombre);
        cuentas[totalCuentas] = nueva;
        totalCuentas++;

        cuentaActiva = nueva;
        System.out.println("✔ Cuenta creada. Sesion iniciada como " + nombre);
        menuPrincipal();
    }

    private static void menuPrincipal() {
        int opcion;
        do {
            System.out.println("\n──── MENU PRINCIPAL (" + cuentaActiva.getDireccion() + ") ────");
            System.out.println("1. Ver inbox");
            System.out.println("2. Mandar correo");
            System.out.println("3. Leer correo");
            System.out.println("4. Limpiar inbox (eliminar leídos)");
            System.out.println("5. Cerrar sesion");
            System.out.print("Opcion: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> cuentaActiva.mostrarInbox();
                case 2 -> mandarCorreo();
                case 3 -> leerCorreo();
                case 4 -> cuentaActiva.eliminarLeidos();
                case 5 -> cerrarSesion();
                default -> System.out.println("✘ Opción invalida. Ingresa un numero del 1 al 5.");
            }
        } while (opcion != 5);
    }

    private static void mandarCorreo() {
        System.out.print("Destinatario: ");
        String destino = sc.nextLine().trim();

        if (!destino.contains("@")) {
            System.out.println("✘ Formato de correo inválido (debe contener '@').");
            return;
        }

        EmailAccount destAccount = buscarCuenta(destino);
        if (destAccount == null) {
            System.out.println("✘ La cuenta destino no existe.");
            return;
        }

        System.out.print("Asunto  : ");
        String asunto = sc.nextLine().trim();
        System.out.print("Contenido: ");
        String contenido = sc.nextLine().trim();

        if (asunto.isEmpty() || contenido.isEmpty()) {
            System.out.println("✘ Asunto y contenido no pueden estar vacíos.");
            return;
        }

        Email email = new Email(cuentaActiva.getDireccion(), asunto, contenido);

        if (destAccount.recibirCorreo(email)) {
            System.out.println("✔ Correo enviado exitosamente a " + destino);
        } else {
            System.out.println("✘ El inbox del destinatario está lleno.");
        }
    }

    private static void leerCorreo() {
        System.out.print("Posición del correo: ");
        int pos = leerEntero();

        if (pos == -1) {
            System.out.println("✘ Ingresa un numero de posicion valido.");
            return;
        }
        cuentaActiva.leerCorreo(pos);
    }

    private static void cerrarSesion() {
        System.out.println("Sesion cerrada. Hasta pronto, " + cuentaActiva.getNombre() + "!");
        cuentaActiva = null;
    }

    private static EmailAccount buscarCuenta(String correo) {
        for (int i = 0; i < totalCuentas; i++) {
            if (cuentas[i].getDireccion().equalsIgnoreCase(correo)) {
                return cuentas[i];
            }
        }
        return null;
    }

    private static int leerEntero() {
        String linea = sc.nextLine().trim();
        try {
            return Integer.parseInt(linea);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
