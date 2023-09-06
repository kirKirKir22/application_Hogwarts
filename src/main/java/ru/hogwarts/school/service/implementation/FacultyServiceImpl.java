package ru.hogwarts.school.service.implementation;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyCRUDException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.interfaces.FacultyService;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final Map<Long, Faculty> facultyMap = new HashMap<>();

    private long counterId = 1L;


    @Override
    public Faculty create(Faculty faculty) {
        if (facultyMap.containsValue(faculty)) {
            throw new FacultyCRUDException("такой факультет уже существует");
        }
        faculty.setId(counterId++);
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty reade(long id) {
        if (!facultyMap.containsKey(id)) {
            throw new FacultyCRUDException("Факультет не найден");
        }
        return facultyMap.get(id);
    }

    @Override
    public Faculty update(Faculty faculty) {
        if (!facultyMap.containsKey(faculty.getId())) {
            throw new FacultyCRUDException("Факультет не найден");
        }
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty delete(long id) {
        Faculty faculty = facultyMap.remove(id);
        if (faculty == null) {
            throw new FacultyCRUDException("Факультет не найден");
        }
        return faculty;
    }

    @Override
    public List<Faculty> findColor(String color) {
        return facultyMap.values().stream()
                .filter(st -> st.getColor() == color)
                .collect(Collectors.toUnmodifiableList());

    }
}