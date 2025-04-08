package com.jsparser.model;

import java.util.List;

public class Market {
    private String name;
    private List<Outcome> outcomes;

    public Market() {}

    public Market(String name, List<Outcome> outcomes) {
        this.name = name;
        this.outcomes = outcomes;
    }

    public String getName() {
        return name;
    }

    public List<Outcome> getOutcomes() {
        return outcomes;
    }
}
