package sn.webg.archivage.service.exceptions;

public class MethodArgumentNotValidException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MethodArgumentNotValidException(String message) {
        super(message);
    }

}
