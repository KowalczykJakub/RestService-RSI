package com.example.restservice;

import java.util.List;

public interface PersonRepository {
    List<Person> getAllPersons();
    Person getPerson(int id) throws PersonNotFoundEx;
    int getPersonListSize();
    Person addPerson(Person person) throws BadRequestEx;
    Person updatePerson(int id, Person person) throws PersonNotFoundEx;
    boolean deletePerson(int id) throws PersonNotFoundEx;
}
