package com.nilson.account.services;

import com.nilson.account.exceptions.ResourceNotFoundException;
import com.nilson.account.models.IndividualPerson;
import com.nilson.account.models.Person;
import com.nilson.account.repositories.PersonRepository;
import com.nilson.account.request.IndividualPersonRequest;
import com.nilson.account.response.EntityCreated;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    private static final Logger log = LogManager.getLogger(PersonService.class);

    public EntityCreated create(IndividualPersonRequest individualPersonRequest) {
        Person individualPerson = new IndividualPerson(
                individualPersonRequest.getCpf(),
                individualPersonRequest.getFullName(),
                individualPersonRequest.getBirthDay()
        );

        Person personCreated = personRepository.save(individualPerson);
        log.info(format("Pessoa salva com sucesso %s", personCreated.toString()));

        return new EntityCreated(personCreated.getId());

    }

    public Person findById(long personId) {
        return personRepository.findById(personId).orElseThrow(
                () -> new ResourceNotFoundException(format("Pessoa não encontrada com id: %s", personId)));
    }

    public Iterable<Person> findAll() {
        return personRepository.findAll();

    }
}
