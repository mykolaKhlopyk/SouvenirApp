package com.geeksforless.dao.impl;

import com.geeksforless.dao.SouvenirsDAO;
import com.geeksforless.domain.entity.Producer;
import com.geeksforless.domain.entity.Souvenir;

import java.io.*;
import java.util.*;

public class SouvenirsFileDAO implements SouvenirsDAO {
    private static String SOUVENIRS_FILE_NAME = "souvenirs.bin";
    private static String NAMES_OF_SOUVENIRS_FILE_NAME = "souvenirs-names.bin";
    private List<Souvenir> souvenirs;

    private static SouvenirsFileDAO instance;

    private SouvenirsFileDAO() {
        readDataFromFiles();
    }

    private void readDataFromFiles() {
        List<String> names = null;
        try (ObjectInput objectInputSouvenirs = new ObjectInputStream(new FileInputStream(FileDAOUtils.createFilePath(SOUVENIRS_FILE_NAME)));
             ObjectInput objectInputNamesOfSouvenirs = new ObjectInputStream(new FileInputStream(FileDAOUtils.createFilePath(NAMES_OF_SOUVENIRS_FILE_NAME)))) {
            souvenirs = (List<Souvenir>) objectInputSouvenirs.readObject();
            names = (List<String>) objectInputNamesOfSouvenirs.readObject();
            injectNamesToSouvenirs(names);
            injectProducersToSouvenirs();
        } catch (ClassNotFoundException | IOException e) {
            souvenirs = new ArrayList<>();
            names = new ArrayList<>();
        }

    }

    private void injectNamesToSouvenirs(List<String> names) {
        souvenirs.forEach(souvenir -> souvenir.setName(names.get(souvenir.getNameId())));
    }

    private void injectProducersToSouvenirs() {
        List<Producer> producers = ProducersFileDAO.getInstance().getAll();
        souvenirs.forEach(souvenir -> souvenir.setProducer(producers.get(souvenir.getProducerId())));
    }


    public static SouvenirsFileDAO getInstance() {
        if (instance == null) {
            instance = new SouvenirsFileDAO();
        }
        return instance;
    }

    public static String getSouvenirsFilePath() {
        return FileDAOUtils.createFilePath(SOUVENIRS_FILE_NAME);
    }

    public static String getNamesOfSouvenirsFilePath() {
        return FileDAOUtils.createFilePath(NAMES_OF_SOUVENIRS_FILE_NAME);
    }

    public static void setSouvenirsFileName(String souvenirsFileName) {
        if (instance == null || SOUVENIRS_FILE_NAME.contains("test")) {
            SOUVENIRS_FILE_NAME = souvenirsFileName;
            return;
        }
        throw new ConcurrentModificationException();
    }

    public static void setNamesOfSouvenirsFileName(String namesOfSouvenirsFileName) {
        if (instance == null || NAMES_OF_SOUVENIRS_FILE_NAME.contains("test")) {
            NAMES_OF_SOUVENIRS_FILE_NAME = namesOfSouvenirsFileName;
            return;
        }
        throw new ConcurrentModificationException();
    }

    @Override
    public List<Souvenir> getAll() {
        return souvenirs;
    }

}
