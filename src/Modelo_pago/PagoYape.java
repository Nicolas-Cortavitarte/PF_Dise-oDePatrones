package Modelo_pago;

public class PagoYape implements MetodoPago{

    @Override
    public void procesarPago(double monto) {
        System.out.println("Pago por yape procesado por: S/ " + monto);
    }
    
}
