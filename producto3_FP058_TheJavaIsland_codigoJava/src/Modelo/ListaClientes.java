package Modelo;

import java.util.ArrayList;

public class ListaClientes extends Lista<Cliente>{

    //Contructor
    public ListaClientes() {
        ListaClientes.super.lista = new ArrayList<Cliente>();
    }


}