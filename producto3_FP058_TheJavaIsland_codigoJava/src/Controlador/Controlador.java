package Controlador;
import Modelo.*;
import Modelo.DAO.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class Controlador {

    //Atributos
    private ArticuloDAO articuloDAO;
    private ClienteDAO clienteDAO;
    private PedidoDAO pedidoDAO;

    //Contructor
    public Controlador(ArticuloDAO articuloDAO, ClienteDAO clienteDAO, PedidoDAO pedidoDAO) {
        this.articuloDAO = articuloDAO;
        this.clienteDAO = clienteDAO;
        this.pedidoDAO = pedidoDAO;
    }

    public ArticuloDAO getArticuloDAO() {
        return articuloDAO;
    }

    public void setArticuloDAO(ArticuloDAO articuloDAO) {
        this.articuloDAO = articuloDAO;
    }

    public ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    public void setClienteDAO(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public PedidoDAO getPedidoDAO() {
        return pedidoDAO;
    }

    public void setPedidoDAO(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }


    //Metodos
    public void actualizarEnvios() {
        // Obtener la lista de pedidos desde la base de datos
        ArrayList<Pedido> pedidos = pedidoDAO.readAll();
        for (Pedido p : pedidos) {
            Duration diferencia = Duration.between(p.getFechaHora(), LocalDateTime.now());
            double segundos = diferencia.getSeconds();
            if ((p.getArticulo().getTiempoDePreparacion() * p.getCantidadArticulos()) < segundos) {
                p.setEnviado(true);
                // Actualizar el estado del pedido en la base de datos
                pedidoDAO.update(p);
            }
        }
    }
    public void añadirArticulo(Articulo articulo) {
        // Insertar el artículo en la base de datos
        articuloDAO.insert(articulo);
    }
    public Boolean clienteExistente(String nif) {
        // Verificar si el cliente existe en la base de datos
        return clienteDAO.findById(nif);
    }

    public void añadirCliente(Cliente cliente) {
        // Insertar el cliente en la base de datos
        clienteDAO.insert(cliente);
    }

    public Cliente conseguirClienteNif(String nif) {
        // Obtener el cliente por su NIF desde la base de datos
        return clienteDAO.findById(nif);
    }

    public String imprimirArticulos() {
        // Obtener la lista de artículos desde la base de datos
        ArrayList<Articulo> articulos = articuloDAO.readAll();

        StringBuilder result = new StringBuilder();
        for (Articulo art : articulos) {
            result.append(art.getCodigo()).append(" ").append(art.getDescripcion()).append(" ").append(art.getPrecioDeVenta()).append("€\n");
        }
        return result.toString();
    }
    public Articulo obtenerArticuloCodigo(String codigo) {
        // Obtener el artículo por su código desde la base de datos
        return articuloDAO.findById(codigo);
    }

    public String imprimirClientes() {
        // Obtener la lista de clientes desde la base de datos
        ArrayList<Cliente> clientes = clienteDAO.readAll();

        StringBuilder result = new StringBuilder();
        for (Cliente cl : clientes) {
            result.append(cl.getNif()).append(" ").append(cl.getNombre()).append("\n");
        }
        return result.toString();
    }

    public String imprimirClientesPremium() {
        // Obtener la lista de clientes premium desde la base de datos
        ArrayList<Cliente> clientesPremium = clienteDAO.readAll();

        StringBuilder result = new StringBuilder();
        for (Cliente cl : clientesPremium) {
            result.append(cl.getNif()).append(" ").append(cl.getNombre()).append("\n");
        }
        return result.toString();
    }

    public String imprimirClientesEstandar() {
        // Obtener la lista de clientes estándar desde la base de datos
        ArrayList<Cliente> clientesEstandar = clienteDAO.readAll();

        StringBuilder result = new StringBuilder();
        for (Cliente cl : clientesEstandar) {
            result.append(cl.getNif()).append(" ").append(cl.getNombre()).append("\n");
        }
        return result.toString();
    }

    public void añadirPedido(Pedido pedido) {
        // Insertar el pedido en la base de datos
        pedidoDAO.insert(pedido);
    }
    public boolean eliminarPedido(int codigo) {
        Pedido pedido = pedidoDAO.findById(codigo);
        if (pedido != null && !pedido.getEnviado()) {
            pedidoDAO.delete(codigo);
            return true;
        }
        return false;
    }

    public String obtenerPedidosPendientes() {
        // Obtener la lista de pedidos pendientes desde la base de datos
        ArrayList<Pedido> pedidosPendientes = pedidoDAO.readAll();

        StringBuilder result = new StringBuilder();
        for (Pedido p : pedidosPendientes) {
            if (!p.getEnviado()) result.append(p.getNumeroPedido()).append(" ").append(p.getPrecioTotal()).append("\n");
        }
        return result.toString();
    }
    public String obtenerPediosEnviados() {
        // Obtener la lista de pedidos enviados desde la base de datos
        ArrayList<Pedido> pedidosEnviados = pedidoDAO.readAll();

        StringBuilder result = new StringBuilder();
        for (Pedido p : pedidosEnviados) {
            if (p.getEnviado()) result.append(p.getNumeroPedido()).append(" ").append(p.getPrecioTotal()).append("\n");
        }
        return result.toString();
    }

}
