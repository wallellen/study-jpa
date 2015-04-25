package alvin.basic.services;

import alvin.basic.entities.Person;
import alvin.configs.TestSupport;
import org.junit.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class PersonServiceTest extends TestSupport {

    @Inject
    private PersonService personService;

    @Test
    public void test_save_person() throws Exception {
        Person person = new Person();
        person.setName("Alvin");
        person.setGender("M");
        person.setEmail("alvin@fake-email.com");
        person.setTelephone("13999999999");
        person.setBirthday(getBirthday(1981, 3, 17));

        personService.save(person);
    }

    private LocalDateTime getBirthday(int year, int month, int date) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, date, 0, 0, 0);
        return localDateTime.atOffset(ZoneOffset.UTC).toLocalDateTime();
    }
}