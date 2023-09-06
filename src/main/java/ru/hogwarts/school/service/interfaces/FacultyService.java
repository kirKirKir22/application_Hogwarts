package ru.hogwarts.school.service.interfaces;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface FacultyService {

    Faculty create(Faculty faculty);

    Faculty reade(long id);

    Faculty update(Faculty faculty);

    Faculty delete(long id);

    List<Faculty> findColor(String color);
}


