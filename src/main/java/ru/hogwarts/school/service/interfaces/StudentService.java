package ru.hogwarts.school.service.interfaces;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {

    Student create(Student student);

    Student read(long id);

    Student update(Student student);

    Student delete(long id);

    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(Integer min, Integer max);

    Faculty findStudentFaculty(Long studentId);

    List<Student> findAllStudents();

    Integer findStudentCount();

    Integer findAvgAge();

    List<Student> findFiveLastStudent();


}
