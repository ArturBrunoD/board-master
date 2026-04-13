package com.board.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DatabaseConfig {
    private static DatabaseConfig instance;
    private String url;
    private String user;
    private String password;

    private DatabaseConfig() {
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        this.url = bundle.getString("db.url");
        this.user = bundle.getString("db.user");
        this.password = bundle.getString("db.password");
    }

    public static synchronized DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}