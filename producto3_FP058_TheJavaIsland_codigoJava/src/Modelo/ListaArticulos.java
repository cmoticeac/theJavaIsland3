package Modelo;

import java.util.ArrayList;

public class ListaArticulos extends Lista<Articulo>{

    //Contructor
    public ListaArticulos() {
        ListaArticulos.super.lista = new ArrayList<Articulo>();
    }


}
