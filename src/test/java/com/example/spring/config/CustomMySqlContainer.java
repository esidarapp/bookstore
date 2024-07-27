package com.example.spring.config;

import org.testcontainers.containers.MySQLContainer;

public class CustomMySqlContainer extends MySQLContainer<CustomMySqlContainer> {
    private static final String DB_IMAGE = "mysqldb";
    private static CustomMySqlContainer mysqlContainer;

    private CustomMySqlContainer() {
        super(DB_IMAGE);
    }

    public static synchronized CustomMySqlContainer getInstance() {
        if (mysqlContainer == null) {
            mysqlContainer = new CustomMySqlContainer();
        }
        return mysqlContainer;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("TEST_DB_URL", mysqlContainer.getJdbcUrl());
        System.setProperty("TEST_DB_USERNAME", mysqlContainer.getUsername());
        System.setProperty("TEST_DB_PASSWORD", mysqlContainer.getPassword());
    }

    @Override
    public void stop() {
    }
}
