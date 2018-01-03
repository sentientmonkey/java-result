package com.sentientmonkey.result;

import lombok.*;

import java.util.function.Function;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Result<T,E> {
    private final T ok;
    private final E error;

    public static <T,E> Result<T,E> ok(T ok) {
        return new Result<>(ok, null);
    }

    public static <T,E> Result<T,E> error(E error) {
        return new Result<>(null, error);
    }

    public boolean isOk() {
        return ok != null;
    }

    public boolean isError() {
        return error != null;
    }

    public T unwrap() throws ResultErrorException {
        if (isError()) {
            throw new ResultErrorException(this.getError(), "Tried to unwrap error");
        }

        return ok;
    }

    public <R> Result<R,E> map(Function<T,R> function) {
        if (isOk()) {
            return Result.ok(function.apply(ok));
        }

        return Result.error(this.getError());
    }

    public <R> Result<T,R> mapError(Function<E,R> function) {
        if (isError()) {
            return Result.error(function.apply(error));
        }

        return Result.ok(this.getOk());
    }
}
