package Modelo;
import java.text.SimpleDateFormat;
import java.time.*;
import java.text.ParseException;
import java.util.Date;
public class Pedido {

    /**
     * Atributos de la clase
     */

    private int numeroPedido;
    private Cliente cliente;
    private Articulo articulo;
    private int cantidadArticulos;
    private LocalDateTime fechaHora;
    private double precioTotal;
    private boolean enviado;

    //Constructores
    public Pedido(int np, Cliente cl, Articulo art, int ca, LocalDateTime fh, double pt, boolean en){
        this.numeroPedido = np;
        this.cliente = cl;
        this.articulo = art;
        this.cantidadArticulos = ca;
        this.fechaHora = fh;
        this.precioTotal = pt;
        this.enviado = en;
    }
    public Pedido(){}

    /**
     * Metodos Getters y Setters
     */


    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public int getCantidadArticulos() {
        return cantidadArticulos;
    }

    public void setCantidadArticulos(int cantidadArticulos) {
        this.cantidadArticulos = cantidadArticulos;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {   //entiendo que en el momento de crear el pedido ya calcula la hora de ese momento "this.fechaHora= LocalDateTime.now()";
        this.fechaHora = fechaHora;
    }

    public void setEnviado(boolean enviado){this.enviado = enviado;}

    public boolean getEnviado() {
        return enviado;
    }


    public double getPrecioTotal() {
        // Calcula el precio total como precio de venta por cantidad de artículos más gastos de envío
        double precioVenta = this.articulo.getPrecioDeVenta();
        double precioTotal = precioVenta * this.cantidadArticulos + this.articulo.getGastosDeEnvio();
        return precioTotal;
    }



    //Funcion para calcular el precio TOTAL del pedido premium (precio de todos los articulos + envio - descuento)
    public double precioEnvio(double precioDeVenta, double cantidadArticulos, double gastosEnvio, Cliente cliente) {
        double precio = precioDeVenta * cantidadArticulos;
        if (cliente instanceof ClientePremium) {
            ClientePremium cp = (ClientePremium) cliente;
            return precio + ((1-(cp.getDescuentoEnvio() / 100)) * gastosEnvio);
        } else {
            return precio + gastosEnvio;
        }

    }


    @Override
    public String toString() {
        if (enviado) {
            String imprimir = new String();
            imprimir = "\n--------------------------------------------------" +
                    "\nNumero del Pedido= " + numeroPedido +
                    "\nfechaHora= " + fechaHora +
                    "\nNif del cliente= " + cliente.getNif() +
                    "\nNombre del Cliente= " + cliente.getNombre() +
                    "\nArticulo codigo= " + articulo.getCodigo() +
                    "\nDescripción= " + articulo.getDescripcion() +
                    "\nCantidad= " + cantidadArticulos +
                    "\nPrecio unidad= " + articulo.getPrecioDeVenta() +
                    "\nGastos de envio= " + articulo.getGastosDeEnvio() +
                    "\nPrecio total(CON DESCUENTO SI PROCEDE)= " + precioEnvio(articulo.getPrecioDeVenta(), cantidadArticulos, articulo.getGastosDeEnvio(), cliente) + " €" +
                    "\nEl pedido esta ENVIADO." +
                    "\n--------------------------------------------------\n";
                    return imprimir;

        } else {
            return "\n--------------------------------------------------" +
                    "\nNumero del Pedido= " + numeroPedido +
                    "\nfechaHora= " + fechaHora +
                    "\nNif del cliente= " + cliente.getNif() +
                    "\nNombre del Cliente= " + cliente.getNombre() +
                    "\nArticulo codigo= " + articulo.getCodigo() +
                    "\nDescripción= " + articulo.getDescripcion() +
                    "\nCantidad= " + cantidadArticulos +
                    "\nPrecio unidad= " + articulo.getPrecioDeVenta() +
                    "\nGastos de envio= " + articulo.getGastosDeEnvio() +
                    "\nPrecio total(con DESCUENTO, si procede)= " + precioEnvio(articulo.getPrecioDeVenta(), cantidadArticulos, articulo.getGastosDeEnvio(), cliente) + " €" +
                    "\nEl pedido NO esta ENVIADO." +
                    "\n--------------------------------------------------\n";
        }
    }
    public boolean pedidoEnviado(LocalDateTime fechaHora, Long tiempoPreparacion) {        // funcion para saber si el pedido ha sido envido o no
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String fecha1 = fechaHora.toString().replace("T", " ");  //fecha elaboracion
        String fecha2 = LocalDateTime.now().toString().replace("T", " "); //fecha actual
        try {
            Date date1 = sdf.parse(fecha1);
            Date date2 = sdf.parse(fecha2);
            long diff = date2.getTime() - date1.getTime();
            if (diff > tiempoPreparacion) {   //si diff es mayor al tiempo de prep., el pedido ya se ha enviado
                return true;
            } else {    //si diff es menor al tiempo de prep., el pedido aun no se ha enviado
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;  //si hay algun error, el pedido no se devolvera ya que saldra como enviado.
    }
}

