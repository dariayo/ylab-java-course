package ru.dariayo;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.stereotype.Service;

import java.net.URL;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

@Service
public class LiquibaseManager {
    public LiquibaseManager() {
    }

    /**
     * connect with liquibase
     */
    public void createBase() {
        Connection connection;
        try {

            URL fileUrl = LiquibaseManager.class.getClassLoader().getResource("db/db.properties");

            FileInputStream fis = new FileInputStream(fileUrl.getFile());
            Properties properties = new Properties();
            properties.load(fis);

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");
            String path = properties.getProperty("liquibase.changelog.path");
            connection = DriverManager.getConnection(url, user, password);
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(path, new ClassLoaderResourceAccessor(),
                    database);
            liquibase.update();
            System.out.println("Migration is completed successfully");
        } catch (SQLException | LiquibaseException | IOException e) {
            System.out.println("SQL Exception in migration " + e.getMessage());
        }
    }
}
