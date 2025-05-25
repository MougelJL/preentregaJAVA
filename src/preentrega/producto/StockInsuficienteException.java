package preentrega.producto;

public class StockInsuficienteException extends Exception{
    public StockInsuficienteException(String message) {
        super(message);
    }
}
