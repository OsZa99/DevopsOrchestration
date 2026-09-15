package com.example.devops.web;

import com.example.devops.domain.Person;
import com.example.devops.domain.PersonRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/persons")
public class PersonController {

    
    private PersonRepository personRepo;

    public PersonController(PersonRepository personRepo) {
        this.personRepo = personRepo;
    }
    
    @GetMapping
    public Iterable<Person> getPersons(){
        return this.personRepo.findAll();
    }
}