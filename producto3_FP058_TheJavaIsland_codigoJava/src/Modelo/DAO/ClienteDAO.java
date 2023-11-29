package Modelo.DAO;
import Modelo.Cliente;
import java.util.*;

public interface ClienteDAO {

    void insert(Cliente cliente);

    ArrayList<Cliente> readAll();

    Cliente findById(String id);

    void update(Cliente cliente);
}