package com.geeksforless.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Producer implements Serializable {
    private long id;
    private String name;
    private Country country;
    private transient List<Souvenir> souvenirs;
}
