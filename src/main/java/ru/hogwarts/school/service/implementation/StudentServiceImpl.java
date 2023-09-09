package ru.hogwarts.school.service.implementation;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentCRUDException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.interfaces.StudentService;

import java.util.List;
import java.util.Optional;


@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public Student create(Student student) {

        if (studentRepository.findByNameAndAge(student.getName(), student.getAge()).isPresent()) {
            throw new StudentCRUDException("такой студент уже есть в базе данных");
        }
        return studentRepository.save(student);

    }


    @Override
    public Student read(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentCRUDException("студент в базе не найден");
        }
        return student.get();
    }


    @Override
    public Student update(Student student) {
        if (studentRepository.findById(student.getId()).isEmpty()) {
            throw new StudentCRUDException("студент в базе не найден");

        }
        return studentRepository.save(student);

    }

    @Override
    public Student delete(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentCRUDException("студент в базе не найден");
        }
        studentRepository.deleteById(id);
        return student.get();


    }


    @Override
    public List<Student> findByAge(int age) {
        return studentRepository.findByAge(age);

    }
}
