package preentrega.sistema;

import preentrega.pedido.Pedido;
import preentrega.producto.Producto;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.spi.AbstractResourceBundleProvider;

public class Sistema {
    private ArrayList<Producto> productos_cargados;
    private ArrayList<Pedido> pedidos;

    public Sistema (){
        this.productos_cargados = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    public Sistema(ArrayList<Producto> productos_cargados) {
        this.productos_cargados = productos_cargados;
    }

    public ArrayList<Producto> getProductos_cargados() {
        return productos_cargados;
    }

    public void setProductos_cargados(ArrayList<Producto> productos_cargados) {
        this.productos_cargados = productos_cargados;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public boolean productoCargado (Producto producto) {
        for (Producto cargado: productos_cargados) {

            if (cargado == producto) {
                return  true;
            }
        }
        return  false;
    }

    public boolean productoCargado (String nombre) {
        for (Producto cargado: productos_cargados){
            if (cargado.getNombre().equals(nombre)) {return  true;}

        }
        return  false;
    }

    public boolean productoCargado (int id) {
        for (Producto cargado: productos_cargados) {
            if (cargado.getId() == id) {return  true;}
        }
        return false;
    }

    public void agregarProducto (String nombre, int stock, double precio) throws ProductoCargadoException{

        if (productoCargado(nombre)) {
            throw new ProductoCargadoException("El producto ya esta cargado en sistema.");
        }
        else {productos_cargados.add(new Producto(nombre, stock, precio));}

    }

    public void agregarPedido (Pedido pedido) {
        pedidos.add(pedido);
    }

    public Producto buscarProducto (String nombre) throws ProductoNoEncontradoException {
        Producto producto_buscado = null;

        if (!productoCargado(nombre)) {
            throw new ProductoNoEncontradoException("El producto inidicado no fue encontrado en el sistema.");
        }
        //logica de busqueda
        for (Producto producto: productos_cargados) {
            if (nombre.equals(producto.getNombre())) {
                producto_buscado = producto;
            }
        }
        return  producto_buscado;
    }

    public Producto buscarProducto (int id) throws ProductoNoEncontradoException {
        Producto producto_buscado = null;

        if (!productoCargado(id)) {
            throw new ProductoNoEncontradoException("El producto inidicado no fue encontrado en el sistema.");
        }
        //logica de busqueda
        for (Producto producto: productos_cargados) {
            if (id == producto.getId()) {
                producto_buscado = producto;
            }
        }
        return  producto_buscado;
    }

    public void eliminarProducto (int id) throws ProductoNoEncontradoException {
        Producto producto = this.buscarProducto(id);
        this.productos_cargados.remove(producto);
    }

    public void actulizarProducto (String nombre, int nuevo_stock, double nuevo_precio) throws ProductoNoEncontradoException {
        Producto producto = this.buscarProducto(nombre);
        producto.setStock(nuevo_stock);
        producto.setPrecio(nuevo_precio);
    }

    public void actualizarProducto (int id, int nuevo_stock, double nuevo_precio) throws ProductoNoEncontradoException {
        Producto producto = this.buscarProducto(id);
        producto.setStock(nuevo_stock);
        producto.setPrecio(nuevo_precio);
    }

    public Object[][] tablaProductos (){
        final Object[][] tabla = new String[productos_cargados.size() +1][];
        tabla [0] = new String[]{"Id", "Nombre", "Stock", "Precio"};

        int i = 1;
        for (Producto producto: productos_cargados) {
            tabla[i] = producto.toArray();
            i ++;
        }
        return tabla;
    }

    public void listarProductos (){
        final Object[][] tabla = this.tablaProductos();

        //itero e imprimo la tabla.
        for (final Object[] fila: tabla) {
            System.out.format("|%-8s |%-16s |%-10s |%-10s |%n", fila);

        }
        //System.out.format("%-8s%-16s%-16s%d", "TOTAL", "","", 200);
    }



}
