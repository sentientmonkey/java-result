package com.sentientmonkey.result;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ResultTest {
    @Test
    public void resultSuccess() {
        Result<Integer,String> result = Result.ok(1);
        assertTrue(result.isOk());
        assertFalse(result.isError());
        assertEquals((Integer)1, result.getOk());
        assertEquals((Integer)1, result.unwrap());
    }

    @Test
    public void resultError() {
        Result<Integer,String> result = Result.error("Failed");
        assertTrue(result.isError());
        assertFalse(result.isOk());
        assertEquals("Failed", result.getError());
    }

    @Test
    public void resultCanMap() {
        Result<Integer,String> success = Result.ok(1);
        Result<Integer,String> failure = Result.error("Failed");
        assertEquals((Integer) 2, success.map(i -> i + 1).unwrap());
        assertEquals("Failed", failure.map(i -> i + 1).getError());
    }

    @Test
    public void resultCanMapToAnOtherType() {
        Result<Integer,String> success = Result.ok(1);
        Result<Integer,String> failure = Result.error("Failed");
        assertEquals("2", success.map(i -> new Integer(i + 1).toString()).unwrap());
        assertEquals("Failed", failure.map(i -> new Integer(i + 1).toString()).getError());
    }

    @Test
    public void resultCanMapError() {
        Result<Integer,String> success = Result.ok(1);
        Result<Integer,String> failure = Result.error("Failed");
        assertEquals((Integer) 1, success.mapError(s -> s + "!!").unwrap());
        assertEquals("Failed!!", failure.mapError(s -> s + "!!").getError());
    }

    @Test
    public void resultCanMapErrorToAnotherType() {
        Result<Integer,String> success = Result.ok(1);
        Result<Integer,String> failure = Result.error("Failed");
        assertEquals((Integer) 1, success.mapError(s -> s + "!!").unwrap());
        assertEquals((Integer) 42, failure.mapError(s -> new Integer(42)).getError());
    }
}