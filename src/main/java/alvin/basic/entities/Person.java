package alvin.basic.entities;

import alvin.core.convert.LocalDateTimeConvert;
import com.google.common.base.Joiner;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column()
    private String name;

    @Convert(converter = LocalDateTimeConvert.class)
    private LocalDateTime birthday;
    private String gender;
    private String telephone;
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getAge() {
        return (int) birthday.until(LocalDateTime.now(ZoneOffset.UTC), ChronoUnit.YEARS);
    }

    public void makeBirthday(int year, int month, int date) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, date, 0, 0, 0);
        birthday = localDateTime.atOffset(ZoneOffset.UTC).toLocalDateTime();
    }

    @Override
    public String toString() {
        return Joiner.on(";").join(id == null ? "" : id.toString(),
                name, getAge(), gender, telephone, email);
    }
}
