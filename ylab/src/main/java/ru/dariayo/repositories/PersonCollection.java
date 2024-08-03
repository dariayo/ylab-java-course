package ru.dariayo.repositories;

import java.util.TreeSet;

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
        personCollection.add(person);
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


}
