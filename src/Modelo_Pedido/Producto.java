package Modelo_Pedido;

public class Producto {
    private String nombre;
    private double precio;
    private String ubicacion;

    public Producto(String nombre, double precio, String ubicacion) {
        this.nombre = nombre;
        this.precio = precio;
        this.ubicacion = ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\nPrecio: " + precio + "\nUbicacion=" + ubicacion;
    }
    
}
