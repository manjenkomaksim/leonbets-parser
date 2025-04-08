package com.jsparser;

import com.fasterxml.jackson.databind.JsonNode;
import com.jsparser.service.HttpService;
import com.jsparser.service.LoggerService;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public abstract class BaseParser {
    private final HttpService httpService = new HttpService();

    protected abstract String getSportName();
    protected abstract String getLogFileName();
    protected abstract String getEmoji();

    public void parse(ExecutorService executor) {
        LoggerService logger = new LoggerService(getLogFileName());
        try {
            JsonNode sportsArray = httpService.getSports();

            for (JsonNode sport : sportsArray) {
                String sportName = sport.get("name").asText();
                if (!getSportName().equals(sportName)) continue;

                processSport(sportName, sport, logger, executor);
                break;
            }
        } catch (Exception e) {
            logger.log("❌ " + getSportName() + "Parser error: " + e.getMessage());
        } finally {
            logger.close();
        }
    }

    private void processSport(String sportName, JsonNode sport, LoggerService logger, ExecutorService executor) {
        JsonNode regions = sport.get("regions");
        if (regions == null || !regions.isArray()) return;

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (JsonNode region : regions) {
            String regionName = region.has("nameDefault") ? region.get("nameDefault").asText() : region.get("name").asText();
            JsonNode leagues = region.get("leagues");
            if (leagues == null || !leagues.isArray()) continue;

            for (JsonNode league : leagues) {
                boolean isTop = league.has("top") && league.get("top").asBoolean();
                if (!isTop) continue;

                String leagueName = getLeagueName(league);
                String fullName = regionName + " - " + leagueName;
                long leagueId = league.get("id").asLong();

                futures.add(CompletableFuture.runAsync(() ->
                        processLeague(sportName, fullName, leagueId, logger), executor));
            }
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    private void processLeague(String sportName, String leagueFullName, long leagueId, LoggerService logger) {
        StringBuilder sb = new StringBuilder();
        try {
            JsonNode eventsData = httpService.getEventsForLeague(leagueId);
            JsonNode events = eventsData.get("events");
            if (events == null || !events.isArray() || events.isEmpty()) {
                return;
            }

            List<JsonNode> matches = new ArrayList<>();
            events.elements().forEachRemaining(matches::add);
            matches = matches.stream().limit(2).collect(Collectors.toList());

            sb.append("\n");
            sb.append(getEmoji()).append(" ").append(getSportName()).append(" → ").append(leagueFullName).append("\n");

            for (JsonNode match : matches) {
                long eventId = match.get("id").asLong();
                String team1 = match.get("competitors").get(0).get("name").asText();
                String team2 = match.get("competitors").get(1).get("name").asText();
                long kickoff = match.get("kickoff").asLong();

                String start = Instant.ofEpochMilli(kickoff)
                        .atZone(ZoneOffset.UTC)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " UTC";

                sb.append("\uD83C\uDFDF ").append(team1).append(" - ").append(team2)
                        .append(", ").append(start).append(", ").append(eventId).append("\n");

                JsonNode detail = httpService.getEventDetails(eventId);
                JsonNode markets = detail.get("markets");
                if (markets == null || !markets.isArray()) continue;

                for (JsonNode market : markets) {
                    String marketName = market.get("name").asText();
                    sb.append("\u25B6 ").append(marketName).append("\n");

                    JsonNode runners = market.get("runners");
                    if (runners == null || !runners.isArray()) continue;

                    for (JsonNode runner : runners) {
                        String outcomeName = runner.get("name").asText();
                        double price = runner.get("price").asDouble();
                        long outcomeId = runner.get("id").asLong();

                        sb.append("   • ").append(outcomeName).append(", ")
                                .append(price).append(", ").append(outcomeId).append("\n");
                    }
                    sb.append("\n");
                }
            }

            logger.log(sb.toString());

        } catch (Exception e) {
            sb.append("❌ Failed to parse league: ").append(leagueFullName).append(" → ").append(e.getMessage()).append("\n");
            logger.log(sb.toString());
        }
    }

    protected String getLeagueName(JsonNode league) {
        return league.has("nameDefault") ? league.get("nameDefault").asText() : league.get("name").asText();
    }
}
