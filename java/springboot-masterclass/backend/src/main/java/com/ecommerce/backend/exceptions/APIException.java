package com.ecommerce.backend.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class APIException extends RuntimeException {
    String message;

    public APIException(String m) {
        super(m);
        this.message = m;
    }
}
