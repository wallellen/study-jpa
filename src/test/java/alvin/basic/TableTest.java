package alvin.basic;

import alvin.basic.entities.Person;
import alvin.basic.entities.Student;
import alvin.basic.services.PersonService;
import alvin.basic.services.StudentService;
import alvin.builders.PersonBuilder;
import alvin.builders.StudentBuilder;
import alvin.configs.TestSupport;
import com.google.inject.Inject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TableTest extends TestSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableTest.class);

    @Inject
    private PersonService personService;

    @Inject
    private StudentService studentService;

    @Test
    public void test_save_person() {
        Person expectedPerson = withBuilder(PersonBuilder.class).build();
        LOGGER.info("before persist: {}", expectedPerson);

        personService.save(expectedPerson);

        assertThat(expectedPerson.getId(), is(notNullValue()));
        LOGGER.info("after persist: {}", expectedPerson);

        em.clear();

        Person actualPerson = em.find(Person.class, expectedPerson.getId());
        LOGGER.info("after load: {}", actualPerson);

        assertThat(expectedPerson.toString(), is(actualPerson.toString()));
    }

    @Test
    public void test_second_table() throws Exception {
        Student expectedStudent = withBuilder(StudentBuilder.class).build();
        LOGGER.info("before persist: {}", expectedStudent);

        studentService.save(expectedStudent);

        assertThat(expectedStudent.getId(), is(notNullValue()));
        LOGGER.info("after persist: {}", expectedStudent);

        em.clear();

        Student actualStudent = em.find(Student.class, expectedStudent.getId());
        LOGGER.info("after load: {}", actualStudent);

        assertThat(actualStudent.toString(), is(actualStudent.toString()));
    }

    @Override
    protected String[] getTruncateTables() {
        return new String[]{"person", "student", "student_detail"};
    }
}
