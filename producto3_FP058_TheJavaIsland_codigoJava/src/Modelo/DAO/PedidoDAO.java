package Modelo.DAO;

package src.Modelo.DAO;

import Modelo.Pedido;

import java.util.List;

public interface PedidoDAO {

    void insert(Pedido pedido);

    List<Pedido> readAll();

    Pedido findById(int numeroPedido);

    void update(Pedido pedido);
}