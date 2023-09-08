package ru.hogwarts.school.service.implementation;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.interfaces.StudentService;

import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public Student create(Student student) {
        return studentRepository.save(student);

    }


    @Override
    public Student read(long id) {
        return studentRepository.findById(id).get();
    }


    @Override
    public Student update(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void delete(long id) {
        studentRepository.deleteById(id);

    }


    @Override
    public List<Student> findByAge(int age) {
        return studentRepository.findByAge(age);

    }
}
