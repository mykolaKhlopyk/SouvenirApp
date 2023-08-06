package com.geeksforless.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Producer implements Serializable, Comparable<Producer> {
    private int id;
    private String name;
    private Country country;
    private transient List<Souvenir> souvenirs;
    @Builder
    public Producer(String name, String country, List<Souvenir> souvenirs) {
        this.name = name;
        this.country = Country.valueOf(country.toUpperCase());
        this.souvenirs = souvenirs==null ? new ArrayList<>() : new ArrayList<>(souvenirs);
        for (Souvenir souvenir: this.souvenirs) {
            souvenir.setProducer(this);
        }
    }

    @Override
    public int compareTo(Producer producer) {
        return Integer.compare(id, producer.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return id == producer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("{id = %d, name = %s, country = %s}", id, name, country);
    }
}
