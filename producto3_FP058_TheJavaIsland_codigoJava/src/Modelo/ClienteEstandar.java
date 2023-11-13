package Modelo;

public class ClienteEstandar extends Cliente{

    public ClienteEstandar(String n, String d, String nif, String m) {
        super(n, d, nif, m);
    }

    public void ToString(){
        System.out.println("Cliente Estandar"
                + "\nNombre: " + this.getNombre()
                + "\nDireccion: " + this.getDomicilio()
                + "\nNif: " + this.getNif()
                + "\nEmail: " + getEmail());
    }
}
