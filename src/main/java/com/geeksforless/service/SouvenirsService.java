package com.geeksforless.service;

import com.geeksforless.domain.entity.Country;
import com.geeksforless.domain.entity.Producer;
import com.geeksforless.domain.entity.Souvenir;

import java.util.List;
import java.util.Map;

public interface SouvenirsService {
    Souvenir getById(int id);
    Souvenir save(Souvenir souvenir);
    Souvenir update(Souvenir updatedSouvenir);
    List<Souvenir> getAll();
    List<Souvenir> getByCountry(Country country);
    List<Producer> getProducersOfSouvenirNameCreatedAt(String souvenirName, int year);
    Map<Integer, List<Souvenir>> getMapYearSouvenirs();


}
