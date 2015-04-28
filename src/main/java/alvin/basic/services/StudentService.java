package alvin.basic.services;

import alvin.basic.entities.Student;
import alvin.basic.repositories.StudentRepository;
import com.google.inject.persist.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StudentService {
    private StudentRepository studentRepository;

    @Inject
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public void save(Student student) {
        studentRepository.save(student);
    }
}
