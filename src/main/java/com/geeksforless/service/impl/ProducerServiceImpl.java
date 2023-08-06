package com.geeksforless.service.impl;

import com.geeksforless.dao.ProducersDAO;
import com.geeksforless.dao.SouvenirsDAO;
import com.geeksforless.dao.impl.ProducersFileDAO;
import com.geeksforless.dao.impl.SouvenirsFileDAO;
import com.geeksforless.domain.entity.Producer;
import com.geeksforless.domain.entity.Souvenir;
import com.geeksforless.domain.exception.ResourcesNotFoundException;
import com.geeksforless.service.ProducersService;
import com.geeksforless.service.SouvenirsService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProducerServiceImpl implements ProducersService {

    private final ProducersDAO producersDAO;
    private  SouvenirsService souvenirsService;
    private static ProducerServiceImpl instance;


    private ProducerServiceImpl() {
        producersDAO = ProducersFileDAO.getInstance();
    }

    public static ProducerServiceImpl getInstance() {
        if (instance == null)
            instance = new ProducerServiceImpl();
        return instance;
    }

    public void setSouvenirsService(SouvenirsService souvenirsService) {
        this.souvenirsService = souvenirsService;
    }

    @Override
    public Producer getById(int id) {
        return getAll().stream().filter(producer -> producer.getId() == id).findAny().orElseThrow(() -> new ResourcesNotFoundException("Incorrect id"));
    }

    @Override
    public Producer save(Producer producer) {
        if (producer.getId() == 0) {
            getAll().add(producer);
            producer.setId(getAll().size());
            saveSouvenirsOfProducer(producer);
        }
        return producer;
    }

    private void saveSouvenirsOfProducer(Producer producer) {
        if (producer.getSouvenirs() != null) {
            setSouvenirsService(SouvenirsServiceImpl.getInstance());
            producer.getSouvenirs().forEach(souvenir -> {
                souvenir.setProducer(producer);
                souvenirsService.save(souvenir);
            });
        }

    }

    @Override
    public Producer update(Producer updatedProducer) throws ResourcesNotFoundException {
        Optional<Producer> producerForUpdating = getAll().stream().filter(s -> s.getId() == updatedProducer.getId()).findAny();
        if (producerForUpdating.isPresent()) {
            getAll().remove(producerForUpdating.get());
            getAll().add(updatedProducer);
        } else {
            throw new ResourcesNotFoundException("Souvenir doesn't exist");
        }
        return updatedProducer;
    }

    @Override
    public List<Producer> getAll() {
        return producersDAO.getAll();
    }

    @Override
    public List<Souvenir> getSouvenirsOfProducer(Producer producer) {
        return producer.getSouvenirs();
    }

    @Override
    public List<Producer> getAllWithPricesOfSouvenirsLessThan(double maxPrice) {
        return getAll().stream().filter(producer -> !producer.getSouvenirs().stream().map(Souvenir::getPrice).anyMatch(price -> price >= maxPrice)).collect(Collectors.toList());
    }

    @Override
    public Map<Producer, List<Souvenir>> getProducersWithTheirSouvenirs() {
        return getAll().stream().collect(Collectors.toMap(producer -> producer, Producer::getSouvenirs));
    }

    @Override
    public void deleteProducerAndHisSouvenirs(Producer producer) {
        getAll().remove(producer);
        souvenirsService.getAll().removeIf(souvenir -> souvenir.getProducer().equals(producer));
    }
}
