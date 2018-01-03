package com.sentientmonkey.result;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class ResultErrorException extends Exception {
    private final Object error;

    public ResultErrorException(Object error, String message) {
        super(message);
        this.error = error;
    }
}
