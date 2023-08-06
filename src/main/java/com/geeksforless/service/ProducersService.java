package com.geeksforless.service;

import com.geeksforless.domain.entity.Producer;
import com.geeksforless.domain.entity.Souvenir;

import java.util.List;
import java.util.Map;

public interface ProducersService {
    Producer getById(int id);
    Producer save(Producer producer);
    Producer update(Producer producer);
    List<Producer> getAll();
    List<Souvenir> getSouvenirsOfProducer(Producer producer);
    List<Producer> getAllWithPricesOfSouvenirsLessThan(double maxPrice);
    Map<Producer, List<Souvenir>> getProducersWithTheirSouvenirs();
    void deleteProducerAndHisSouvenirs(Producer producer);

}
