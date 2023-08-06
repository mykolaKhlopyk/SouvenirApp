package com.geeksforless.dao.impl;

import com.geeksforless.dao.PreservingDAO;
import com.geeksforless.domain.entity.Producer;
import com.geeksforless.domain.entity.Souvenir;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

public class PreservingFiIeDAO implements PreservingDAO {
    private static PreservingFiIeDAO instance;

    private List<Souvenir> souvenirs;
    private List<Producer> producers;
    private List<String> names;

    public static PreservingFiIeDAO getInstance() {
        if (instance == null)
            instance = new PreservingFiIeDAO();
        return instance;
    }

    @Override
    public void saveInFile() {
        getCollections();
        rewriteIdsOfProducers();
        rewriteIdsForNamesOfSouvenirs();
        rewriteIdsOfProducersInSouvenirs();
        saveProducersInFile();
        saveSouvenirsInFile();
    }


    private void getCollections() {
        souvenirs = SouvenirsFileDAO.getInstance().getAll();
        producers = ProducersFileDAO.getInstance().getAll();
        names = getAllUniqueNamesSorted();
    }

    private List<String> getAllUniqueNamesSorted() {
        return souvenirs.stream().map(Souvenir::getName).sorted().toList();
    }

    private void rewriteIdsOfProducers() {
        int i = 0;
        for (Producer producer : producers) {
            producer.setId(i++);
        }
    }

    private void rewriteIdsForNamesOfSouvenirs() {
        souvenirs.forEach(souvenir -> souvenir.setNameId(Collections.binarySearch(names, souvenir.getName())));
    }

    private void rewriteIdsOfProducersInSouvenirs() {
        souvenirs.forEach(souvenir -> souvenir.setProducerId(Collections.binarySearch(producers, souvenir.getProducer())));
    }

    private void saveProducersInFile() {
        try (ObjectOutput objectOutputProducers = new ObjectOutputStream(new FileOutputStream(ProducersFileDAO.getProducersFilePath()))) {
            objectOutputProducers.writeObject(producers);
        } catch (IOException e) {
            System.err.println("some error by files");
        }
    }

    private void saveSouvenirsInFile() {
        try (ObjectOutput objectOutputSouvenirs = new ObjectOutputStream(new FileOutputStream(SouvenirsFileDAO.getSouvenirsFilePath()));
             ObjectOutput objectOutputNamesOfSouvenirs = new ObjectOutputStream(new FileOutputStream(SouvenirsFileDAO.getNamesOfSouvenirsFilePath()))) {
            objectOutputSouvenirs.writeObject(souvenirs);
            objectOutputNamesOfSouvenirs.writeObject(names);
        } catch (IOException e) {
            System.err.println("some error by files");
        }
    }

}
