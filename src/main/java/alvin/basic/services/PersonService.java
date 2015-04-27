package alvin.basic.services;

import alvin.basic.entities.Person;
import alvin.basic.repositories.PersonRepository;
import com.google.inject.persist.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;

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
