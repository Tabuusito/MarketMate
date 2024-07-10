package esei.uvigo.demo.exceptions;

public class NotFoundException extends Exception {

    public NotFoundException() {
        super("Elemento no encontrado");
    }

    public NotFoundException(String mensaje) {
        super(mensaje);
    }
}
