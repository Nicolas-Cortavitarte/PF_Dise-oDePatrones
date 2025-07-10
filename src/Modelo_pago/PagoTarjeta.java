package Modelo_pago;

public class PagoTarjeta implements MetodoPago {

    @Override
    public void procesarPago(double monto) {
        System.out.println("Pago con tarjeta procesado por: S/ " + monto);
    }

}
