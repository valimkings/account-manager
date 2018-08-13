package com.nilson.account.services;

import com.nilson.account.exceptions.ResourceNotFoundException;
import com.nilson.account.models.OrganizationPerson;
import com.nilson.account.models.Person;
import com.nilson.account.repositories.PersonRepository;
import com.nilson.account.request.OrganizationPersonRequest;
import com.nilson.account.request.Response.EntityCreated;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class OrganizationPersonService {

    @Autowired
    PersonRepository personRepository;

    private static final Logger log = LogManager.getLogger(OrganizationPersonService.class);

    public EntityCreated create(OrganizationPersonRequest organizationPersonRequest) {
        Person person = new OrganizationPerson(
                organizationPersonRequest.getCnpj(),
                organizationPersonRequest.getSocialName(),
                organizationPersonRequest.getFantasyName()
        );

        log.info(format("Pessoa juridica foi salva com sucesso %s", person.toString()));

        Person personCreated = personRepository.save(person);
        return new EntityCreated(personCreated.getId());

    }

}
