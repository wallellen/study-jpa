package alvin.basic.services;

import alvin.basic.entities.Person;
import alvin.configs.TestSupport;
import org.junit.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class PersonServiceTest extends TestSupport {

    @Inject
    private PersonService personService;

    @Inject
    private EntityManager em;

    @Test
    public void test_save_person() throws Exception {
        Person person = new Person();
        person.setName("Alvin");
        person.setGender("M");
        person.setEmail("alvin@fake-email.com");
        person.setTelephone("13999999999");
        person.setBirthday(getBirthday(1981, 3, 17));

        personService.save(person);
        assertThat(person.getId(), is(notNullValue()));

        em.clear();

    }

    private LocalDateTime getBirthday(int year, int month, int date) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, date, 0, 0, 0);
        return localDateTime.atOffset(ZoneOffset.UTC).toLocalDateTime();
    }
}