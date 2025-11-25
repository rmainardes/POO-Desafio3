package Model.exception;

public class ProdutoInexistenteException extends RuntimeException {

    public ProdutoInexistenteException(String message) {
        super(message);
    }

    public ProdutoInexistenteException(String message, Throwable cause) {
        super(message, cause);
    }
}
