package com.jsparser;

public class HockeyParser extends BaseParser {
    @Override
    protected String getSportName() {
        return "Ice Hockey";
    }

    @Override
    protected String getLogFileName() {
        return "hockey.log";
    }

    @Override
    protected String getEmoji() {
        return "\uD83C\uDFD2"; // 🏒
    }
}
