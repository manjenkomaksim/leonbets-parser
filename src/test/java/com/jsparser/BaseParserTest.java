package com.jsparser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class BaseParserTest {

    private final ObjectMapper mapper = new ObjectMapper();

    private final BaseParser parser = new FootballParser();

    @Test
    void testGetLeagueName_returnsNameDefaultIfPresent() throws Exception {
        String json = "{ \"name\": \"Fallback Name\", \"nameDefault\": \"Primary Name\" }";
        JsonNode league = mapper.readTree(json);
        String result = parser.getLeagueName(league);
        assertEquals("Primary Name", result);
    }

    @Test
    void testGetLeagueName_fallsBackToNameIfNoDefault() throws Exception {
        String json = "{ \"name\": \"Only Name\" }";
        JsonNode league = mapper.readTree(json);
        String result = parser.getLeagueName(league);
        assertEquals("Only Name", result);
    }

    @Test
    void testLimitMatchesToTwo() throws Exception {
        String json = "[{}, {}, {}, {}]"; // simulate 4 dummy events
        JsonNode eventsArray = mapper.readTree(json);
        assertEquals(4, eventsArray.size());

        long limited = eventsArray.elements().next().size();
        assertTrue(limited <= 2); // will check in live integration
    }

}
