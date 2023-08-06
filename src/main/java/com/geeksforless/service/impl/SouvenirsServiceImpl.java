package com.geeksforless.service.impl;

import com.geeksforless.dao.SouvenirsDAO;
import com.geeksforless.dao.impl.SouvenirsFileDAO;
import com.geeksforless.domain.entity.Country;
import com.geeksforless.domain.entity.Producer;
import com.geeksforless.domain.entity.Souvenir;
import com.geeksforless.domain.exception.ResourcesNotFoundException;
import com.geeksforless.service.ProducersService;
import com.geeksforless.service.SouvenirsService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SouvenirsServiceImpl implements SouvenirsService {

    private final SouvenirsDAO souvenirsDAO;

    private ProducersService producersService;

    private static SouvenirsServiceImpl instance;

    private SouvenirsServiceImpl() {
        souvenirsDAO = SouvenirsFileDAO.getInstance();
    }

    public static SouvenirsServiceImpl getInstance() {
        if (instance == null)
            instance = new SouvenirsServiceImpl();
        return instance;
    }

    public void setProducersService(ProducersService producersService) {
        this.producersService = producersService;
    }

    @Override
    public Souvenir getById(int id) {
        return getAll().stream().filter(souvenir -> souvenir.getId() == id).findAny().orElseThrow(()-> new ResourcesNotFoundException("souvenir with id "+id+" doesn't exist"));
    }

    @Override
    public Souvenir save(Souvenir souvenir) {
        if (souvenir.getId() == 0) {
            getAll().add(souvenir);
            souvenir.setId(souvenirsDAO.getAll().size() - 1);
            if (souvenir.getProducer() != null){
                setProducersService(ProducerServiceImpl.getInstance());
                producersService.save(souvenir.getProducer());
            }
        }
        return souvenir;
    }

    @Override
    public Souvenir update(Souvenir updatedSouvenir) throws ResourcesNotFoundException {
        Optional<Souvenir> souvenirForUpdating = getAll().stream().filter(s -> s.getId() == updatedSouvenir.getId()).findAny();
        if (souvenirForUpdating.isPresent()) {
            getAll().remove(souvenirForUpdating.get());
            getAll().add(updatedSouvenir);
        } else {
            throw new ResourcesNotFoundException("Souvenir doesn't exist");
        }
        return updatedSouvenir;
    }

    @Override
    public List<Souvenir> getAll() {
        return souvenirsDAO.getAll();
    }

    @Override
    public List<Souvenir> getByCountry(Country country) {
        return getAll().stream()
                .filter(souvenir -> souvenir.getProducer().getCountry().equals(country))
                .collect(Collectors.toList());
    }

    @Override
    public List<Producer> getProducersOfSouvenirNameCreatedAt(String souvenirName, int year) {
        return getAll().stream()
                .filter(souvenir -> souvenir.getName().equals(souvenirName))
                .filter(souvenir -> souvenir.getCreatedAt().getYear() == year)
                .map(Souvenir::getProducer)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Map<Integer, List<Souvenir>> getMapYearSouvenirs() {
        return getAll().stream()
                .collect(Collectors.groupingBy(souvenir -> souvenir.getCreatedAt().getYear()));
    }
}
