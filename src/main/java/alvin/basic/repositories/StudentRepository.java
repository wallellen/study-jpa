package alvin.basic.repositories;

import alvin.basic.entities.Student;
import alvin.core.repositories.RepositorySupport;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

@Singleton
public class StudentRepository extends RepositorySupport<Student> {

    @Inject
    public StudentRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }
}
