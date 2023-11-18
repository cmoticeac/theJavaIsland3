package Modelo.DAO;
import Modelo.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    private Connection connection;

    public ClienteDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Modelo.Cliente cliente) {
        String query = "INSERT INTO clientes (Nombre, Domicilio, NIF, Email, TipoCliente, CuotaMensual, Descuento) VALUES (?, ?, ?, ?,?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getDomicilio());
            preparedStatement.setString(3, cliente.getNif());
            preparedStatement.setString(4, cliente.getEmail());
            if (cliente instanceof Modelo.ClientePremium){
                Modelo.ClientePremium cp = (Modelo.ClientePremium) cliente;
                preparedStatement.setBoolean(5, true);
                preparedStatement.setDouble(6, cp.getCuota());
                preparedStatement.setDouble(7, cp.getDescuentoEnvio());
            }
            else{
                preparedStatement.setBoolean(5, false);
                preparedStatement.setString(6, null);
                preparedStatement.setString(7, null);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Modelo.Cliente> readAll() {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM clientes";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Modelo.Cliente cliente = null;
                cliente.setNombre(resultSet.getString("Nombre"));
                cliente.setDomicilio(resultSet.getString("Domicilio"));
                cliente.setNif(resultSet.getString("Nif"));
                cliente.setEmail(resultSet.getString("Email"));
                if (resultSet.getBoolean("TipoCliente") == false){
                    Modelo.ClienteEstandar ce = (Modelo.ClienteEstandar) cliente;
                    clientes.add(ce);
                }
                else{
                    Modelo.ClientePremium cp = (Modelo.ClientePremium) cliente;
                    cp.setCuota(resultSet.getDouble("CuotaMensual"));
                    cp.setDescuentoEnvio(resultSet.getDouble("Descuento"));
                    clientes.add(cp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public Modelo.Cliente findById(int id) {
        String query = "SELECT * FROM clientes WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Modelo.Cliente cliente = null;
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setDomicilio(resultSet.getString("domicilio"));
                cliente.setNif(resultSet.getString("nif"));
                cliente.setEmail(resultSet.getString("email"));
                if (resultSet.getBoolean("TipoCliente") == false){
                    Modelo.ClienteEstandar ce = (Modelo.ClienteEstandar) cliente;
                    return ce;
                }
                else{
                    Modelo.ClientePremium cp = (Modelo.ClientePremium) cliente;
                    cp.setCuota(resultSet.getDouble("CuotaMensual"));
                    cp.setDescuentoEnvio(resultSet.getDouble("Descuento"));
                    return cp;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Cliente cliente) {
        String query = "UPDATE clientes SET nombre = ?, domicilio = ?, nif = ?, email = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getDomicilio());
            preparedStatement.setString(3, cliente.getNif());
            preparedStatement.setString(4, cliente.getEmail());
            preparedStatement.setInt(5, cliente.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}