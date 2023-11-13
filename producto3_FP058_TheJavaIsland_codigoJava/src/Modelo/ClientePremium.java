package Modelo;

public class ClientePremium extends Cliente{

    //Atributos propios de usuarios premium
    private double cuota;
    private double descuentoEnvio;


    //Contructor
    public ClientePremium( String n, String d, String nif, String m, double desc, double c) {
        super( n, d, nif, m);
        this.descuentoEnvio = desc;
        this.cuota = c;
    }

    //Setters i getters
    public double getCuota() {return this.cuota;}
    public double getDescuentoEnvio(){return this.descuentoEnvio;}

    public void setCuota(double c) {this.cuota = c;}
    public void setDescuentoEnvio(double c) {this.descuentoEnvio = c;}


    //toString()
    public void ToString(){
        System.out.println("Cliente Premium"
                + "\nNombre: " + this.getNombre()
                + "\nDireccion: " + this.getDomicilio()
                + "\nNif: " + this.getNif()
                + "\nEmail: " + getEmail());
    }
}
