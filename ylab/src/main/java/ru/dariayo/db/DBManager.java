package ru.dariayo.db;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;

public class DBManager {
    private final String url;
    private final String username;
    private final String password;
    private Connection connection;

    public DBManager(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;

    }

    /**
     * create connection
     * @return
     */
    public Connection connectDB() {

        try {
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

}
