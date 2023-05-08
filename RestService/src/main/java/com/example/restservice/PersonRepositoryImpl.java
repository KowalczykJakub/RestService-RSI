package com.example.restservice;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class PersonRepositoryImpl implements PersonRepository {

    private List<Person> personList;

    public PersonRepositoryImpl() {
        personList = new ArrayList<>();
    }

    @Override
    public List<Person> getAllPersons() {
        return personList;
    }

    public Person getPerson(int id) {
        for (Person thePerson : personList) {
            if (thePerson.getId() == id) {
                return thePerson;
            }
        }
        throw new PersonNotFoundEx(id);
    }

    @Override
    public int getPersonListSize() {
        return personList.size();
    }

    @Override
    public Person addPerson(Person person) throws BadRequestEx {
        boolean existsWithTheSameId = personList.stream()
                .anyMatch(p -> p.getId() == person.getId());

        if (existsWithTheSameId) {
            throw new PersonIdAlreadyExistsEx(person.getId());
        } else if (personList.contains(person)) {
            throw new PersonAlreadyExistsEx();
        }
        personList.add(person);

        return person;
    }

    @Override
    public Person updatePerson(int personId, Person person) throws PersonNotFoundEx {
        OptionalInt indexOpt = IntStream.range(0, personList.size())
                .filter(i -> personList.get(i).getId() == personId)
                .findFirst();
        if (indexOpt.isEmpty()) {
            throw new PersonNotFoundEx(personId);
        }
        person.setId(personId);
        personList.set(indexOpt.getAsInt(), person);
        return person;
    }

    @Override
    public boolean deletePerson(int id) throws PersonNotFoundEx {
        Person person = getPerson(id);
        return personList.remove(person);
    }
}
