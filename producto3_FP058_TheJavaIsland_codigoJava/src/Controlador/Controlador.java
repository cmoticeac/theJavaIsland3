package Controlador;


import Modelo.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;



public class Controlador {

    //Atributos
    public Datos datos;

    //Contructor
    public Controlador(){
        datos = new Datos();
    }

    //Getter y Setter
    public void setDatos(Datos datos) {this.datos = datos;}
    public Datos getDatos(){return datos;}

    //Metodos
    public void actualizarEnvios(){
        for(Pedido p: this.datos.getListaPedidos().getLista()){
            Duration diferencia = Duration.between(p.getFechaHora(), LocalDateTime.now());
            double segundos = diferencia.getSeconds();
            if ((p.getArticulo().getTiempoDePreparacion() * p.getCantidadArticulos()) < segundos){
                p.setEnviado(true);
            }
        }
    }

    public void añadirArticulo(Articulo articulo){
        this.datos.getListaArticulos().add(articulo);
    }

    public Boolean clienteExistente(String nif){
        for(int i = 0;i < this.datos.getListaClientes().getSize(); ++i){
            if(this.datos.getListaClientes().getAt(i).getNif().equals(nif)){
                return true;
            }
        }
        return false;
    }

    public void añadirCliente(Cliente cliente){
        this.datos.getListaClientes().add(cliente);
    }

    public Cliente conseguirClienteNif(String nif){
        for (Cliente cl: this.datos.getListaClientes().getLista()){
            if(cl.getNif().equals(nif)){
                return cl;
            }
        }
        return null;
    }

    public String imprimirArticulos(){
        String articulos = new String();
        for(Articulo art: this.datos.getListaArticulos().getLista()){
            articulos += art.getCodigo() + " " + art.getDescripcion() + " " + art.getPrecioDeVenta() + "€\n";
        }
        return articulos;
    }
    public Articulo obtenerArticuloCodigo(String codigo){
        for(Articulo art:this.datos.getListaArticulos().getLista()){
            if(art.getCodigo().equals(codigo)){
                return art;
            }
        }
        return null;
    }

    public String imprimirClientes(){
        String clientes = new String();
        for(Cliente cl: this.datos.getListaClientes().getLista()){
            clientes += cl.getNif() + " " + cl.getNombre() + "\n";
        }
        return clientes;
    }

    public String imprimirClientesPremium(){
        String clientes = new String();
        for(Cliente cl: this.datos.getListaClientes().getLista()){
            if(cl instanceof ClientePremium)
            clientes += cl.getNif() + " " + cl.getNombre() + "\n";
        }
        return clientes;
    }
    public String imprimirClientesEstandar(){
        String clientes = new String();
        for(Cliente cl: this.datos.getListaClientes().getLista()){
            if(cl instanceof ClienteEstandar)
                clientes += cl.getNif() + " " + cl.getNombre() + "\n";
        }
        return clientes;
    }

    public void añadirPedido(Pedido pedido){
        this.datos.getListaPedidos().add(pedido);
    }

    //false => no se ha podido borrar
    //true => borrado
    public Boolean eliminarPedido(int codigo){
        for (Pedido p: this.datos.getListaPedidos().getLista()){
            if(p.getNumeroPedido() == codigo){
                if(p.getEnviado()){
                    return false;
                }
                else{
                    this.datos.getListaPedidos().borrar(p);
                    return true;
                }
            }
        }
        return false;
    }

    public String obtenerPedidosPendientes(){
        String pedidos = new String();
        for (Pedido p:this.datos.getListaPedidos().getLista()){
            if(p.getEnviado() == false) pedidos += p.getNumeroPedido() + " " + p.getPrecioTotal() + "\n";
        }
        return pedidos;
    }

    public String obtenerPediosEnviados(){
        String pedidos = new String();
        for (Pedido p:this.datos.getListaPedidos().getLista()){
            if(p.getEnviado()) pedidos += p.getNumeroPedido() + " " + p.getPrecioTotal() + "\n";
        }
        return pedidos;
    }
}
