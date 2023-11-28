package Modelo.DAO;

import Modelo.Articulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDAOImpl implements ArticuloDAO {

    private Connection getConecction(){
        return thejavaislandConnection.getConnection();
    }



    @Override
    public void insert(Articulo articulo) {
        Connection conexion = getConecction();
        String query = "INSERT INTO articulos (Id, Descripcion, PrecioDeVenta, GastosDeEnvio, TiempoDePreparacion) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
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
        Connection conexion = getConecction();
        List<Articulo> articulos = new ArrayList<>();
        String query = "SELECT * FROM articulos";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
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
    public Articulo findById(int id) {
        Connection conexion = getConecction();
        String query = "SELECT * FROM Articulo WHERE Id = ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
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
        Connection conexion = getConecction();
        String query = "UPDATE articulos SET Descripcion = ?, PrecioDeVenta = ?, GastosDeEnvio = ?, TiempoDePreparacion = ? WHERE Codigo = ?";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
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