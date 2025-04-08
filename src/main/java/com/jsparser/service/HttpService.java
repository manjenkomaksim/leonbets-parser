package com.jsparser.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpService {

    private static final String BASE_URL = "https://leonbets.com";
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public HttpService() {
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    private JsonNode getJson(String endpoint) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("User-Agent", "Mozilla/5.0")
                .header("Accept", "application/json, text/plain, */*")
                .header("Referer", "https://leonbets.com/")
                .header("Accept-Language", "en-US,en;q=0.9")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.err.println("❌ Failed request: " + endpoint);
            System.err.println("Response body:\n" + response.body());
            throw new RuntimeException("Failed to fetch " + endpoint + ", status: " + response.statusCode());
        }

        return objectMapper.readTree(response.body());
    }

    // Sports array (with regions and leagues)
    public JsonNode getSports() throws IOException, InterruptedException {
        return getJson("/api-2/betline/sports?ctag=en-US&flags=urlv2");
    }

    // Event list and vtag
    public JsonNode getEventsForLeague(long leagueId) throws IOException, InterruptedException {
        return getJson("/api-2/betline/events/all?ctag=en-US&league_id=" + leagueId +
                "&hideClosed=true&flags=reg,urlv2,mm2,rrc,nodup");
    }

    // All markets
    public JsonNode getEventDetails(long eventId) throws IOException, InterruptedException {
        return getJson("/api-2/betline/event/all?ctag=en-US&eventId=" + eventId +
                "&flags=reg,urlv2,mm2,rrc,nodup,smgv2,outv2");
    }
}
