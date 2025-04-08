package com.jsparser;

public class BasketballParser extends BaseParser {
    @Override
    protected String getSportName() {
        return "Basketball";
    }

    @Override
    protected String getLogFileName() {
        return "basketball.log";
    }

    @Override
    protected String getEmoji() {
        return "\uD83C\uDFC0"; // 🏀
    }
}
