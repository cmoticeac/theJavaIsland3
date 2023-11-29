package Modelo.DAO;
import Modelo.Cliente;
import Modelo.ClienteEstandar;
import Modelo.ClientePremium;

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
        String query = "SELECT * FROM cliente";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getBoolean("TipoCliente") == false){
                    Modelo.ClienteEstandar ce = new ClienteEstandar();
                    ce.setNombre(resultSet.getString("Nombre"));
                    ce.setDomicilio(resultSet.getString("Domicilio"));
                    ce.setNif(resultSet.getString("NIF"));
                    ce.setEmail(resultSet.getString("Email"));
                    clientes.add(ce);
                }
                else{
                    Modelo.ClientePremium cp = new ClientePremium();
                    cp.setNombre(resultSet.getString("Nombre"));
                    cp.setDomicilio(resultSet.getString("Domicilio"));
                    cp.setNif(resultSet.getString("NIF"));
                    cp.setEmail(resultSet.getString("Email"));
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
        String query = "SELECT * FROM cliente WHERE NIF = ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setString(1, (id));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getBoolean("TipoCliente") == false){
                    Modelo.ClienteEstandar ce = new ClienteEstandar();
                    ce.setNombre(resultSet.getString("Nombre"));
                    ce.setDomicilio(resultSet.getString("Domicilio"));
                    ce.setEmail(resultSet.getString("Email"));
                    return ce;
                } else{
                    Modelo.ClientePremium cp = new ClientePremium();
                    cp.setNombre(resultSet.getString("Nombre"));
                    cp.setDomicilio(resultSet.getString("Domicilio"));
                    cp.setEmail(resultSet.getString("Email"));
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

            String query = "UPDATE cliente SET Nombre = ?, Domicilio = ?, Email = ? WHERE NIF = ?";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
                preparedStatement.setString(1, cliente.getNombre());
                preparedStatement.setString(2, cliente.getDomicilio());
                preparedStatement.setString(4, cliente.getEmail());

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