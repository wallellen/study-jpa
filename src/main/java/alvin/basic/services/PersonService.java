package alvin.basic.services;

import alvin.basic.entities.Person;
import alvin.basic.repositories.PersonRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
public class PersonService {
    private PersonRepository personRepository;

    @Inject
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }
}
