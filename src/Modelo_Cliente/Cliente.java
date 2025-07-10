package Modelo_Cliente;

public abstract class Cliente {
    private String nombre;
    private String correo;

    public Cliente(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }
    
    public abstract double getDescuento();
    
    public abstract String tipoDeCliente();
}
