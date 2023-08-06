package com.geeksforless;

import com.geeksforless.dao.PreservingDAO;
import com.geeksforless.dao.impl.PreservingFiIeDAO;
import com.geeksforless.dao.impl.SouvenirsFileDAO;
import com.geeksforless.domain.entity.Producer;
import com.geeksforless.domain.entity.Souvenir;
import com.geeksforless.service.ProducersService;
import com.geeksforless.service.SouvenirsService;
import com.geeksforless.service.impl.ProducerServiceImpl;
import com.geeksforless.service.impl.SouvenirsServiceImpl;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Producer producer1 = Producer.builder().name("Bob").country("Ukraine").souvenirs(List.of(
                Souvenir.builder().name("fridge magnet").createdAt(LocalDate.now()).price(200).build(),
                Souvenir.builder().name("bell").createdAt(LocalDate.now().minusDays(4)).price(300).build())).build();
        Producer producer2 = Producer.builder().name("Alice").country("USA").souvenirs(List.of(
                Souvenir.builder().name("keychain").createdAt(LocalDate.now().minusDays(2)).price(150).build(),
                Souvenir.builder().name("mug").createdAt(LocalDate.now().minusDays(6)).price(250).build(),
                Souvenir.builder().name("t-shirt").createdAt(LocalDate.now().minusDays(8)).price(350).build())).build();

        ProducersService producersService = ProducerServiceImpl.getInstance();
//        producersService.save(producer1);
//        producersService.save(producer2);
        producersService.getAll().forEach(System.out::println);
        SouvenirsService souvenirsService = SouvenirsServiceImpl.getInstance();
//        Souvenir souvenir = souvenirsService.getById(4);
//        souvenir.setCreatedAt(LocalDate.now().minusYears(3));
//        souvenirsService.update(souvenir);
        souvenirsService.getAll().forEach(System.out::println);

        souvenirsService.getMapYearSouvenirs().forEach((k, v) -> System.out.println(k + " " + v));
        //saving
        PreservingDAO preservingDAO = PreservingFiIeDAO.getInstance();
        preservingDAO.saveInFile();
    }


}

