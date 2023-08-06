package com.geeksforless.service.impl;


import com.geeksforless.domain.entity.Producer;
import com.geeksforless.domain.entity.Souvenir;
import com.geeksforless.service.ProducersService;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceImplTest {

    private static ProducersService producersService;
    private static List<Producer> producers = List.of(
            Producer.builder().name("Bob").country("Ukraine").souvenirs(List.of(
                    Souvenir.builder().name("fridge magnet").createdAt(LocalDate.now()).price(200).build(),
                    Souvenir.builder().name("bell").createdAt(LocalDate.now().minusDays(4)).price(300).build())).build(),
            Producer.builder().name("Alice").country("USA").souvenirs(List.of(
                    Souvenir.builder().name("keychain").createdAt(LocalDate.now().minusDays(2)).price(150).build(),
                    Souvenir.builder().name("mug").createdAt(LocalDate.now().minusDays(6)).price(250).build(),
                    Souvenir.builder().name("t-shirt").createdAt(LocalDate.now().minusDays(8)).price(350).build())).build(),
            Producer.builder().name("Charlie").country("Canada").souvenirs(List.of(
                    Souvenir.builder().name("postcard").createdAt(LocalDate.now().minusDays(3)).price(100).build())).build(),
            Producer.builder().name("David").country("Australia").souvenirs(List.of(
                    Souvenir.builder().name("keychain").createdAt(LocalDate.now().minusDays(1)).price(50).build(),
                    Souvenir.builder().name("mug").createdAt(LocalDate.now().minusDays(3)).price(199).build(),
                    Souvenir.builder().name("t-shirt").createdAt(LocalDate.now().minusDays(5)).price(150).build(),
                    Souvenir.builder().name("fridge magnet").createdAt(LocalDate.now().minusDays(7)).price(100).build())).build(),
            Producer.builder().name("Eva").country("Germany").souvenirs(List.of()).build(),
            Producer.builder().name("Frank").country("France").souvenirs(List.of(
                    Souvenir.builder().name("keychain").createdAt(LocalDate.now().minusDays(2)).price(180).build())).build(),
            Producer.builder().name("Grace").country("Italy").souvenirs(List.of(
                    Souvenir.builder().name("mug").createdAt(LocalDate.now().minusDays(3)).price(250).build(),
                    Souvenir.builder().name("t-shirt").createdAt(LocalDate.now().minusDays(6)).price(380).build(),
                    Souvenir.builder().name("fridge magnet").createdAt(LocalDate.now().minusDays(8)).price(470).build())).build(),
            Producer.builder().name("Henry").country("Spain").souvenirs(List.of(
                    Souvenir.builder().name("keychain").createdAt(LocalDate.now().minusDays(2)).price(160).build(),
                    Souvenir.builder().name("mug").createdAt(LocalDate.now().minusDays(4)).price(280).build())).build()
    );

    @BeforeAll
    static void setUp() {
        ServicesUtils.mainSetUp();
        producersService = ProducerServiceImpl.getInstance();
    }

    @Test
    @Order(0)
    void testSave() {
        producersService.save(producers.get(0));
        producersService.save(producers.get(1));
        producersService.save(producers.get(2));
        Assertions.assertEquals(3, producersService.getAll().size());
    }

    @Test
    @Order(5)
    void testGetAll() {
        Assertions.assertEquals(3, producersService.getAll().size());
    }

    @Test
    @Order(10)
    void testUpdate() {
        Producer producer = producersService.save(producers.get(3));

        String newName = "Barbara";
        producer.setName(newName);
        producersService.update(producer);

        Producer updatedProducer = producersService.getById(producer.getId());
        Assertions.assertEquals(newName, updatedProducer.getName());
    }


    @Test
    @Order(15)
    void testGetSouvenirsOfProducer() {
        Assertions.assertEquals(2, producersService.getSouvenirsOfProducer(producers.get(0)).size());
    }

    @Test
    @Order(20)
    void testGetAllWithPricesOfSouvenirsLessThan() {
        producersService.save(producers.get(4));
        Assertions.assertEquals(3, producersService.getAllWithPricesOfSouvenirsLessThan(200).size());
    }

    @Test
    @Order(25)
    void testGetProducersWithTheirSouvenirs() {
        Map<Producer, List<Souvenir>> map = producersService.getProducersWithTheirSouvenirs();
        Assertions.assertEquals(2, map.get(producers.get(0)).size());
        Assertions.assertEquals(0, map.get(producers.get(4)).size());
    }

    @Test
    @Order(30)
    void testDeleteProducerAndHisSouvenirs() {
        producersService.deleteProducerAndHisSouvenirs(producers.get(0));
        Assertions.assertFalse(producersService.getAll().stream().anyMatch(producer -> producer.getId()==0));
    }
}
