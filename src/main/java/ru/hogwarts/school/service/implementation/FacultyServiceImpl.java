package ru.hogwarts.school.service.implementation;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyCRUDException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.interfaces.FacultyService;


import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public Faculty create(Faculty faculty) {
        if (facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()).isPresent()) {
            throw new FacultyCRUDException("такой факультет уже есть в базе");
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty read(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyCRUDException("факультет в базе не найден");
        }
        return faculty.get();
    }

    @Override
    public Faculty update(Faculty faculty) {
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            throw new FacultyCRUDException("факультет в базе не найден");
        }
        return facultyRepository.save(faculty);

    }

    @Override
    public Faculty delete(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyCRUDException("факультет в базе не найден");
        }
        facultyRepository.deleteById(id);
        return faculty.get();
    }

    @Override
    public List<Faculty> findByColor(String color) {
        return facultyRepository.findByColor(color);

    }
}