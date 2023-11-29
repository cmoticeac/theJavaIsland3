package Modelo.DAO;
import Modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
public class ClienteDAOImpl implements ClienteDAO {

    private Connection getConecction(){
        return thejavaislandConnection.getConnection();
    }

    @Override
    public void insert(Modelo.Cliente cliente) {
        Connection conexion = getConecction();
        try {
            // Iniciar la transacción
            conexion.setAutoCommit(false);

            String callProcedure = "{call InsertCliente(?, ?, ?, ?, ?, ?, ?)}";
            try (CallableStatement callableStatement = conexion.prepareCall(callProcedure)) {
                callableStatement.setString(1, cliente.getNombre());
                callableStatement.setString(2, cliente.getDomicilio());
                callableStatement.setString(3, cliente.getNif());
                callableStatement.setString(4, cliente.getEmail());

                // Verificar si es ClientePremium
                if (cliente instanceof Modelo.ClientePremium) {
                    Modelo.ClientePremium cp = (Modelo.ClientePremium) cliente;
                    callableStatement.setBoolean(5, true);
                    callableStatement.setDouble(6, cp.getCuota());
                    callableStatement.setDouble(7, cp.getDescuentoEnvio());
                } else {
                    callableStatement.setBoolean(5, false);
                    callableStatement.setNull(6, java.sql.Types.DOUBLE);
                    callableStatement.setNull(7, java.sql.Types.DOUBLE);
                }

                callableStatement.executeUpdate();
            }

            // Confirmar la transacción si todo está bien
            conexion.commit();
        } catch (SQLException e) {
            // Revertir la transacción en caso de error
            try {
                if (conexion != null) {
                    conexion.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace(); // Manejo básico de errores. Puedes personalizar esto según tus necesidades.
        } finally {
            // Restaurar el modo de autocommit al final de la operación
            try {
                if (conexion != null) {
                    conexion.setAutoCommit(true);
                }
            } catch (SQLException autoCommitException) {
                autoCommitException.printStackTrace();
            }

            // Cerrar la conexión
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Modelo.Cliente> readAll() {
        Connection conexion = getConecction();
        ArrayList<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM clientes";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
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

    public Modelo.Cliente findById(String id) {
        Connection conexion = getConecction();
        String query = "SELECT * FROM clientes WHERE id = ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
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
        Connection conexion = getConecction();
        try {
            // Iniciar la transacción
            conexion.setAutoCommit(false);

            String query = "UPDATE clientes SET nombre = ?, domicilio = ?, nif = ?, email = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
                preparedStatement.setString(1, cliente.getNombre());
                preparedStatement.setString(2, cliente.getDomicilio());
                preparedStatement.setString(3, cliente.getNif());
                preparedStatement.setString(4, cliente.getEmail());
                preparedStatement.setString(5, cliente.getNif());

                preparedStatement.executeUpdate();
            }

            // Confirmar la transacción si todo está bien
            conexion.commit();
        } catch (SQLException e) {
            // Revertir la transacción en caso de error
            try {
                if (conexion != null) {
                    conexion.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace(); // Manejo básico de errores. Puedes personalizar esto según tus necesidades.
        } finally {
            // Restaurar el modo de autocommit al final de la operación
            try {
                if (conexion != null) {
                    conexion.setAutoCommit(true);
                }
            } catch (SQLException autoCommitException) {
                autoCommitException.printStackTrace();
            }

            // Cerrar la conexión
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
    }

}