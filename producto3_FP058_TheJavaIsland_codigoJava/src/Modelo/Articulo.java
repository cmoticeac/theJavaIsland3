package Modelo;

/** Clase Articulo */


public class Articulo {

    /** Atributos de la clase */
    private int id;
    private String codigo;
    private String Descripcion;
    private double precioDeVenta;
    private double gastosDeEnvio;
    private double tiempoDePreparacion;


    /** Constructores */

    public Articulo(String codigo, String descripcion, double precioDeVenta, double gastoDeEnvio, double tiempoDePrepar) {
        this.codigo = codigo;
        this.Descripcion = descripcion;
        this.precioDeVenta = precioDeVenta;
        this.gastosDeEnvio = gastoDeEnvio;
        this.tiempoDePreparacion = tiempoDePrepar;
    }

    public Articulo() {
    }

    /**
     * Metodos getters y setters
     */

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getPrecioDeVenta() {
        return this.precioDeVenta;
    }

    public void setPrecioDeVenta(double precioDeVenta) {
        this.precioDeVenta = precioDeVenta;
    }

    public double getGastosDeEnvio() {
        return this.gastosDeEnvio;
    }

    public void setGastosDeEnvio(double gastosDeEnvio) {
        this.gastosDeEnvio = gastosDeEnvio;
    }

    public double getTiempoDePreparacion() {
        return this.tiempoDePreparacion;
    }

    public String getDescripcion() {return this.Descripcion;}

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
    }

    public void setTiempoDePreparacion(double tiempoDePreparacion) {this.tiempoDePreparacion = tiempoDePreparacion;}

    /** El metodo toString() */
    public String toString() {
        return "articulos{codigo='" + this.codigo + "', descripcion='" + this.Descripcion + "', precioDeVenta=" + this.precioDeVenta + ", gastosDeEnvio=" + this.gastosDeEnvio + ", tiempoDePreparacion=" + this.tiempoDePreparacion + "}";
    }
}

