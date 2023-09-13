package ru.hogwarts.school.service.implementation;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.interfaces.FacultyService;


import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }


    @Override
    public Faculty create(Faculty faculty) {
        if (facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()).isPresent()) {
            throw new FacultyException("такой факультет уже есть в базе");
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty read(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("факультет в базе не найден");
        }
        return faculty.get();
    }

    @Override
    public Faculty update(Faculty faculty) {
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            throw new FacultyException("факультет в базе не найден");
        }
        return facultyRepository.save(faculty);

    }

    @Override
    public Faculty delete(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("факультет в базе не найден");
        }
        facultyRepository.deleteById(id);
        return faculty.get();
    }

    @Override
    public List<Faculty> findByColor(String color) {
        return facultyRepository.findByColor(color);

    }

    @Override
    public List<Faculty> findByNameOrColor(String name, String color) {
        Optional<Faculty> faculty = Optional.ofNullable(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color));
        if (faculty.isEmpty()) {
            throw new FacultyException("факультет в базе не найден");
        }
        return List.of(faculty.get());
    }


    @Override
    public List<Student> findStudentsByFaculty(long id) {
        return studentRepository.findByFaculty_id(id);
    }

    @Override
    public List<Faculty> findAllFaculties() {
        return facultyRepository.findAll();
    }
}