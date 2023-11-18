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

    private Connection connection;

    public PedidoDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Pedido pedido) {
        String query = "INSERT INTO Pedido (NumeroPedido, IdCliente, IdArticulo, CantidadArticulo, FechaHora, PrecioTotal, Enviado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pedido.getNumeroPedido());
            preparedStatement.setInt(2, pedido.getCliente().getNif());
            preparedStatement.setString(3, pedido.getArticulo().getCodigo());
            preparedStatement.setInt(4, pedido.getCantidadArticulos());
            preparedStatement.setObject(5, pedido.getFechaHora());
            preparedStatement.setDouble(6, pedido.getPrecioTotal());
            preparedStatement.setBoolean(7, pedido.getEnviado());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pedido> readAll() {
        List<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT * FROM Pedidos";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
                pedido.getCliente().setId(resultSet.getInt("IdCliente"));
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
        String query = "SELECT * FROM pedidos WHERE numeroPedido = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
                pedido.getCliente().setNif(resultSet.getInt("Nif"));
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
        String query = "UPDATE pedidos SET IdCliente = ?, CodigoArticulo = ?, CantidadArticulos = ?, FechaHora = ?, PrecioTotal = ?, Enviado = ? WHERE numeroPedido = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pedido.getCliente().getNif());
            preparedStatement.setString(2, pedido.getArticulo().getCodigo());
            preparedStatement.setInt(3, pedido.getCantidadArticulos());
            preparedStatement.setObject(4, pedido.getFechaHora());
            preparedStatement.setDouble(5, pedido.getPrecioTotal());
            preparedStatement.setBoolean(6, pedido.getEnviado());
            preparedStatement.setInt(7, pedido.getNumeroPedido());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}