package com.geeksforless.domain.entity;

public enum Country {
    UKRAINE("Ukraine"),
    USA("USA"),
    POLAND("Poland"),
    CANADA("Canada"),
    AUSTRALIA("Australia"),
    GERMANY("Germany"),
    FRANCE("France"),
    ITALY("Italy"),
    SPAIN("Spain"),
    UNITED_KINGDOM("United Kingdom");

    private String name;

    Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
