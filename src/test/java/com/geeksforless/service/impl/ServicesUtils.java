package com.geeksforless.service.impl;

import com.geeksforless.dao.impl.FileDAOUtils;
import com.geeksforless.dao.impl.ProducersFileDAO;
import com.geeksforless.dao.impl.SouvenirsFileDAO;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class ServicesUtils {


    public static void mainSetUp(){
        List<String> filesNames = List.of("test-producers.bin", "test-souvenirs.bin", "test-souvenirs-names.bin");
        filesNames.forEach(ServicesUtils::clearFile);
        ProducersFileDAO.setProducersFileName(filesNames.get(0));
        SouvenirsFileDAO.setSouvenirsFileName(filesNames.get(1));
        SouvenirsFileDAO.setNamesOfSouvenirsFileName(filesNames.get(2));
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

}
