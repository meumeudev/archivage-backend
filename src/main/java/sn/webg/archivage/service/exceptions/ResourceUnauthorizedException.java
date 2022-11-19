package sn.webg.archivage.service.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;

import java.nio.charset.Charset;

public class ResourceUnauthorizedException extends HttpStatusCodeException {

    private static final long serialVersionUID = 1L;

    public ResourceUnauthorizedException(HttpStatus statusCode) {
        super(statusCode);
    }

    public ResourceUnauthorizedException(HttpStatus statusCode, String statusText) {
        super(statusCode, statusText);
    }

    public ResourceUnauthorizedException(HttpStatus statusCode, String statusText,
        @Nullable byte[] responseBody, @Nullable Charset responseCharset) {
        super(statusCode, statusText, responseBody, responseCharset);
    }

    public ResourceUnauthorizedException(HttpStatus statusCode, String statusText,
        @Nullable HttpHeaders responseHeaders, @Nullable byte[] responseBody,
        @Nullable Charset responseCharset) {
        super(statusCode, statusText, responseHeaders, responseBody, responseCharset);
    }

}
