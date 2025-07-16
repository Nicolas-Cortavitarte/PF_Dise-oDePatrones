package Modelo_pago;

public class PagoYape implements MetodoPago {

    @Override
    public String procesarPago(double monto) {
        return "✅ Escanea el QR para pagar S/ " + String.format("%.2f", monto)
                + "\nEsperando confirmación...\nPago confirmado.";
    }
}

