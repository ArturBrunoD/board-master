package com.board.config;

import org.flywaydb.core.Flyway;

public class FlywayConfig {
    public static void migrate() {
        DatabaseConfig dbConfig = DatabaseConfig.getInstance();
        Flyway flyway = Flyway.configure()
                .dataSource(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPassword())
                .load();
        flyway.migrate();
    }
}