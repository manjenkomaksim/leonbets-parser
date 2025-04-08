package com.jsparser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MainParserTest {

    @Test
    public void testMainExecution() {
        assertDoesNotThrow(() -> MainParser.main(new String[]{}));
    }
}
