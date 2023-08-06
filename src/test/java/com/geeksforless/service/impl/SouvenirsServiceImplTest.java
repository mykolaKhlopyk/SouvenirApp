package com.geeksforless.service.impl;

import com.geeksforless.dao.impl.FileDAOUtils;
import com.geeksforless.dao.impl.ProducersFileDAO;
import com.geeksforless.dao.impl.SouvenirsFileDAO;
import com.geeksforless.domain.entity.Country;
import com.geeksforless.domain.entity.Producer;
import com.geeksforless.domain.entity.Souvenir;
import com.geeksforless.service.SouvenirsService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SouvenirsServiceImplTest {

    private static SouvenirsService souvenirsService;

    private static List<Producer> producers = List.of(
            Producer.builder().name("Bob").country("Ukraine").build(),
            Producer.builder().name("Alice").country("USA").build(),
            Producer.builder().name("Charlie").country("Canada").build(),
            Producer.builder().name("David").country("Australia").build());

    private static List<Souvenir> souvenirs = List.of(
            Souvenir.builder().name("fridge magnet").createdAt(LocalDate.now()).price(200).producer(producers.get(0)).build(),
            Souvenir.builder().name("t-shirt").createdAt(LocalDate.now()).price(500).producer(producers.get(1)).build(),
            Souvenir.builder().name("ball").createdAt(LocalDate.now().minusYears(3)).price(150).producer(producers.get(1)).build(),
            Souvenir.builder().name("magnet").createdAt(LocalDate.now()).price(100).producer(producers.get(2)).build(),
            Souvenir.builder().name("keychain").createdAt(LocalDate.now().minusYears(5)).price(400).producer(producers.get(3)).build());

    @BeforeAll
    static void setUp() {
        ServicesUtils.mainSetUp();
        souvenirsService = SouvenirsServiceImpl.getInstance();
    }

    @SneakyThrows
    private static void clearFile(String fileName) {
        File file = new File(FileDAOUtils.createFilePath(fileName));
        if (file.exists()) {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.close();
        }
    }

    @Test
    @Order(0)
    void testSave() {
        souvenirs.forEach(souvenirsService::save);
        Assertions.assertEquals(5, souvenirsService.getAll().size());
    }

    @Test
    @Order(5)
    void testGetAll() {
        Assertions.assertEquals(5, souvenirsService.getAll().size());
    }

    @Test
    @Order(5)
    void testUpdate() {
        Souvenir souvenir = souvenirsService.save(souvenirs.get(3));
        String newName = "golden ball";
        souvenir.setName(newName);
        souvenirsService.update(souvenir);

        Souvenir updatedSouvenir = souvenirsService.getById(souvenir.getId());
        Assertions.assertEquals(newName, updatedSouvenir.getName());
    }


    @Test
    @Order(10)
    void testGetByCountry() {
        Assertions.assertEquals(2, souvenirsService.getByCountry(Country.USA).size());
    }

    @Test
    @Order(15)
    void testGetProducersOfSouvenirNameCreatedAt() {
        Assertions.assertEquals(producers.get(3) ,souvenirsService.getProducersOfSouvenirNameCreatedAt("keychain", LocalDate.now().minusYears(5).getYear()).get(0));
    }

    @Test
    @Order(20)
    void testGetMapYearSouvenirs() {
        Map<Integer, List<Souvenir>> map = souvenirsService.getMapYearSouvenirs();
        Assertions.assertEquals(3,map.get(LocalDate.now().getYear()).size());
        Assertions.assertEquals(1,map.get(LocalDate.now().minusYears(5).getYear()).size());
    }
}