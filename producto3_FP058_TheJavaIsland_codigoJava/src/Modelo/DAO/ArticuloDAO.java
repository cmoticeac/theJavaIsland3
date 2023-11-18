package Modelo.DAO;
package Modelo
import Modelo.Articulo;

import java.util.List;

public interface ArticuloDAO {

    void insert(Modelo.Articulo articulo);

    List<Articulo> readAll();

    Articulo findById(String codigo);

    void update(Articulo articulo);
}