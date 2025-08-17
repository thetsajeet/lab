package com.ecommerce.backend.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    public String fieldName;
    public Long fieldId;
    public String resourceName;
    public String field;

    public ResourceNotFoundException(String rn, String f, String fn) {
        super(String.format("%s not found with %s: %s", rn, f, fn));
        resourceName = rn;
        field = f;
        fieldName = fn;
    }

    public ResourceNotFoundException(String rn, String f, Long fId) {
        super(String.format("%s not found with %s: %d", rn, f, fId));
        resourceName = rn;
        field = f;
        fieldId = fId;
    }
}
