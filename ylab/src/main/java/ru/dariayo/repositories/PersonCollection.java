package ru.dariayo.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class PersonCollection {

    private static final String TABLE_USER = "cs_schema.users";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ROLE = "role";
    private static final String CONTACTS = "contacts";

    private static final Logger logger = Logger.getLogger(PersonCollection.class.getName());

    private final JdbcTemplate jdbcTemplate;
    private final AuditLogRepository auditLogRepository;

    @Autowired
    public PersonCollection(JdbcTemplate jdbcTemplate, AuditLogRepository auditLogRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.auditLogRepository = auditLogRepository;
    }

    /**
     * add person to treeset
     * 
     * @param person
     * @throws SQLException
     */
    public void addPerson(Person person) {
        if (!userExists(person)) {
            String sql = String.format("INSERT INTO %s (%s,%s,%s,%s) VALUES (?, ?, ?, ?)", TABLE_USER, USERNAME,
                    PASSWORD, ROLE, CONTACTS);
            jdbcTemplate.update(sql, person.getName(), person.getPassword(), person.getRole(), person.getContacts());
            logger.log(Level.INFO, "Added person: " + person.getName());
            auditLogRepository.logAction("System", "Add Person", "Added person: " + person.getName());
        }
    }

    public boolean userExists(Person person) {
        String sql = String.format("SELECT COUNT(*) FROM %s WHERE %s = ?", TABLE_USER, CONTACTS);
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, person.getContacts());
        return count != null && count > 0;
    }

    /**
     * check login and password when user login
     * 
     * @param username
     * @param password
     */
    public boolean checkUser(String username, String password) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_USER, USERNAME);
        List<Person> users = jdbcTemplate.query(sql, this::mapRowToPerson, username);

        if (!users.isEmpty()) {
            Person person = users.get(0);
            if (person.getPassword().equals(password)) {
                logger.log(Level.INFO, "User logged in: " + person.getName());
                auditLogRepository.logAction("System", "Person login", "Login person: " + person.getName());
                return true;
            }
        }
        return false;
    }

    /**
     * print information about users
     */
    public List<Person> getUsers() {
        String sql = "SELECT * FROM cs_schema.users";
        return jdbcTemplate.query(sql, this::mapRowToPerson);
    }

    private Person mapRowToPerson(ResultSet rs, int rowNum) throws SQLException {
        return new Person(
                rs.getString(USERNAME),
                rs.getString(PASSWORD),
                rs.getString(ROLE),
                rs.getString(CONTACTS));
    }
}
