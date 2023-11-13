package Modelo;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Lista<T> {

    //Atributo
    protected ArrayList<T> lista;

    //Contructor
    public Lista(){
        lista = new ArrayList<>();
    }

    //toString
    @Override
    public String toString(){
        String imprimir = new String();
        for(T t: lista){
            imprimir +=  t.toString() + "\n";
        }
        return imprimir;
    }


    //Getter y setter
    public ArrayList<T> getLista() {return lista;}
    public void setLista(ArrayList<T> l) {this.lista = l;}

    //Metodos para las listas

    public int getSize() {
        return lista.size();
    }
    public void add(T t){
        lista.add(t);
    }

    public void borrar(T t){
        for (T f: lista){
            if (f == t){
                lista.remove(t);
                return;
            }
        }
        System.out.println("No existe el objeto en la lista");
    }

    public T getAt(int posicion){
        return lista.get(posicion);
    }
}
