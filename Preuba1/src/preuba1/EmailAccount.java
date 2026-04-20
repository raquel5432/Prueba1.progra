/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package preuba1;

/**
 *
 * @author alira
 */
public class EmailAccount {
    
    private String direccion;
    private String password;
    private String nombre;
    private Email[] inbox;
    
    public EmailAccount(String direccion,String password,String nombre){
        
        this.direccion = direccion;
        this.password = password;
        this.nombre = nombre;
        this.inbox = new Email[CAPACIDAD];
    }
    
    public String getDireccion() { 
        return direccion; 
    }
    public String getPassword()  { 
        return password; 
    }
    public String getNombre()    { 
        return nombre; 
    }
    
    public boolean recibirCorreo(Email email) {
        for (int i = 0; i < CAPACIDAD; i++) {
            if (inbox[i] == null) {
                inbox[i] = email;
                return true;
            }
        }
        return false; // inbox lleno
    }
    
    public void mostrarInbox() {
        System.out.println("========================================");
        System.out.println("INBOX DE: " + direccion);
        System.out.println("NOMBRE  : " + nombre);
        System.out.println("========================================");
 
        int sinLeer = 0;
        int total   = 0;
 
        for (int i = 0; i < CAPACIDAD; i++) {
            if (inbox[i] != null) {
                total++;
                String estado = inbox[i].isLeido() ? "LEIDO" : "SIN LEER";
                if (!inbox[i].isLeido()) sinLeer++;
                System.out.printf("[%d] %s – %s – %s%n",
                        i, inbox[i].getEmisor(), inbox[i].getAsunto(), estado);
            }
        }
 
        if (total == 0) {
            System.out.println("(No hay correos en el inbox)");
        }
 
        System.out.println("----------------------------------------");
        System.out.println("Sin leer : " + sinLeer);
        System.out.println("Total    : " + total);
        System.out.println("========================================");
    }
    
    public void leerCorreo(int posicion) {
        if (posicion < 0 || posicion >= CAPACIDAD || inbox[posicion] == null) {
            System.out.println("Correo No Existe");
            return;
        }
        System.out.println("========================================");
        inbox[posicion].imprimir();
        inbox[posicion].marcarLeido();
        System.out.println("========================================");
    }
    
    public void eliminarLeidos() {
        int eliminados = 0;
        for (int i = 0; i < CAPACIDAD; i++) {
            if (inbox[i] != null && inbox[i].isLeido()) {
                inbox[i] = null;
                eliminados++;
            }
        }
        System.out.println("Se eliminaron " + eliminados + " correo(s) leído(s).");
    }

}
