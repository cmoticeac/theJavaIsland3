package Modelo.DAO;
import Modelo.Cliente;
import java.util.List;

public interface ClienteDAO {

    void insert(Cliente cliente);

    List<Cliente> readAll();

    Cliente findById(int id);

    void update(Cliente cliente);
}