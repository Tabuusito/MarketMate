package esei.uvigo.demo.exceptions;

public class AlreadyExistsException extends Exception {

    public AlreadyExistsException() {
        super("Elemento ya existe");
    }

    public AlreadyExistsException(String mensaje) {
        super(mensaje);
    }
}
