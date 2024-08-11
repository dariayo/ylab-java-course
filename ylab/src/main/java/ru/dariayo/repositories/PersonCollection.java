package ru.dariayo.repositories;

import java.sql.SQLException;
import java.util.TreeSet;

import ru.dariayo.comparator.*;
import ru.dariayo.db.DBManager;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.model.Person;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonCollection {
    private Person person;
    private static final Logger logger = Logger.getLogger(PersonCollection.class.getName());
    private AuditLogRepository auditLogRepository;
    private DBManager dbManager;

    public PersonCollection() {
    }

    public PersonCollection(AuditLogRepository auditLogRepository, DBManager dbManager) {
        this.auditLogRepository = auditLogRepository;
        this.dbManager = dbManager;
    }

    /**
     * add person to treeset
     * 
     * @param person
     * @throws SQLException
     */
    public void addPerson(Person person) throws SQLException {
        dbManager.registerUser(person);
        setPerson(person);
        logger.log(Level.INFO, "Added person: " + person.getName());
        auditLogRepository.logAction("System", "Add Person", "Added person: " + person.getName());
    }

    /**
     * check login and password when user loginz
     * 
     * @param username
     * @param password
     */
    public void checkUser(String username, String password) {
        if (dbManager.userLogin(username, password)) {
            setPerson(person);
            logger.log(Level.INFO, "Пользователь авторизирован: " + person.getName());
            auditLogRepository.logAction("System", "Person login", "Login person: " + person.getName());
            System.out.println("Добро пожаловать, " + person.getName());

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
        dbManager.infoUsers();
    }

    /**
     * sort users by name, contacts and count of orders
     * 
     * @param param
     */
    // public void printSortedUsers(String param) {
    // TreeSet<Person> sortedCollection;

    // switch (param) {
    // case "name":
    // sortedCollection = new TreeSet<>(new PersonNameComparator());
    // break;
    // case "contacts":
    // sortedCollection = new TreeSet<>(new PersonContactsComparator());
    // break;
    // case "orders":
    // sortedCollection = new TreeSet<>(new PersonOrdersComparator());
    // break;
    // default:
    // System.out.println("Invalid sort parameter.");
    // return;
    // }

    // sortedCollection.addAll(personCollection);

    // for (Person person : sortedCollection) {
    // System.out.println(person.getName() + ", " + person.getRole() + ", " +
    // person.getContacts() + ", "
    // + person.getCountOrders());
    // }
    // }

}
