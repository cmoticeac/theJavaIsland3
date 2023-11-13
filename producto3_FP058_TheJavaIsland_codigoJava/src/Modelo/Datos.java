package Modelo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Datos {

    //Atributos
    private ListaArticulos listaArticulos;
    private ListaClientes listaClientes;
    private ListaPedidos listaPedidos;

    //Contructor
    public Datos (){
        listaArticulos = new ListaArticulos();
        listaClientes = new ListaClientes();
        listaPedidos = new ListaPedidos();
        datosPrograma();
    }
    //ToString
    @Override
    public String toString(){
        String imprimir = new String();
        imprimir = "LISTA DE ARTICULOS: " + listaArticulos.toString() + "\nLISTA DE CLIENTES: " + listaClientes.toString() + "\nLISTA DE PEDIDOS: " + listaPedidos.toString();
        return imprimir;
    }

    //Getters y Setters
    public ListaArticulos getListaArticulos() {return this.listaArticulos;}
    public ListaClientes getListaClientes() {return this.listaClientes;}
    public ListaPedidos getListaPedidos() {return this.listaPedidos;}

    public void setListaArticulos(ListaArticulos l) {this.listaArticulos = l;}
    public void setListaClientes(ListaClientes l) {this.listaClientes = l;}
    public void setListaPedidos(ListaPedidos l) {this.listaPedidos = l;}

    //Metodos

    public void datosPrograma(){

/** Carga de Articulos **/
        Articulo LibCocina= new Articulo("lib0001","Libro de Cocina", 10.50, 3.00,45);
        Articulo LibTerror= new Articulo("lib0002","Evangelio del Mal", 21.50, 3.00,60);
        Articulo LibInfantil= new Articulo("lib0003","El Gato con Botas", 8.30, 3.00,25);
        Articulo LibHistoria= new Articulo("lib0004","Edad Media", 10.50, 3.00,5);
        Articulo LibBricolage= new Articulo("lib0005","Manitas para Dummies", 14.50, 3.00,5);

        ArrayList<Articulo> artiP= new ArrayList<Articulo>();
        artiP.add(LibBricolage);
        artiP.add(LibCocina);
        artiP.add(LibTerror);
        artiP.add(LibInfantil);
        artiP.add(LibHistoria);

        this.listaArticulos.lista= artiP;


        /**Carga de Clientes **/

        ClientePremium MLopez= new ClientePremium("Manuel Lopez", "Barcelona", "E2216876l", "mLopez@hotmail.com", 40,10);
        ClientePremium DTerrago= new ClientePremium("Dicac Terrago", "Barcelona", "D7549630n", "dTerrago@hotmail.com", 45,20);
        ClienteEstandar PMacron= new ClienteEstandar("Pier Macron", "Paris", "F2239015l", "pMacron@hotmail.com");
        ClienteEstandar SPlana= new ClienteEstandar("Sara Plana", "Bilbao", "R8007153s", "sPlana@hotmail.com");

        this.listaClientes.lista.add(MLopez);
        this.listaClientes.lista.add(DTerrago);
        this.listaClientes.lista.add(PMacron);
        this.listaClientes.lista.add(SPlana);

        //Carga de pedidos
        Pedido p1 = new Pedido(1,MLopez, LibHistoria, 2, LocalDateTime.now(), 10, false);
        Pedido p2 = new Pedido(2,PMacron, LibBricolage, 5, LocalDateTime.now(), 30, false);
        Pedido p3 = new Pedido(3,SPlana, LibCocina, 4, LocalDateTime.now(), 40, false);
        this.listaPedidos.add(p1);
        this.listaPedidos.add(p2);
        this.listaPedidos.add(p3);
    }
}
