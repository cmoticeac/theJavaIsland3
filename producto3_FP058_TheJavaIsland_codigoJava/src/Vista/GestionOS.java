package Vista;

import Controlador.Controlador;
import Modelo.*;
import Modelo.DAO.*;
import java.util.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class GestionOS {
    private Controlador controlador;
    Scanner teclado = new Scanner(System.in);
    public GestionOS() {
        ArticuloDAO articuloDAO = new ArticuloDAOImpl();
        ClienteDAO clienteDAO = new ClienteDAOImpl();
        PedidoDAO pedidoDAO = new PedidoDAOImpl();
        controlador = new Controlador(articuloDAO, clienteDAO, pedidoDAO);
    }
    public GestionOS(Controlador controlador) {
        this.controlador = controlador;
    }

    public void Inicio() {
        ArticuloDAO adao = new ArticuloDAOImpl();
        Articulo a = adao.findById(1);
        System.out.println(a.getDescripcion());
        boolean salir = false;
        char opcio;
        do {
            System.out.println("1. Gestión Articulos");
            System.out.println("2. Gestión Clientes");
            System.out.println("3. Gestión Pedidos");
            System.out.println("0. Salir");
            opcio = pedirOpcion(1);
            switch (opcio) {
                case '1':
                    menuArticulos();
                    break;
                case '2':
                    menuCliente();
                    break;
                case '3':
                    menuPedidos();
                    break;

                case '0':
                    salir = true;
            }
        } while (!salir);
    }
    char pedirOpcion(int i) {
        String resp;
        if (i == 1) System.out.println("Elige una opción (1,2,3 o 0): ");
        if (i == 2) System.out.println("Elige una opción (1,2 o 0): ");
        if (i == 3) System.out.println("Elige una opción (1,2,3,4 o 0): ");
        resp = teclado.nextLine();
        if (resp.isEmpty()) {
            resp = " ";
        }
        return resp.charAt(0);
    }


    public void menuArticulos(){
        boolean salir = false;
        char opcio;
        do {
            System.out.println("1. Añadir Articulo");
            System.out.println("2. Imprimir Articulo");
            System.out.println("0. Salir");
            opcio = pedirOpcion(2);
            switch (opcio) {
                case '1' -> recolectarDatosArticulo();
                case '2' -> MostrarArticulo();
                case '0' -> salir = true;
            }
        } while (!salir);
    }

    //Recoge los datos necesarios para crear un nuevo articulo
    //y lo guarda en Datos a través del controlador.
    public void recolectarDatosArticulo(){
        //Variables para la recoleccion de datos
        String codigos, descripciones;
        double precios, envios;
        int tiempos;
        //Inicia la recoleccion de datos
        System.out.println("introduce el codigo\n");
        codigos = teclado.nextLine();
        System.out.println("introduce la descripcion\n");
        descripciones = teclado.nextLine();
        System.out.println("introduce el precio, si tiene decimales separalos usando ( , )\n");
        precios = teclado.nextDouble();
        System.out.println("introduce el Costo de envio si tiene decimales separalos usando ( , )\n");
        envios =teclado.nextDouble();
        System.out.println("introduce el tiempo de envio\n");
        tiempos =teclado.nextInt();
        //Creamos un articulo con los datos recogidos y los guardamos gracias al controlador.
        Articulo articulo = new Articulo(codigos,descripciones, precios, envios,tiempos);
        controlador.añadirArticulo(articulo);
    }


    //Muestra todos los articulos, imprimiendo el código y su descripcion
    //Seguidamente pregunta el codigo de un producto para saber mas informacion
    public void MostrarArticulo() {
        //Bucle para imprimir codigo + descripcion de todos los articulos
        System.out.println("====================Listado de Articulos Disponibles======================");
        System.out.println(controlador.imprimirArticulos());
        //Preguntamos por el codigo del articulo que interesa
        System.out.println("==========================================================================\n");
        System.out.print("Introduce el codigo del articulo que deseas mostrar:\n");
        String codigoIngresado = teclado.next();
        //Lo buscamos en la lista a través del controlador
        Articulo artSelec = controlador.obtenerArticuloCodigo(codigoIngresado);
        if(artSelec != null){
            System.out.println("articulos codigo=" + artSelec.getCodigo() + "\n" +
                    "descripcion=" + artSelec.getDescripcion() + "\n" +
                    "precioDeVenta=" + artSelec.getPrecioDeVenta() + "\n" +
                    "gastosDeEnvio=" + artSelec.getGastosDeEnvio() + "\n" +
                    "tiempoDePreparacion=" + artSelec.getTiempoDePreparacion() + "\n");
        }

        else{
            //En caso contrario informamos al usuario
            System.out.println("No existe ningún articulo con el código que has introducido.");
        }
    }

    public void menuCliente() {
        boolean salir = false;
        char opcio;
        do {
            System.out.println("1. Añadir Clientes");
            System.out.println("2. Mostrar Clientes");
            System.out.println("3. Mostrar Clientes Estandar");
            System.out.println("4. Mostrar Clientes Premium");
            System.out.println("0. Salir");
            opcio = pedirOpcion(3);
            switch (opcio) {
                case '1' -> addCliente();
                case '2' -> imprimirListaClientes();
                case '3' -> imprimirListaClientesEstandar();
                case '4' -> imprimirListaClientesPremium();
                case '0' -> salir = true;
            }
        } while (!salir);
    }



    //Agregar cliente nuevo
    public void addCliente(){
        //Empezamos preguntando por el NIF
        System.out.print("Introduce el NIF: ");
        String nif = teclado.nextLine();
        //Como lo usamos como identificador, primero comprobaremos que no haya sido usado
        while (controlador.clienteExistente(nif) != null){
            System.out.println("\n¡Este cliente ya está registrado!");
            System.out.print("Introduce el NIF: ");
            nif = teclado.nextLine();
        }
        //Seguimos recogiendo datos
        System.out.print("Introduce el nombre del nuevo cliente: ");
        String nombre = teclado.nextLine();
        System.out.print("Indica domicilio: ");
        String addres = teclado.nextLine();
        System.out.print("Indica Email: ");
        String mail = teclado.nextLine();
        System.out.println("Elige el tipo de cliente: 1.Premium  2.Estandar");
        //Dependiendo del tipo de cliente, haremos uso de un caso u otro
        int option = teclado.nextInt();
        switch (option) {
            case 1 -> {
                //Si es premium preguntaremos por los atributos que nos faltan
                System.out.print("Indica cuota: ");
                double cuota = teclado.nextDouble();
                System.out.print("Indica descuento: ");
                double descuento = teclado.nextDouble();
                //Creamos un nuevo cliente premium y lo añadimos a través del controlador
                ClientePremium c1 = new ClientePremium(nombre, addres, nif, mail, cuota, descuento);
                controlador.añadirCliente(c1);
                System.out.println("\nEl cliente se registro correctamente.\n");
            }
            case 2 -> {
                //Si es estandar, crearemos el cliente y lo guardaremos
                ClienteEstandar c2 = new ClienteEstandar(nombre, addres, nif, mail);
                controlador.añadirCliente(c2);
                System.out.println("\nEl cliente se registro correctamente.\n");
            }
            default -> System.out.print("\nDebes introducir un número: ");
        }
    }

    //Imprime la lista completa de cliente por nif y nombre
    public void imprimirListaClientes(){
        //Obtiene la lista de clientes y hace un foreach para imprimirlos todos
        System.out.println("=====================Listado de Clientes registrados========================\n");
        System.out.println(controlador.imprimirClientes());
    }

    //Imprime los clientes premium
    public void imprimirListaClientesPremium(){
        //Uilizamos un foreach en la lista completa de Clientes,
        //pero utilizamos intanceof para saber si es premium
        System.out.println("=====Clientes Premium=====");
        System.out.println(controlador.imprimirClientesPremium());
    }

    //Imprime los clientes estandar
    public void imprimirListaClientesEstandar(){
        //Uilizamos un foreach en la lista completa de Clientes,
        //pero utilizamos intanceof para saber si es premium
        System.out.println("=====Clientes Estandar=====");
        System.out.println(controlador.imprimirClientesEstandar());
    }

    public void menuPedidos() {   //menu de pedidos
        boolean salir = false;
        do {
            System.out.println("ESTA ES LA GESTION DE PEDIDOS:\n");
            System.out.println("1. Añadir pedido.");
            System.out.println("2. Eliminar Pedido");
            System.out.println("3. Mostras pedidos pendientes de envio");
            System.out.println("4. Mostras pedidos enviados");
            System.out.println("0. Salir");

            int resp;
            System.out.println("Elige una opción (1,2,3,4 o 0):");

            resp = teclado.nextInt();

            switch (resp) {
                case 1:
                    menuCrearPedido();
                    break;
                case 2:
                    menuEliminarPedido();
                    break;
                case 3:
                    menuMostrarPendientesEnvio();
                    break;
                case 4:
                    menuMostrarEnviados();
                    break;
                case 0:
                    salir = true;
            }
        } while (!salir);
    }


    //Funcion para crear un pedido nuevo
    public void menuCrearPedido() {
        //Primero creamos un pedido vacio y preguntamos por el número del pedido
        //AÑADIR COMPROBACIÓN DE SI EXISTE PEDIDO
        System.out.println("Añade el numero de pedido: ");
        Pedido pedido= new Pedido();
        int numeroPedido = teclado.nextInt();
        teclado.nextLine();
        pedido.setNumeroPedido(numeroPedido);
        //Preguntamos por si queremos crear un cliente o usar uno ya existente
        System.out.println("1. Escoge un cliente.\n" +
                "2. Crea un cliente.\n");

        int opcion = teclado.nextInt();
        teclado.nextLine();
        if (opcion == 1) {
            //En caso de ya existir, imprimimos la lista de clientes para que el usuario seleccione uno
            System.out.println("=====================Listado de Clientes registrados========================\n");
            imprimirListaClientesEstandar();
            System.out.println("............................................................................\n");
            imprimirListaClientesPremium();
            System.out.println("============================================================================\n");

            System.out.println("Introduce el NIF del cliente:\n ");
            Scanner input2 = new Scanner(System.in);
            String nif = input2.nextLine();
            //cliente registrado
            pedido.setCliente(controlador.conseguirClienteNif(nif));
            //Añadir manejo de errores nif introducido incorrecto
        }else if(opcion == 2){
            //Si no existe, creamos un nuevo cliente
            //MIRAR SI SE PUEDE UTILIZAR EL MENU DE CREAR USUARIO
            boolean salir = false;
            do {
                System.out.println("1. Crear Cliente Estandar");
                System.out.println("2. Crear Cliente Premium");
                System.out.println("0. Salir");

                int opcio;
                System.out.println("Elige una opción (1,2 o 0):");
                opcio = teclado.nextInt();
                teclado.nextLine();
                switch (opcio) {
                    case 1:
                        teclado.nextLine();
                        System.out.println("introduce el Nif");
                        String Nif= teclado.nextLine();
                        System.out.println("introduce el Nombre");
                        String name= teclado.nextLine();
                        System.out.println("introduce la Direccion");
                        String Addres= teclado.nextLine();
                        System.out.println("introduce un correo electronico");
                        String mail= teclado.nextLine();
                        ClienteEstandar cE= new ClienteEstandar(Nif, name, Addres, mail);
                        controlador.añadirCliente(cE);
                        pedido.setCliente(cE);
                        System.out.println("Cliente standar añadido.");
                        teclado.nextLine();
                        salir=true;
                        break;
                    case 2:
                        teclado.nextLine();
                        System.out.println("introduce el Nif");
                        String nif= teclado.nextLine();
                        System.out.println("introduce el Nombre");
                        String Name= teclado.nextLine();
                        System.out.println("introduce la Direccion");
                        String dir= teclado.nextLine();
                        System.out.println("introduce un correo electronico");
                        String email= teclado.nextLine();
                        System.out.println("introduce la cuota mensual del socio");
                        double quotaCliente= teclado.nextDouble();
                        System.out.println("introduce el porcentage de descuento");
                        double porDes= teclado.nextDouble();
                        ClientePremium cP= new ClientePremium(nif, Name, dir, email, quotaCliente, porDes);
                        controlador.añadirCliente(cP);
                        pedido.setCliente(cP);
                        System.out.println("Cliente premium añadido.");
                        salir=true;
                        teclado.nextLine();
                        break;
                    case 0:
                        salir = true;
                }
            } while (!salir);
        }

        //Imprimimos todos los artículos
        System.out.println("====================Listado de Articulos Disponibles======================");
        System.out.println(controlador.imprimirArticulos());
        System.out.println("==========================================================================\n");

        //Preguntamos el codigo del articulo que nos interessa y lo buscamos en listaArticulos
        System.out.println("Introduce el codigo del articulo para añadirlo al pedido: ");
        String codigo = teclado.nextLine();
        if(controlador.obtenerArticuloCodigo(codigo) != null) pedido.setArticulo(controlador.obtenerArticuloCodigo(codigo));
        //Preguntamos cuantas unidades del artículo quiere el cliente

        System.out.println("Introduce la cantidad de articulos: ");
        int cantidadArticulos = teclado.nextInt();
        teclado.nextLine();
        pedido.setCantidadArticulos(cantidadArticulos);

        //Terminamos de crear el pedido utilizando un Date para calcular el tiempo de envio
        LocalDateTime horaActual= LocalDateTime.now();
        pedido.setFechaHora(horaActual);
        pedido.setEnviado(false);
        controlador.añadirPedido(pedido);
    }

    //Metodo para eliminar pedidos que aun no han sido enviados
    public void menuEliminarPedido(){
        controlador.actualizarEnvios();
        System.out.println("MENU PARA ELIMINAR PEDIDOS\n");
        System.out.println("Introduce el numero del pedido.\n" +
                "(Recuerda que solo podras eliminar pedidos PENDIENTES DE ENVIO!)\n");

        int codigo = teclado.nextInt();
        if (controlador.eliminarPedido(codigo)) System.out.println("Pedido " +codigo+ " borrado con exito!");
        else System.out.println("El pedido no se ha podio borrar. El codigo introducido no existe o el pedido ya ha sido enviado");
    }

    //Metodo para mostrar los pedidos aun por enviar
    public void menuMostrarPendientesEnvio(){
        controlador.actualizarEnvios();
        System.out.println("\nLista de los pedidos PENDIENTES de envio:\n");
        System.out.println("-----------------------------------------------");
        System.out.println(controlador.obtenerPedidosPendientes());
            System.out.println("-----------------------------------------------");
        }


    //Metodo para mostrar los pedidos enviados
    public void menuMostrarEnviados(){
        controlador.actualizarEnvios();
        System.out.println("\nLista de los pedidos ENVIADOS:\n");
        System.out.println("-----------------------------------------------");
        System.out.println(controlador.obtenerPediosEnviados());
            System.out.println("-----------------------------------------------");
        }
}