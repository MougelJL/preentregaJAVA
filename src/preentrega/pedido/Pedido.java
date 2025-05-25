package preentrega.pedido;

import preentrega.producto.Producto;
import preentrega.producto.StockInsuficienteException;

import java.util.ArrayList;

public class Pedido {
    private static int ultimo_id = 0;
    private ArrayList<Producto> productos;
    private ArrayList<Integer> cantidad;
    private int id;

    public Pedido() {
        this.productos = new ArrayList<>();
        this.cantidad = new ArrayList<>();
        this.id = ultimo_id;
        ultimo_id ++;
    }

    public void append (Producto producto, int cantidad_pedido) throws StockInsuficienteException {
        if (cantidad_pedido > producto.getStock()) {
            throw new StockInsuficienteException("Stock insuficiente");
        }
        else{
            productos.add(producto);
            cantidad.add(cantidad_pedido);
        }
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public ArrayList<Integer> getCantidad() {
        return cantidad;
    }

    public void setCantidad(ArrayList<Integer> cantidad) {
        this.cantidad = cantidad;
    }

    public void restarStock () {
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            int cantidad_a_restar = cantidad.get(i);
            producto.setStock(producto.getStock() - cantidad_a_restar);
        }
    }

    public ArrayList<Double> costoTotalPorItem () {
        ArrayList<Double> costo_total_por_item = new ArrayList<>();
        for (int i = 0; i < productos.size(); i++){

            Producto producto = productos.get(i);
            int cantidad_pedido = cantidad.get(i);
            double precio = producto.getPrecio();

            costo_total_por_item.add(precio * cantidad_pedido);

        }
        return costo_total_por_item;
    }

    public double costoTotalPedido () {
        double costo_total_pedido = 0;
        for (double costo_por_item: costoTotalPorItem()) {
            costo_total_pedido = costo_total_pedido + costo_por_item;
        }
        return  costo_total_pedido;
    }

    public Object [][] tablaPedido () {
        final  Object[][] tabla = new String[productos.size() + 3][];
        tabla [0] = new String [] {"ID del Pedido: ",String.valueOf(id),"","" };
        tabla [1] = new String[] {"ID Producto", "Nombre", "Cantidad Pedida", "Costo total"};

        ArrayList<Double> costos_totales = costoTotalPorItem();
        double costo_total_pedido = costoTotalPedido();
        int i = 2;
        while (i < productos.size()+2) {

            Producto producto = productos.get(i-2);
            int id_producto = producto.getId();
            String nombre = producto.getNombre();
            int cantidad_pedida = cantidad.get(i-2);
            double costo_total  = costos_totales.get(i-2);

            tabla [i] = new String [] { String.valueOf(id_producto),
                                        nombre,
                                        String.valueOf(cantidad_pedida),
                                        String.valueOf(costo_total)};
            i = i +1;

        }

        //Pie de pagina
        tabla [productos.size() +2] = new String[] {"TOTAL", "", "", String.valueOf(costo_total_pedido)};
        return tabla;
    }
    public void print () {
        final Object [][] tabla = tablaPedido();

        for (final Object[] fila: tabla) {
            System.out.printf("%-20s%-20s%-20s%-20s%n", fila);
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Pedido{");
        sb.append("productos=").append(productos);
        sb.append(", cantidad=").append(cantidad);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}

