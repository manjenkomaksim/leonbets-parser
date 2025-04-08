package com.jsparser;

public class TennisParser extends BaseParser {
    @Override
    protected String getSportName() {
        return "Tennis";
    }

    @Override
    protected String getLogFileName() {
        return "tennis.log";
    }

    @Override
    protected String getEmoji() {
        return "\uD83C\uDFBE"; // 🎾
    }
}
