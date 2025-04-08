package com.jsparser.model;

public class Match {
    private long id;
    private String team1;
    private String team2;
    private String startTimeUtc;

    public Match() {}

    public Match(long id, String team1, String team2, String startTimeUtc) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.startTimeUtc = startTimeUtc;
    }

    public long getId() {
        return id;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getStartTimeUtc() {
        return startTimeUtc;
    }
}
