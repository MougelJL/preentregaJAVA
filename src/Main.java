import preentrega.pedido.Pedido;
import preentrega.producto.Producto;
import preentrega.producto.StockInsuficienteException;
import preentrega.sistema.ProductoNoEncontradoException;
import preentrega.sistema.Sistema;

import javax.swing.*;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws StockInsuficienteException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        //System.out.printf("Hello and welcome!");

        SistemaService();

    }

    public static void SistemaService () {
        Sistema sistema = new Sistema();
        Scanner scanner = new Scanner(System.in);
        String entrada = "";

        while (!entrada.equals("X")) {
            System.out.printf
                    ("[A/a] Agregar un producto \n" +
                            "[L/l] Listar todos los productos \n" +
                            "[B/b] Buscar un producto\n" +
                            "[U/u] Actuliazar un producto\n" +
                            "[E/e] Eliminar un producto\n" +
                            "[AP/Ap] Crear un nuevo pedido\n" +
                            "[LP/lp] Listar todos los pedidos\n" +
                            "[X/x] Salir \n");

            entrada = scanner.nextLine().toUpperCase();

            switch (entrada){
                //Agregar Producto
                case "A":
                        try {
                            System.out.printf("Ingrese el nombre del producto\n");
                            String nombre = scanner.nextLine();
                            int stock = intValido("Introduzca el stock del producto\n","El stock debe ser un numero entero.\n");
                            double precio = doubleValido("Introduza el precio del producto.\n","El precio deber ser un numero decimal.\n");
                            sistema.agregarProducto(nombre, stock, precio);

                        } catch (Exception e) {}
                        break;
                    //Listar Productos
                case  "L": {
                    {
                        if (sistema.getProductos_cargados().size() < 1) {
                            System.out.printf("El sistema no tiene cargado ningun producto.\n" +
                                    "Ingrese un nuevo producto primero.\n\n");
                        } else {
                            sistema.listarProductos();
                        }
                    }
                }
                    break;

                // Buscar un producto por ID
                case  "B":
                    try {
                        int id = intValido("Introduzca el numero de ID del producto.\n", "El numero de ID debe ser un número entero.\n"); //chequeo que sea valido

                        if (sistema.productoCargado(id)) {
                            Producto producto = sistema.buscarProducto(id);
                            System.out.printf(producto.toString());
                        }else {
                            System.out.printf("No existe ningun producto con el numero de ID #%d cargado en sistema", id);
                        }
                    }catch (Exception e) {}
                    break;

                //Actualizar un producto por ID
                case  "U":
                    try {
                        int id = intValido("Introduzca el numero de ID del producto.\n","El número de ID debe ser un número entero.\n");

                        if (!sistema.productoCargado(id)) {
                            System.out.printf("No existe ningun producto con el numero de ID #%d cargado en sistema", id);
                        }else {
                            int nuevo_stock = intValido("Introduzca el stock.\n","El stock debe ser un numero entero.\n");
                            double nuevo_precio = doubleValido("Introduza el precio del producto.\n","EL precio debe ser un numero decimal.\n");
                            sistema.actualizarProducto(id, nuevo_stock, nuevo_precio);
                        }
                    } catch (Exception e) {}
                    break;

                //Eliminar un producto por ID
                case "E":
                    try {
                        int id = intValido("Introduzca el numero de ID del producto.\n", "El ID  debe ser un número entero.\n");

                        if(!sistema.productoCargado(id)) {
                            System.out.printf("No existe ningun producto con el numero de ID #%d cargado en sistema", id);
                        }
                        else {
                            sistema.eliminarProducto(id);
                        }
                    } catch (NumberFormatException e) {}
                    break;

                //Agregar un Pedido (cantidad de prodoductos validos
                case "AP":
                    try {
                        int cantidad_productos = intValido("Introduzca cuantos productos diferentes desea pedir.\n",
                                "Intrada invalida. Ingrese un numero entero.\n");

                        Pedido pedido = new Pedido();

                        while (cantidad_productos > 0) {
                            int id = intValido("Ingrese el ID del producto.\n",
                                             "Entrada invalida. Ingrese un numero entero.\n");

                            int cantidad_pedido = intValido(  "Ingrese la cantidad que desea ordenar.\n",
                                                            "Entrada invalida. Ingrese un numero entero.\n");

                            Producto producto = sistema.buscarProducto(id);
                            pedido.append(producto, cantidad_pedido);
                            cantidad_productos --;
                        }

                        //Para la comfirmacion del pedido
                        System.out.printf("¿Desea confrimar el siguiente pedido?\n");
                        pedido.print();
                        System.out.printf("[si][no] (default es no)\n");

                        String confrimacion = scanner.nextLine();

                        if (confrimacion.equals("si")) {
                            pedido.restarStock();
                            sistema.agregarPedido(pedido);
                        }
                        break;
                    } catch (StockInsuficienteException s) {
                        System.out.printf("No hay suficiente stock para el pedido");
                        break;
                    } catch (NumberFormatException e) {break;}
                    catch (ProductoNoEncontradoException p) {System.out.printf("No se a encontrado ese producto en el sistema");}


                case "LP":
                    for (Pedido pedido: sistema.getPedidos()){
                        pedido.print();
                    }
                   break;

                default: break;
            }

        }
    }

    public static int intValido (String entrada,String excepcion) throws NumberFormatException {
        System.out.print(entrada);
        Scanner scanner = new Scanner(System.in);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.printf(excepcion);
            throw new NumberFormatException();
        }

    }

    public static double doubleValido (String entrada, String excepcion) throws NumberFormatException {
        System.out.printf(entrada);
        Scanner scanner = new Scanner(System.in);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.printf(excepcion);
            throw new NumberFormatException();
        }

    }
}