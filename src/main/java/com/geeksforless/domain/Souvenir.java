package com.geeksforless.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class Souvenir implements Serializable {
    private long id;
    private String name;
    private LocalDate createdAt;
    private double price;
    private long producerId;
    private transient Producer producer;
}
