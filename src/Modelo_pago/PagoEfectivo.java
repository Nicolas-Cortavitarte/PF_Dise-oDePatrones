package Modelo_pago;

public class PagoEfectivo implements MetodoPago {

    @Override
    public String procesarPago(double monto) {
        return "✅ Pago en efectivo recibido. Total: S/ " + String.format("%.2f", monto)
                + "\nPor favor entregar el dinero al repartidor.";
    }

}
