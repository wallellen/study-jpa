package alvin.basic;

import alvin.basic.entities.Person;
import alvin.basic.entities.Student;
import alvin.configs.TestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TableTest extends TestSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableTest.class);

    @Test
    public void test_save_person() {
        Person expectedPerson = createPerson();
        LOGGER.info("before persist: {}", expectedPerson);

        em.getTransaction().begin();
        try {
            em.persist(expectedPerson);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }

        assertThat(expectedPerson.getId(), is(notNullValue()));
        LOGGER.info("after persist: {}", expectedPerson);

        em.clear();

        Person actualPerson = em.find(Person.class, expectedPerson.getId());
        LOGGER.info("after load: {}", actualPerson);

        assertThat(expectedPerson.toString(), is(actualPerson.toString()));
    }

    private Person createPerson() {
        Person person = new Person();
        person.setName("Alvin");
        person.setGender("M");
        person.setEmail("alvin@fakeaddr.com");
        person.setTelephone("13999999999");
        person.setBirthday(LocalDateTime.of(1981, 3, 17, 0, 0).atOffset(ZoneOffset.UTC).toLocalDateTime());
        return person;
    }

    @Test
    public void test_second_table() throws Exception {
        Student expectedStudent = createStudent();
        LOGGER.info("before persist: {}", expectedStudent);

        em.getTransaction().begin();
        try {
            em.persist(expectedStudent);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }

        assertThat(expectedStudent.getId(), is(notNullValue()));
        LOGGER.info("after persist: {}", expectedStudent);

        em.clear();

        Student actualStudent = em.find(Student.class, expectedStudent.getId());
        LOGGER.info("after load: {}", actualStudent);

        assertThat(actualStudent.toString(), is(actualStudent.toString()));
    }

    private Student createStudent() {
        Student student = new Student();
        student.setSno("001");
        student.setName("Alvin");
        student.setGender("M");
        student.setTelephone("13991999999");
        student.setBirthday(LocalDateTime.of(1981, 3, 17, 0, 0).atOffset(ZoneOffset.UTC).toLocalDateTime());
        student.setAddress("Xi'an Shannxi China");
        student.setEmail("alvin@fake.com");
        student.setQq("19888");
        return student;
    }

    @Override
    protected String[] getTruncateTables() {
        return new String[]{"person", "student", "student_detail"};
    }
}
