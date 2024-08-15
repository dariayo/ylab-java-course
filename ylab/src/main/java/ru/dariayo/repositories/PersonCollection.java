package ru.dariayo.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ru.dariayo.db.DBManager;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.model.Person;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonCollection {
    private static final String TABLE_USER = "cs_schema.users";
    private static final String CONTACTS = "contacts";
    private static final String PASSWORD = "password";
    private static final String USERNAME = "username";
    private static final String ROLE = "role";
    private Person person;
    private static final Logger logger = Logger.getLogger(PersonCollection.class.getName());
    private AuditLogRepository auditLogRepository;
    private Connection connection;

    private static final String SQL_ADD_USER = String.format("INSERT INTO %s (%s,%s,%s,%s) VALUES (?, ?, ?, ?)",
            TABLE_USER, USERNAME, PASSWORD, ROLE, CONTACTS);
    private static final String SQL_FIND_USER = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_USER, USERNAME);

    public PersonCollection() {
    }

    public PersonCollection(AuditLogRepository auditLogRepository, DBManager dbManager) {
        this.auditLogRepository = auditLogRepository;
        this.connection = dbManager.connectDB();
    }

    /**
     * add person to treeset
     * 
     * @param person
     * @throws SQLException
     */
    public void addPerson(Person person) throws SQLException {
        if (!userExist(person)) {
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_USER);
            statement.setString(1, person.getName());
            statement.setString(2, person.getPassword());
            statement.setString(3, person.getRole());
            statement.setString(4, person.getContacts());
            statement.executeUpdate();
            statement.close();
        }
        setPerson(person);
        logger.log(Level.INFO, "Added person: " + person.getName());
        auditLogRepository.logAction("System", "Add Person", "Added person: " + person.getName());
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

    /**
     * check login and password when user loginz
     * 
     * @param username
     * @param password
     */
    public void checkUser(String username, String password) {

        try (PreparedStatement statement = connection.prepareStatement(
                SQL_FIND_USER)) {
            statement.setString(1, username);
            // statement.setString(2, password);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Person person = new Person(resultSet.getString("username"), resultSet.getString("password"),
                        resultSet.getString("role"), resultSet.getString("contacts"));

                if (person != null) {
                    setPerson(person);
                    logger.log(Level.INFO, "Пользователь авторизирован: " + person.getName());
                    auditLogRepository.logAction("System", "Person login", "Login person: " + person.getName());
                    System.out.println("Добро пожаловать, " + person.getName());

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * print information about users
     */
    public void info() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM cs_schema.users")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println("Имя: " + resultSet.getString("username") + " роль: "
                            + resultSet.getString("role"));
                }
            }
        } catch (Exception e) {
        }
    }

}
