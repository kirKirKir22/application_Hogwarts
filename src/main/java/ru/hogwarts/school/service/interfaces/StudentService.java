package ru.hogwarts.school.service.interfaces;

import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {

    Student create(Student student);

    Student reade(long id);

    Student update(Student student);

    Student delete(long id);

    List<Student> findAge(int age);

}
