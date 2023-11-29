package Modelo.DAO;

import Modelo.Articulo;
import java.util.*;
import java.util.List;

public interface ArticuloDAO {

    void insert(Articulo articulo);

    ArrayList<Articulo> readAll();

    Articulo findById(int id);

    void update(Articulo articulo);
}