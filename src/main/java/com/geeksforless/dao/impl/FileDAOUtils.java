package com.geeksforless.dao.impl;


public class FileDAOUtils {
    public static String createFilePath(String fileName) {
        return ClassLoader.getSystemClassLoader().getResource(fileName).getPath().substring(1);
    }
}
