package Modelo.DAO;

import java.util.List;

public interface FactoryDAOImpl<T, R> {
    void insert(T entity);
    List<T> list();
    void update(T entity);
    void delete(T entity);
    T read(R id);
}
