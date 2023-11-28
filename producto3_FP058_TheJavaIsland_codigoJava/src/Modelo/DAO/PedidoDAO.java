package Modelo.DAO;
import Modelo.Pedido;
import java.sql.Connection;
import java.util.List;

public interface PedidoDAO {
    private Connection getConecction(){
        return thejavaislandConnection.getConnection();
    }
    void insert(Pedido pedido);

    List<Pedido> readAll();

    Pedido findById(int numeroPedido);

    void update(Pedido pedido);
}