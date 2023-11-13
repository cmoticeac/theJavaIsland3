package Modelo;

import java.util.ArrayList;

public class ListaPedidos extends Lista<Pedido>{

    //Contructor
    public ListaPedidos() {
        ListaPedidos.super.lista = new ArrayList<Pedido>();
    }

}
