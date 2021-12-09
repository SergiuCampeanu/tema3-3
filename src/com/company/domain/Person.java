package com.company.domain;

/**
 * @author sncam
 */
public class Person {

    private String name;
    private String firstName;

    public Person(String name, String firstName) {
        this.name = name;
        this.firstName = firstName;
    }

    /*
getter and setter
*/

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name of the person
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName of the person
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
