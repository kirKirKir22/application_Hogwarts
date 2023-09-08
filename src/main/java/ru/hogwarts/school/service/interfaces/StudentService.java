package ru.hogwarts.school.service.interfaces;

import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {

    Student create(Student student);

    Student read(long id);

    Student update(Student student);

    void delete(long id);

    List<Student> findByAge(int age);
}
