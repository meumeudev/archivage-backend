package sn.webg.archivage.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class MetaDataException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public MetaDataException(String message) {
        super(message);
    }
}
