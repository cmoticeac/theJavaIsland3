package Modelo.DAO;

import Modelo.Articulo;
import java.util.*;

public interface ArticuloDAO {

    void insert(Articulo articulo);

    ArrayList<Articulo> readAll();

    Articulo findById(String id);

    void update(Articulo articulo);
}