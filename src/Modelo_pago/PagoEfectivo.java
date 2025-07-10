package Modelo_pago;

public class PagoEfectivo implements MetodoPago{

    @Override
    public void procesarPago(double monto) {
        System.out.println("Pago en efectivo procesado por: S/ " + monto);
    }
    
}
