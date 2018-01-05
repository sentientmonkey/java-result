package com.sentientmonkey.result;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ResultTest {
    Result<Integer,String> success = Result.ok(1);
    Result<Integer,String> failure = Result.error("Failed");

    @Test
    public void resultSuccess() throws Exception {
        assertThat(success.isOk()).isTrue();
        assertThat(success.isError()).isFalse();
        assertThat(success.getOk()).isEqualTo(1);
        assertThat(success.unwrap()).isEqualTo(1);
    }

    @Test
    public void resultThrowsUnwrappingError() throws Exception {
        Throwable exception = catchThrowable(() -> failure.unwrap());

        assertThat(exception).isInstanceOf(ResultErrorException.class)
                .hasMessage("Tried to unwrap error")
                .hasFieldOrPropertyWithValue("error", "Failed");
    }

    @Test
    public void resultExceptionProperty() throws Exception {
        boolean thrown = false;
        try {
            failure.unwrap();
        } catch (ResultErrorException e) {
            String error = e.getError();
            assertThat(error).isEqualTo("Failed");
            thrown = true;
        }
        assertThat(thrown).isTrue();
    }

    @Test
    public void resultError() {
        assertThat(failure.isError()).isTrue();
        assertThat(failure.isOk()).isFalse();
        assertThat(failure.getError()).isEqualTo("Failed");
    }

    @Test
    public void resultCanMap() throws Exception {
        assertThat(success.map(i -> i + 1).unwrap()).isEqualTo(2);
        assertThat(failure.map(i -> i + 1).getError()).isEqualTo("Failed");
    }

    @Test
    public void resultCanMapToAnOtherType() throws Exception {
        assertThat(success.map(i -> Integer.toString(i + 1)).unwrap()).isEqualTo("2");
        assertThat(failure.map(i -> Integer.toString(i + 1)).getError()).isEqualTo("Failed");
    }

    @Test
    public void resultCanFlatMap() throws Exception {
        assertThat(success.flatMap(i -> Result.ok(i + 1)).unwrap()).isEqualTo(2);
        assertThat(failure.flatMap(i -> Result.ok(i + 1)).getError()).isEqualTo("Failed");
    }

    @Test
    public void resultCanMapError() throws Exception {
        assertThat(success.mapError(s -> s + "!!").unwrap()).isEqualTo(1);
        assertThat(failure.mapError(s -> s + "!!").getError()).isEqualTo("Failed!!");
    }

    @Test
    public void resultCanMapErrorToAnotherType() throws Exception {
        assertThat(success.mapError(s -> s + "!!").unwrap()).isEqualTo(1);
        assertThat(failure.mapError(s -> s.equals("Failed") ? 42 : 0).getError()).isEqualTo(42);
    }
}