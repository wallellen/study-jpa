package alvin.builders;

import alvin.basic.entities.Person;
import alvin.basic.services.PersonService;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class PersonBuilder implements Builder<Person> {
    private PersonService personService;

    private String name = "Alvin";
    private LocalDateTime birthday = LocalDateTime.of(1981, 3, 17, 0, 0).atOffset(ZoneOffset.UTC).toLocalDateTime();
    private String gender = "M";
    private String telephone = "13999999999";
    private String email = "alvin@fakeaddr.com";

    @Inject
    public PersonBuilder(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public Person build() {
        Person person = new Person();
        person.setName(name);
        person.setBirthday(birthday);
        person.setGender(gender);
        person.setTelephone(telephone);
        person.setEmail(email);
        return person;
    }

    @Override
    public Person create() {
        Person person = build();
        personService.save(person);
        return person;
    }

    public PersonBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PersonBuilder birthday(LocalDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public PersonBuilder gender(String gender) {
        this.gender = gender;
        return this;
    }

    public PersonBuilder telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public PersonBuilder email(String email) {
        this.email = email;
        return this;
    }
}
