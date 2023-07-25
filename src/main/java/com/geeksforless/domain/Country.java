package com.geeksforless.domain;

public enum Country {
    UKRAINE("Ukraine"),
    USA("USA"),
    POLAND("Poland"),
    UNITED_KINGDOM("United Kingdom");

    private String name;

    Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
