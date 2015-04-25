package alvin.basic.repositories;

import alvin.basic.entities.Person;
import alvin.core.repositories.RepositorySupport;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

@Singleton
public class PersonRepository extends RepositorySupport<Person> {

    @Inject
    public PersonRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }
}
