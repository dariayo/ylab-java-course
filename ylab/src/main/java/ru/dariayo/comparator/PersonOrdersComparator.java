package ru.dariayo.comparator;

import java.util.Comparator;

import ru.dariayo.model.Person;

public class PersonOrdersComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return Integer.compare(p1.getCountOrders(), p2.getCountOrders());
    }
}