package ru.hogwarts.school.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository,
                              StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }


    @Override
    public Faculty create(Faculty faculty) {
        logger.info("вызван метод create с данными: " + faculty);
        if (facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()).isPresent()) {
            throw new FacultyException("такой факультет уже есть в базе");
        }
        Faculty savedFaculty = facultyRepository.save(faculty);
        logger.info("метод create вернул: " + savedFaculty);

        return savedFaculty;
    }

    @Override
    public Faculty read(long id) {
        logger.info("вызван метод read с данными: " + id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("факультет в базе не найден");
        }
        Faculty readFaculty = faculty.get();
        logger.info("метод read вернул: " + readFaculty);
        return readFaculty;
    }

    @Override
    public Faculty update(Faculty faculty) {

        logger.info("вызван метод update с данными: " + faculty);
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            throw new FacultyException("факультет в базе не найден");
        }
        Faculty updateFaculty = facultyRepository.save(faculty);
        logger.info("метод update вернул: " + updateFaculty);
        return updateFaculty;

    }

    @Override
    public Faculty delete(long id) {
        logger.info("вызван метод delete с данными: " + id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("факультет в базе не найден");
        }
        Faculty deleteFaculty = faculty.get();
        logger.info("метод delete вернул: " + faculty);
        return deleteFaculty;
    }

    @Override
    public List<Faculty> findByColor(String color) {
        logger.info("был вызван метод findByColor с данными" + color);

        List<Faculty> facultyList = facultyRepository.findByColor(color);

        logger.info("метод findByColor вернул: " + facultyList);
        return facultyList;
    }

    @Override
    public List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        logger.info("был вызван метод findByNameIgnoreCaseOrColorIgnoreCase с данными" + name + color);

        List<Faculty> facultyList = facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);

        logger.info("метод findByNameIgnoreCaseOrColorIgnoreCase вернул: " + facultyList);
        return facultyList;


    }


    @Override
    public List<Student> findStudentsByFaculty(long id) {
        logger.info("был вызван метод findStudentsByFaculty с данными" + id);

        List<Student> studentList = studentRepository.findByFacultyId(id);

        logger.info("метод findStudentsByFaculty вернул: " + studentList);

        return studentList;
    }

    @Override
    public List<Faculty> findAllFaculties() {
        logger.info("был вызван метод findAllFaculties");

        List<Faculty> facultyList = facultyRepository.findAll();
        logger.info("метод findAllFaculties вернул: " + facultyList);
        return facultyList;
    }
}