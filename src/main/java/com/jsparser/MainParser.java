package com.jsparser;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainParser {
    public void runParser() {
        List<BaseParser> parsers = List.of(
                new FootballParser(),
                new TennisParser(),
                new HockeyParser(),
                new BasketballParser()
        );

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (BaseParser parser : parsers) {
            parser.parse(executor);
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // wait
        }
    }

    public static void main(String[] args) {
        new MainParser().runParser();
    }
}
