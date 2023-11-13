package Vista;
import Controlador.Controlador;
import Modelo.Articulo;
import Modelo.ClientePremium;
import Modelo.Pedido;
import Modelo.ClienteEstandar;
import Vista.GestionOS;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class TestJUnit extends TestCase {
    private Pedido pedido;
    private ClientePremium cliente;

    public void escenario (){  pedido = new Pedido(); }  //cada test debe de tener su propia instancia

    @Test
    public void testPrecioEnvioClienteE() {
        escenario();
        double precioDeVenta = 10;
        double cantidadArticulos = 50;
        double gastosEnvio = 5;
        double descuento = 0;
        ClienteEstandar clienteEstandar = new ClienteEstandar("Alejandro Maximo", "Pablo Picasso, 3", "56321456L", "amaximo@uoc.edu");
        double resultado = pedido.precioEnvio(precioDeVenta, cantidadArticulos, gastosEnvio, clienteEstandar);
        double expected = 505;
        assertEquals(expected, resultado, 0.01);
        System.out.println("La funcion(PrecioEnvio) funciona porque el calculo realizado es correcto.");
    }
    @Test
    public void testPrecioEnvioClienteP() {
        escenario();
        double precioDeVenta = 10;
        double cantidadArticulos = 50;
        double gastosEnvio = 5;
        double descuento = 10.0;
        ClientePremium clientePremium = new ClientePremium("Alejandro Maximo", "Pablo Picasso, 3", "56321456L", "amaximo@uoc.edu", 10.0, 5.0);
        double resultado = pedido.precioEnvio(precioDeVenta, cantidadArticulos, gastosEnvio, clientePremium);
        double expected = (precioDeVenta * cantidadArticulos) + (1 - (descuento / 100)) * gastosEnvio;
        assertEquals(expected, resultado, 0.01); // Verificar que el resultado sea el esperado
        assertEquals(expected, resultado, 0.01);
        System.out.println("La funcion(PrecioEnvio) funciona porque el calculo realizado es correcto.");
    }
    @Test
    public void testPedidoEnviado(){
        escenario();
        long duracionPreparacion= 12;
        LocalDateTime FechaPedido = LocalDateTime.now();
        assertFalse("FALLO: El pedido consta enviado pero no ha superado el tiempo de preparacion.",pedido.pedidoEnviado(FechaPedido,duracionPreparacion));
        System.out.println("La funcion(pedidoEnviado) funciona porque no se ha enviado el pedido todabia.");
    }
    @Before
    public void setUp() {
        pedido = new Pedido();
    }

    @Test
    public void testGetPrecioTotal() {
        Articulo articulo = new Articulo("lib009", "Historia de las redes", 10.0, 3.0, 48);
        pedido.setArticulo(articulo);
        pedido.setCantidadArticulos(1);

        double precioDeVenta = articulo.getPrecioDeVenta();
        double cantidadArticulos = pedido.getCantidadArticulos();
        double gastosEnvio = articulo.getGastosDeEnvio();
        ClienteEstandar clienteEstandar = new ClienteEstandar("Alejandro Maximo", "Pablo Picasso, 3", "56321456L", "amaximo@uoc.edu");
        pedido.setCliente(clienteEstandar);

        double resultado = pedido.getPrecioTotal();
        double expected = 13.0;
        assertEquals(expected, resultado, 0.01);
    }
    @Test
    public void testAgregarClienteNuevo() {
        escenario();
        ClientePremium clientePremium = new ClientePremium("Juan Perez", "Carretera de Barcelona, 150", "12345678X", "jperez@uoc.edu", 10.0, 5.0);
        pedido.setCliente(clientePremium);

        // Verifica que los atributos del cliente se hayan configurado correctamente.
        assertNotNull(pedido.getCliente());
        assertEquals("Juan Perez", pedido.getCliente().getNombre());  // Utiliza pedido.getCliente() en lugar de cliente.getNombre()
        assertEquals("Carretera de Barcelona, 150", pedido.getCliente().getDomicilio());
        assertEquals("12345678X", pedido.getCliente().getNif());
        assertEquals("jperez@uoc.edu", pedido.getCliente().getEmail());
        assertEquals(10.0, ((ClientePremium)pedido.getCliente()).getDescuentoEnvio(), 0.01);  // Asegúrate de que el cliente sea de tipo ClientePremium
        assertEquals(5.0, ((ClientePremium)pedido.getCliente()).getCuota(), 0.01);  // Asegúrate de que el cliente sea de tipo ClientePremium
    }
}
