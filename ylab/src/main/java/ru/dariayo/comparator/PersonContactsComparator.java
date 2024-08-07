package ru.dariayo.comparator;

import java.util.Comparator;

import ru.dariayo.model.Person;

public class PersonContactsComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return p1.getContacts().compareTo(p2.getContacts());
    }

}
