package Controlador;


import Modelo.Datos;
import Modelo.Pedido;

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
}
