package Modelo_Cliente;

public class ClienteNormal extends Cliente{

    public ClienteNormal(String nombre, String correo) {
        super(nombre, correo);
    }

    @Override
    public String tipoDeCliente() {
        return "Normal";
    }

    @Override
    public double getDescuento() {
        return 0.0;
    }
    
}
