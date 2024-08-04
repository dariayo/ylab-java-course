package ru.dariayo.repositories;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import ru.dariayo.comparator.PersonContactsComparator;
import ru.dariayo.comparator.PersonNameComparator;
import ru.dariayo.comparator.PersonOrdersComparator;
import ru.dariayo.model.Person;

public class PersonCollection {
    private TreeSet<Person> personCollection = new TreeSet<>();
    private Person person;

    public PersonCollection(TreeSet<Person> personCollection) {
        this.personCollection = personCollection;
    }

    public PersonCollection() {
    }

    public void addPerson(Person person) {
        this.personCollection.add(person);
        setPerson(person);
    }

    public void checkUser(String username, String password) {
        for (Person person : personCollection) {
            if (person.getName().equals(username) && person.getPassword().equals(password)) {
                setPerson(person);
                System.out.println("Добро пожаловать, " + person.getName());
            }
        }
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void info() {
        for (Person person : personCollection) {
            System.out.println(person.getName());
            System.out.println(person.getRole());
        }
    }

    public void printSortedUsers(String param) {
        TreeSet<Person> sortedCollection;

        switch (param) {
            case "name":
                sortedCollection = new TreeSet<>(new PersonNameComparator());
                break;
            case "contacts":
                sortedCollection = new TreeSet<>(new PersonContactsComparator());
                break;
            case "orders":
                sortedCollection = new TreeSet<>(new PersonOrdersComparator());
                break;
            default:
                System.out.println("Invalid sort parameter.");
                return;
        }

        sortedCollection.addAll(personCollection);

        for (Person person : sortedCollection) {
            System.out.println(person.getName() + ", " + person.getRole() + ", " + person.getContacts() + ", " + person.getCountOrders());
        }
    }

}
