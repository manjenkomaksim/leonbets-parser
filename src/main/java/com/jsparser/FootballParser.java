package com.jsparser;

public class FootballParser extends BaseParser {
    @Override
    protected String getSportName() {
        return "Football";
    }

    @Override
    protected String getLogFileName() {
        return "football.log";
    }

    @Override
    protected String getEmoji() {
        return "\u26BD"; // ⚽
    }
}
