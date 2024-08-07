package ru.dariayo.config;

import ru.dariayo.factory.CollectionFactory;
import ru.dariayo.model.Car;
import ru.dariayo.model.Person;
import ru.dariayo.repositories.CarCollection;
import ru.dariayo.repositories.PersonCollection;

public class AppConfig {
    private final PersonCollection personCollection;
    private final CarCollection carCollection;

    public AppConfig(CollectionFactory factory) {
        this.personCollection = factory.createPersonCollection();
        this.carCollection = factory.createCarCollection();
        initializeDefaults();
    }

    private void initializeDefaults() {
        Person person = new Person("Mike", "1234", "user", "mike@gmail.com");
        personCollection.addPerson(person);
        Person person1 = new Person("Katya", "567", "user", "katya@yandex.ru");
        personCollection.addPerson(person1);

        Car car = new Car("Bmw", "M5", 2004, 4000, "Well");
        carCollection.addCar(car);
        Car car1 = new Car("Toyota", "Land", 1998, 2000, "Bad");
        carCollection.addCar(car1);
    }

    public PersonCollection getPersonCollection() {
        return personCollection;
    }

    public CarCollection getCarCollection() {
        return carCollection;
    }
}
