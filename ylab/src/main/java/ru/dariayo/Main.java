package ru.dariayo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import ru.dariayo.factory.CollectionFactory;
import ru.dariayo.factory.DefaultCollectionFactory;

public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "123";

    public static void main(String[] args) throws IOException, SQLException {
        try (Scanner scanner = new Scanner(System.in)) {
            Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(),
                    database);
            liquibase.update();
            System.out.println("Migration is completed successfully");
            CollectionFactory factory = new DefaultCollectionFactory();

            CommandManager commandManager = factory.createCommandManager();
            System.out.println("Введите команду login для входа или register для регистрации, help - все команды");
            String input;
            do {
                if (!scanner.hasNextLine())
                    return;
                input = scanner.nextLine();
                commandManager.existCommand(input);

            } while (!input.equals("exit"));
            // auditLogRepository.exportLogsToFile("audit_log.txt");
        } catch (SQLException | LiquibaseException e) {
            System.out.println("SQL Exception in migration " + e.getMessage());
        }
    }
}