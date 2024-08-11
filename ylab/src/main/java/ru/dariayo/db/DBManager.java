package ru.dariayo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ru.dariayo.model.Person;

public class DBManager {
    private static final String TABLE_USER = "tableusers";
    private static final String CONTACTS = "contacts"; // No need for double quotes unless reserved keyword
    private static final String PASSWORD = "password"; // Same here
    private static final String USERNAME = "username"; // No need for double quotes unless reserved keyword
    private static final String ROLE = "role";
    private final String url;
    private final String username;
    private final String password;
    private Connection connection;

    private static final String SQL_FIND_USERNAME = String.format("SELECT COUNT(*) FROM %s WHERE %s = ?", TABLE_USER, CONTACTS);
    private static final String SQL_ADD_USER = String.format("INSERT INTO %s (%s,%s,%s, %s) VALUES (?, ?, ?, ?)", TABLE_USER, USERNAME, PASSWORD, ROLE, CONTACTS);

    private static final String SQL_CREATE_PERSON = String.format("DROP TABLE IF EXISTS %s; CREATE TABLE %s (id SERIAL PRIMARY KEY, username TEXT, password TEXT, role TEXT, contacts TEXT);",TABLE_USER,  TABLE_USER);

    public DBManager(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;

    }

    public void connectDB() {

        try {
            connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {

        }
    }

    public void registerUser(Person person) throws SQLException {
        if (!userExist(person)) {
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_USER);
            statement.setString(1, person.getName());
            statement.setString(2, person.getPassword());
            statement.setString(3, person.getRole());
            statement.setString(4, person.getContacts());
            statement.executeUpdate();
            statement.close();
        }

    }

    public boolean userExist(Person person) throws SQLException {
        String sql = String.format("SELECT COUNT(*) FROM %s WHERE %s = ?", TABLE_USER, CONTACTS);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, person.getContacts());
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        statement.close();
        return count != 0;
    }

    public void createTablePerson() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_CREATE_PERSON);
        System.out.println("SSSS    ");
        statement.executeUpdate();
        System.out.println("SSSSsa    ");
        statement.close();
    }

}
