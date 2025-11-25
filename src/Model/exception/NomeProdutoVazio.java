package Model.exception;

public class NomeProdutoVazio extends RuntimeException {
    public NomeProdutoVazio(String message) {
        super(message);
    }
}
