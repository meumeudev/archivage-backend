package sn.webg.archivage.service.exceptions;

import javax.naming.AuthenticationException;

public class PasswordException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public PasswordException() {
        super();
    }

    public PasswordException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PasswordException(final String message) {
        super(message);
    }

    public PasswordException(final Throwable cause) {
        super(cause);
    }

}