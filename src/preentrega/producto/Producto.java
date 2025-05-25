package preentrega.producto;

import java.util.Objects;

public class Producto {
    //Para ir agregando los IDs
    private static int id_totales = 0;

    private int id;
    private String nombre;
    private int stock;
    private double precio;

    public Producto(String nombre, int stock, double precio) {
        this.nombre = nombre;
        this.id = id_totales;
        this.stock = stock;
        this.precio = precio;

        // actuliazar la variable de clase.
        Producto.id_totales ++;
    }

    public Producto(String nombre, int stock, double precio, int id) {
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String[] toArray (){
        return new String[]{String.valueOf(id), nombre, String.valueOf(stock), String.valueOf(precio)};
    }

    @Override
    public String toString() {

        String to_string = "Id: " + id + "\n" +
                            "Nombre: " + nombre + "\n"+
                            "Precio: " + precio + "\n"+
                            "Stock: "  + stock;
        return to_string;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Producto producto)) return false;
        return id == producto.id || Objects.equals(nombre, producto.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
