package Modelo.DAO;
import Modelo.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {

    private Connection getConecction(){
        return thejavaislandConnection.getConnection();
    }

    @Override
    public void insert(Pedido pedido) {
        Connection conexion = getConecction();
        try {
            // Iniciar la transacción
            conexion.setAutoCommit(false);

            String query = "INSERT INTO Pedido (NumeroPedido, IdCliente, IdArticulo, CantidadArticulo, FechaHora, PrecioTotal, Enviado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
                preparedStatement.setInt(1, pedido.getNumeroPedido());
                preparedStatement.setString(2, pedido.getCliente().getNif());
                preparedStatement.setString(3, pedido.getArticulo().getCodigo());
                preparedStatement.setInt(4, pedido.getCantidadArticulos());
                preparedStatement.setObject(5, pedido.getFechaHora());
                preparedStatement.setDouble(6, pedido.getPrecioTotal());
                preparedStatement.setBoolean(7, pedido.getEnviado());

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


    @Override
    public List<Pedido> readAll() {
        Connection conexion = getConecction();
        List<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT * FROM Pedidos";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pedido pedido = new Pedido(
                        resultSet.getInt("NumeroPedido"),
                        null, // Cliente
                        null, // Articulo
                        resultSet.getInt("CantidadArticulos"),
                        resultSet.getObject("FechaHora", LocalDateTime.class),
                        resultSet.getDouble("PrecioTotal"),
                        resultSet.getBoolean("Enviado")
                );
                pedido.getCliente().setNif(resultSet.getString("NifCliente"));
                pedido.getArticulo().setCodigo(resultSet.getString("IdArticulo"));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    @Override
    public Pedido findById(int numeroPedido) {
        Connection conexion = getConecction();
        String query = "SELECT * FROM pedidos WHERE numeroPedido = ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setInt(1, numeroPedido);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Pedido pedido = new Pedido(
                        resultSet.getInt("NumeroPedido"),
                        null, // Cliente
                        null, // Articulo
                        resultSet.getInt("CantidadArticulos"),
                        resultSet.getObject("FechaHora", LocalDateTime.class),
                        resultSet.getDouble("PrecioTotal"),
                        resultSet.getBoolean("Enviado")
                );
                pedido.getCliente().setNif(resultSet.getString("Nif"));
                pedido.getArticulo().setCodigo(resultSet.getString("IdArticulo"));
                return pedido;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Pedido pedido) {
        Connection conexion = getConecction();
        try {
            // Iniciar la transacción
            conexion.setAutoCommit(false);

            String query = "UPDATE pedidos SET IdCliente = ?, CodigoArticulo = ?, CantidadArticulos = ?, FechaHora = ?, PrecioTotal = ?, Enviado = ? WHERE numeroPedido = ?";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
                preparedStatement.setString(1, pedido.getCliente().getNif());
                preparedStatement.setString(2, pedido.getArticulo().getCodigo());
                preparedStatement.setInt(3, pedido.getCantidadArticulos());
                preparedStatement.setObject(4, pedido.getFechaHora());
                preparedStatement.setDouble(5, pedido.getPrecioTotal());
                preparedStatement.setBoolean(6, pedido.getEnviado());
                preparedStatement.setInt(7, pedido.getNumeroPedido());

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