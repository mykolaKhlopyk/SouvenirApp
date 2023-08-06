package com.geeksforless.dao.impl;

import com.geeksforless.dao.ProducersDAO;
import com.geeksforless.domain.entity.Producer;
import com.geeksforless.domain.exception.ConnectionIsAlreadyExistException;

import java.io.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ProducersFileDAO implements ProducersDAO {

    private static String PRODUCERS_FILE_NAME = "producers.bin";

    private List<Producer> producers;

    private static ProducersFileDAO instance;

    private ProducersFileDAO() {
        readDataFromFiles();
    }

    private void readDataFromFiles() {
        try (ObjectInput objectInputProducers = new ObjectInputStream(new FileInputStream(FileDAOUtils.createFilePath(PRODUCERS_FILE_NAME)))) {
            producers = (List<Producer>) objectInputProducers.readObject();
        } catch (ClassNotFoundException | IOException e) {
            producers = new ArrayList<>();
        }
    }

    public static ProducersFileDAO getInstance() {
        if (instance == null)
            instance = new ProducersFileDAO();
        return instance;
    }

    public static void setProducersFileName(String souvenirsFileName) {
        if (instance == null || PRODUCERS_FILE_NAME.contains("test")){
            PRODUCERS_FILE_NAME = souvenirsFileName;
            return;
        }
        throw new ConcurrentModificationException();
    }

    public static String getProducersFilePath() {
        return FileDAOUtils.createFilePath(PRODUCERS_FILE_NAME);
    }

    @Override
    public List<Producer> getAll() {
        return producers;
    }
}
