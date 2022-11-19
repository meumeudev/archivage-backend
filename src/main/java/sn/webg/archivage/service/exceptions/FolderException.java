package sn.webg.archivage.service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class FolderException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public FolderException(String message) {
        super(message);
    }
}
