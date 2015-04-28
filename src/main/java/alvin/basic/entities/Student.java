package alvin.basic.entities;

import alvin.core.convert.LocalDateTimeConvert;
import com.google.common.base.Joiner;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "student")
@SecondaryTable(name = "student_detail")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
//  @Column(table = "student")
    private String sno;
    private String name;
    private String gender;
    private String telephone;

    @Column(table = "student_detail")
    @Convert(converter = LocalDateTimeConvert.class)
    private LocalDateTime birthday;

    @Column(table = "student_detail")
    private String address;

    @Column(table = "student_detail")
    private String email;

    @Column(table = "student_detail")
    private String qq;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return Joiner.on(";").join(id == null ? "" : id.toString(), sno, name, gender,
                telephone, birthday, address, email, qq);
    }
}
