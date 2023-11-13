package Modelo;

public abstract class Cliente {

    //Atributos
    private int id;
    private String Nombre;
    private String Domicilio;
    private String Nif;
    private String Email;

    //Contructor
    public Cliente(String n, String d, String nif, String m) {
        this.Nombre = n;
        this.Domicilio = d;
        this.Nif = nif;
        this.Email = m;
    }

    //Setters y getters
    public String getNombre() {return Nombre;}

    public String getDomicilio() {return Domicilio;}

    public String getNif() {return Nif;}

    public String getEmail() {return Email;}

    public void setNombre(String c) {this.Nombre = c;}

    public void setDomicilio(String c) {this.Domicilio = c;}

    public void setNif(String c) {this.Nif = c;}

    public void setEmail(String c) {this.Email = c;}

}
