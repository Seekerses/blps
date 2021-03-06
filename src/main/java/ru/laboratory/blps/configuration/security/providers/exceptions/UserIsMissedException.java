package ru.laboratory.blps.configuration.security.providers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserIsMissedException extends AuthenticationException {
    public UserIsMissedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserIsMissedException(String msg) {
        super(msg);
    }
}
