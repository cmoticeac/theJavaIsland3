package Modelo.DAO;

public interface FactoryDAOImpl<T, R>{
    public void insert(T o);
    public ArrayList<T> list();
    public void update(T, o);
    public void delete(T o);
    public T read(R o);
}
