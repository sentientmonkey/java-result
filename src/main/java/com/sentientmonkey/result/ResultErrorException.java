package com.sentientmonkey.result;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = false)
public class ResultErrorException extends Exception {
    private final Object error;

    public ResultErrorException(Object error, String message) {
        super(message);
        this.error = error;
    }

    public <E> E getError() {
        return (E)error;
    }
}
