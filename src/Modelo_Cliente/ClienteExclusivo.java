package Modelo_Cliente;

public class ClienteExclusivo extends Cliente{

    public ClienteExclusivo(String nombre, String correo) {
        super(nombre, correo);
    }

    @Override
    public String tipoDeCliente() {
        return "Exclusivo";
    }

    @Override
    public double getDescuento() {
        return 0.15;
    }
    
}
