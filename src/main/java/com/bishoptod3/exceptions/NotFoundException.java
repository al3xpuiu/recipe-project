package com.bishoptod3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Loky on 22/10/2018.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super( message );
    }

    public NotFoundException(String message, Throwable cause) {
        super( message, cause );
    }
}
