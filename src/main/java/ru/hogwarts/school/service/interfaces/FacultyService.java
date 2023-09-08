package ru.hogwarts.school.service.interfaces;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyService {

    Faculty create(Faculty faculty);

    Faculty reade(long id);

    Faculty update(Faculty faculty);

    void delete(long id);

    List<Faculty> findByColor(String color);
}


