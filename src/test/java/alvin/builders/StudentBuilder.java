package alvin.builders;

import alvin.basic.entities.Student;
import alvin.basic.services.StudentService;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class StudentBuilder implements Builder<Student> {
    private StudentService studentService;

    private String sno = "001";
    private String name = "Alvin";
    private String gender = "M";
    private String telephone = "13991999999";
    private LocalDateTime birthday = LocalDateTime.of(1981, 3, 17, 0, 0).atOffset(ZoneOffset.UTC).toLocalDateTime();
    private String address = "Xi'an Shannxi China";
    private String email = "alvin";
    private String qq = "19888";

    @Inject
    public StudentBuilder(StudentService studentService) {
        this.studentService = studentService;
    }

    public StudentBuilder sno(String sno) {
        this.sno = sno;
        return this;
    }

    public StudentBuilder name(String name) {
        this.name = name;
        return this;
    }

    public StudentBuilder gender(String gender) {
        this.gender = gender;
        return this;
    }

    public StudentBuilder telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public StudentBuilder birthday(LocalDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public StudentBuilder address(String address) {
        this.address = address;
        return this;
    }

    public StudentBuilder email(String email) {
        this.email = email;
        return this;
    }

    public StudentBuilder qq(String qq) {
        this.qq = qq;
        return this;
    }

    @Override
    public Student build() {
        Student student = new Student();
        student.setSno(sno);
        student.setName(name);
        student.setGender(gender);
        student.setTelephone(telephone);
        student.setBirthday(birthday);
        student.setAddress(address);
        student.setEmail(email);
        student.setQq(qq);
        return student;
    }

    @Override
    public Student create() {
        Student student = build();
        studentService.save(student);
        return student;
    }
}
