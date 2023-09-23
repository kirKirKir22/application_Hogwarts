package ru.hogwarts.school.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.interfaces.StudentService;

import java.util.List;
import java.util.Optional;


@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {

        logger.info("вызван метод create с данными:" + student);

        if (studentRepository.findByNameAndAge(student.getName(), student.getAge()).isPresent()) {
            throw new StudentException("такой студент уже есть в базе данных");
        }
        Student savedStudent = studentRepository.save(student);
        logger.info("метод create вернул: " + savedStudent);

        return savedStudent;

    }

    @Override
    public Student read(long id) {

        logger.info("был вызван метод read с данными" + id);

        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentException("студент в базе не найден");
        }
        Student readStudent = student.get();
        logger.info("метод create вернул: " + readStudent);
        return readStudent;
    }


    @Override
    public Student update(Student student) {

        logger.info("был вызван метод update с данными" + student);

        if (studentRepository.findById(student.getId()).isEmpty()) {
            throw new StudentException("студент в базе не найден");
        }
        Student updatedStudent = studentRepository.save(student);
        logger.info("метод update вернул: " + updatedStudent);

        return updatedStudent;
    }

    @Override
    public Student delete(long id) {

        logger.info("был вызван метод delete с данными" + id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentException("студент в базе не найден");
        }
        Student deleteStudent = student.get();
        logger.info("метод delete вернул: " + student);
        return deleteStudent;

    }

    @Override
    public List<Student> findByAge(int age) {

        logger.info("был вызван метод findByAge с данными" + age);

        List<Student> studentList = studentRepository.findByAge(age);

        logger.info("метод findByAge вернул: " + studentList);
        return studentList;

    }

    @Override
    public List<Student> findByAgeBetween(Integer min, Integer max) {

        logger.info("был вызван метод findByAgeBetween с данными" + min + max);

        List<Student> studentList = studentRepository.findByAgeBetween(min, max);

        logger.info("метод findByAgeBetween вернул: " + studentList);
        return studentList;

    }

    @Override
    public Faculty findStudentFaculty(Long studentId) {

        logger.info("был вызван метод findStudentFaculty с данными" + studentId);

        Faculty faculty = read(studentId).getFaculty();
        logger.info("метод findStudentFaculty вернул: " + faculty);
        return faculty;
    }

    @Override
    public List<Student> findAllStudents() {

        logger.info("был вызван метод findAllStudents");

        List<Student> studentList = studentRepository.findAll();

        logger.info("метод findAllStudents вернул: " + studentList);
        return studentList;


    }

    @Override
    public Integer findStudentCount() {

        logger.info("был вызван метод findAllStudents");

        Integer integer = studentRepository.findStudentCount();
        logger.info("метод findStudentCount вернул: " + integer);

        return integer;
    }

    @Override
    public Integer findAvgAge() {
        logger.info("был вызван метод findAvgAge");

        Integer integer = studentRepository.findAvgAge();
        logger.info("метод findAvgAge вернул: " + integer);

        return integer;
    }

    @Override
    public List<Student> findFiveLastStudent() {
        logger.info("был вызван метод findFiveLastStudent");

        List<Student> studentList = studentRepository.findLastStudent(5);

        logger.info("метод findFiveLastStudent вернул: " + studentList);
        return studentList;
    }
}