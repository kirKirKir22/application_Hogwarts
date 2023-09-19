package ru.hogwarts.school.service.interfaces;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface FacultyService {

    Faculty create(Faculty faculty);

    Faculty read(long id);

    Faculty update(Faculty faculty);

    Faculty delete(long id);

    List<Faculty> findByColor(String color);

    List<Faculty> findByNameOrColor(String name, String color);

    List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

    List<Student> findStudentsByFaculty(long id);

    List<Faculty> findAllFaculties();

}


