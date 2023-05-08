package com.example.restservice;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private PersonRepository dataRepo = new PersonRepositoryImpl();

    @GetMapping
    public CollectionModel<EntityModel<Person>> getAllPersons() {
        System.out.println("...called GET");
        List<Person> personList = dataRepo.getAllPersons();

        List<EntityModel<Person>> persons = personList.stream().map(person ->
                EntityModel.of(person,
                        linkTo(methodOn(PersonController.class).getAllPersons()).withSelfRel(),
                        linkTo(methodOn(PersonController.class).getPerson(person.getId())).withRel("get by id"),
                        linkTo(methodOn(PersonController.class).getPersonListSize()).withRel("list size"),
                        linkTo(methodOn(PersonController.class).addPerson(person)).withRel("add"),
                        linkTo(methodOn(PersonController.class).modifyPerson(person.getId(), person)).withRel("modify"),
                        linkTo(methodOn(PersonController.class).deletePerson(person.getId())).withRel("delete")
                )
        ).collect(Collectors.toList());

        return CollectionModel.of(persons,
                linkTo(methodOn(PersonController.class).getAllPersons()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Person> getPerson(@PathVariable int id) {
        System.out.println("...called GET with ID");
        Person person = dataRepo.getPerson(id);
        return EntityModel.of(person,
                linkTo(methodOn(PersonController.class).getPerson(person.getId())).withSelfRel(),
                linkTo(methodOn(PersonController.class).getAllPersons()).withRel("list all"),
                linkTo(methodOn(PersonController.class).getPersonListSize()).withRel("list size"),
                linkTo(methodOn(PersonController.class).addPerson(person)).withRel("add"),
                linkTo(methodOn(PersonController.class).modifyPerson(person.getId(), person)).withRel("modify"),
                linkTo(methodOn(PersonController.class).deletePerson(person.getId())).withRel("delete")
        );
    }

    @GetMapping("/size")
    public EntityModel<Map<String, Integer>> getPersonListSize() {
        System.out.println("...called GET with SIZE");
        Map<String, Integer> sizeMap = new HashMap<>();
        sizeMap.put("size", dataRepo.getPersonListSize());
        return EntityModel.of(sizeMap,
                linkTo(methodOn(PersonController.class).getPersonListSize()).withSelfRel(),
                linkTo(methodOn(PersonController.class).getAllPersons()).withRel("list all")
        );
    }

    @PostMapping
    public EntityModel<Person> addPerson(@RequestBody Person person) {
        System.out.println("...called POST");
        dataRepo.addPerson(person);
        return EntityModel.of(person,
                linkTo(methodOn(PersonController.class).addPerson(person)).withSelfRel(),
                linkTo(methodOn(PersonController.class).getPerson(person.getId())).withRel("get by id"),
                linkTo(methodOn(PersonController.class).getAllPersons()).withRel("list all"),
                linkTo(methodOn(PersonController.class).getPersonListSize()).withRel("list size"),
                linkTo(methodOn(PersonController.class).modifyPerson(person.getId(), person)).withRel("modify"),
                linkTo(methodOn(PersonController.class).deletePerson(person.getId())).withRel("delete")
        );
    }

    @PutMapping("/{id}")
    public EntityModel<Person> modifyPerson(@PathVariable int id, @RequestBody Person person) {
        System.out.println("...called PUT");
        dataRepo.updatePerson(id, person);
        return EntityModel.of(person,
                linkTo(methodOn(PersonController.class).addPerson(person)).withSelfRel(),
                linkTo(methodOn(PersonController.class).getPerson(person.getId())).withRel("get by id"),
                linkTo(methodOn(PersonController.class).getAllPersons()).withRel("list all"),
                linkTo(methodOn(PersonController.class).getPersonListSize()).withRel("list size"),
                linkTo(methodOn(PersonController.class).modifyPerson(person.getId(), person)).withSelfRel(),
                linkTo(methodOn(PersonController.class).deletePerson(person.getId())).withRel("delete")
        );
    }

    @DeleteMapping("/{id}")
    public EntityModel<?> deletePerson(@PathVariable int id) {
        System.out.println("...called DELETE");
        dataRepo.deletePerson(id);
        return EntityModel.of(
                linkTo(methodOn(PersonController.class).deletePerson(id)).withSelfRel(),
                linkTo(methodOn(PersonController.class).getPerson(id)).withRel("get by id"),
                linkTo(methodOn(PersonController.class).getAllPersons()).withRel("list all"),
                linkTo(methodOn(PersonController.class).getPersonListSize()).withRel("list size")
        );
    }
}
