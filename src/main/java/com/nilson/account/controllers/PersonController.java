package com.nilson.account.controllers;

import com.nilson.account.models.Person;
import com.nilson.account.request.IndividualPersonRequest;
import com.nilson.account.request.OrganizationPersonRequest;
import com.nilson.account.response.EntityCreated;
import com.nilson.account.services.OrganizationPersonService;
import com.nilson.account.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private OrganizationPersonService organizationPersonService;


    @PostMapping("/individual")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> create(@RequestBody @Valid IndividualPersonRequest individualPersonRequest) {
        EntityCreated personId = personService.create(individualPersonRequest);
        return new ResponseEntity<>(personId, HttpStatus.OK);
    }

    @PostMapping("/organization")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> create(@RequestBody OrganizationPersonRequest organizationPersonRequest) {
        EntityCreated entityCreated = organizationPersonService.create(organizationPersonRequest);

        return new ResponseEntity<>(entityCreated, HttpStatus.OK);
    }

    @GetMapping()
    @RequestMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") long personId) {
        Person person = personService.findById(personId);
        return new ResponseEntity<>(person, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/person")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Person> findAll() {
        return personService.findAll();
    }

}
