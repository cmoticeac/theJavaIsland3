package Modelo.DAO;

package src.Modelo.DAO;
import Modelo.Articulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDAOImpl implements ArticuloDAO {

    private Connection connection;

    public ArticuloDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Articulo articulo) {
        String query = "INSERT INTO articulos (Id, Descripcion, PrecioDeVenta, GastosDeEnvio, TiempoDePreparacion) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, articulo.getCodigo());
            preparedStatement.setString(2, articulo.getDescripcion());
            preparedStatement.setDouble(3, articulo.getPrecioDeVenta());
            preparedStatement.setDouble(4, articulo.getGastosDeEnvio());
            preparedStatement.setDouble(5, articulo.getTiempoDePreparacion());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo básico de errores. Puedes personalizar esto según tus necesidades.
        }
    }

    @Override
    public List<Articulo> readAll() {
        List<Articulo> articulos = new ArrayList<>();
        String query = "SELECT * FROM articulos";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Articulo articulo = new Articulo(
                        resultSet.getString("Codigo"),
                        resultSet.getString("Descripcion"),
                        resultSet.getDouble("PrecioDeVenta"),
                        resultSet.getDouble("GastosDeEnvio"),
                        resultSet.getDouble("TiempoDePreparacion")
                );
                articulos.add(articulo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articulos;
    }

    @Override
    public Articulo findById(String codigo) {
        String query = "SELECT * FROM articulos WHERE Id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, codigo);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Articulo(
                        resultSet.getString("Id"),
                        resultSet.getString("Descripcion"),
                        resultSet.getDouble("PrecioDeVenta"),
                        resultSet.getDouble("GastosDeEnvio"),
                        resultSet.getDouble("TiempoDePreparacion")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retornamos null si no se encuentra el artículo
    }

    @Override
    // Método para actualizar un artículo en la base de datos
    public void update(Articulo articulo) {
        String query = "UPDATE articulos SET Descripcion = ?, PrecioDeVenta = ?, GastosDeEnvio = ?, TiempoDePreparacion = ? WHERE Codigo = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, articulo.getDescripcion());
            preparedStatement.setDouble(2, articulo.getPrecioDeVenta());
            preparedStatement.setDouble(3, articulo.getGastosDeEnvio());
            preparedStatement.setDouble(4, articulo.getTiempoDePreparacion());
            preparedStatement.setString(5, articulo.getCodigo());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}