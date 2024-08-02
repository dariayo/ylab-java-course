package ru.dariayo;

public class Person implements Comparable<Person>  {
    private String name;
    private String role;
    private int countOrders;
    private String contacts;
    private String password;

    public Person(String name, String password, String role, String contacts){
        this.password = password;
        this.name = name;
        this.role = role;
        this.contacts = contacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getCountOrders() {
        return countOrders;
    }

    public void setCountOrders(int countOrders) {
        this.countOrders = countOrders;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @Override
    public int compareTo(Person o) {
        return Integer.compare(this.countOrders - o.getCountOrders(), 0);
    }

    
}
