package com.example.restservice;

import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.UnknownHostException;
import java.util.Scanner;

public class PersonClient {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String BASE_URL = "http://localhost:8080/persons";

    public static void main(String[] args) throws UnknownHostException {
        MyData.info();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nPerson Management System");
            System.out.println("1. Get all persons");
            System.out.println("2. Get person by ID");
            System.out.println("3. Get list size");
            System.out.println("4. Add person");
            System.out.println("5. Update person");
            System.out.println("6. Delete person");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1:
                        getAllPersons();
                        break;
                    case 2:
                        System.out.print("Enter person ID: ");
                        int id = scanner.nextInt();
                        getPersonById(id);
                        break;
                    case 3:
                        getPersonListSize();
                        break;
                    case 4:
                        System.out.print("Enter person ID: ");
                        int newId = scanner.nextInt();
                        System.out.print("Enter person name: ");
                        String name = scanner.next();
                        System.out.print("Enter person age: ");
                        int age = scanner.nextInt();
                        addPerson(newId, name, age);
                        break;
                    case 5:
                        System.out.print("Enter person ID: ");
                        int updateId = scanner.nextInt();
                        System.out.print("Enter new person name: ");
                        String newName = scanner.next();
                        System.out.print("Enter person age: ");
                        int newAge = scanner.nextInt();
                        updatePerson(updateId, newName, newAge);
                        break;
                    case 6:
                        System.out.print("Enter person ID: ");
                        int deleteId = scanner.nextInt();
                        deletePerson(deleteId);
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (HttpClientErrorException e) {
                int statusCode = e.getRawStatusCode();
                String responseBody = e.getResponseBodyAsString();
                if (statusCode == HttpStatus.NOT_FOUND.value()) {
                    System.out.println("Error: Person not found.");
                    System.out.println("Details: " + responseBody);
                } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                    System.out.println("Error: Invalid request.");
                    System.out.println("Details: " + responseBody);
                } else {
                    System.out.println("An error occurred: " + e.getMessage());
                    System.out.println("Details: " + responseBody);
                }
            }
        }
    }

    private static void getAllPersons() {
        ResponseEntity<String> response = REST_TEMPLATE.getForEntity(BASE_URL, String.class);
        System.out.println("All persons: \n" + response.getBody());
    }

    private static void getPersonById(int id) {
        ResponseEntity<String> response = REST_TEMPLATE.getForEntity(BASE_URL + "/" + id, String.class);
        System.out.println("Person details: \n" + response.getBody());
    }

    private static void getPersonListSize() {
        ResponseEntity<String> response = REST_TEMPLATE.getForEntity(BASE_URL + "/size", String.class);
        System.out.println("List size: \n" + response.getBody());
    }

    private static void addPerson(int id, String name, int age) {
        Person person = new Person(id, name, age);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> entity = new HttpEntity<>(person, headers);

        ResponseEntity<String> response = REST_TEMPLATE.postForEntity(BASE_URL, entity, String.class);
        System.out.println("Added person: \n" + response.getBody());
    }

    private static void updatePerson(int id, String newName, int age) {
        Person person = new Person(id, newName, age);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> entity = new HttpEntity<>(person, headers);

        REST_TEMPLATE.put(BASE_URL + "/" + id, entity);
        System.out.println("Updated person with ID: " + id);
    }

    private static void deletePerson(int id) {
        REST_TEMPLATE.delete(BASE_URL + "/" + id);
        System.out.println("Deleted person with ID: " + id);
    }
}
