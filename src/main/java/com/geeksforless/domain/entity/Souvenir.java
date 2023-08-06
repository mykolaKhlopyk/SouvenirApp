package com.geeksforless.domain.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class Souvenir implements Serializable, Comparable<Souvenir> {
    private int id;

    private int nameId;
    private transient String name;

    private LocalDate createdAt;

    private double price;

    private int producerId;
    private transient Producer producer;

    @Builder
    public Souvenir(String name, LocalDate createdAt, double price, Producer producer) {
        this.name = name;
        this.createdAt = createdAt;
        this.price = price;
        this.producer = producer;
    }

    @Override
    public int compareTo(Souvenir souvenir) {
        return Integer.compare(id, souvenir.id);
    }

    @Override
    public String toString() {
         return String.format("{id = %d, name = %s, createdAt = %s, price = %f}", id, name, createdAt.toString(), price);
    }
}
