package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    String url = "http://localhost:" + port + "/student";

    Faculty faculty = new Faculty(1L, "физкультурный", "малиновый");
    Student student = new Student(1L, "Igor", 10, faculty);

    @AfterEach
    void afterEach() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }


    @Test
    void create__returnStatus200AndStudent() {

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.
                postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        ResponseEntity<Student> studentResponseEntity = restTemplate.
                postForEntity("http://localhost:" + port + "/student", student, Student.class);

        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(student.getName(), studentResponseEntity.getBody().getName());
        assertEquals(student.getFaculty(), studentResponseEntity.getBody().getFaculty());

    }

    @Test
    void read_studentNotInDb_returnStatus400AndStudent() {
        ResponseEntity<String> stringResponseEntity = restTemplate.getForEntity
                ("http://localhost:" + port + "/student" + student.getId(), String.class);

        assertEquals(HttpStatus.NOT_FOUND, stringResponseEntity.getStatusCode());

    }

    @Test
    void update__returnStatus200AndStudent() {
        facultyRepository.save(faculty);
        Student savedStudent = studentRepository.save(student);

        ResponseEntity<Student> response = restTemplate.exchange(

                "http://localhost:" + port + "/student",
                HttpMethod.PUT,
                new HttpEntity<>(savedStudent),
                Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(student.getName(), response.getBody().getName());
        assertEquals(student.getAge(), response.getBody().getAge());

    }

    @Test
    public void deleteStudent__returnStatus200() {
        facultyRepository.save(faculty);
        Student test = studentRepository.save(student);
        student.setFaculty(faculty);

        String url = "http://localhost:" + port + "/student/" + test.getId();

        ResponseEntity<Student> responseEntity = restTemplate.exchange(
                url,                // URL эндпоинта удаления
                HttpMethod.DELETE,  // HTTP метод DELETE
                null,               // Запрос-тело (в данном случае не используется)
                Student.class          // Ожидаемый тип ответа (класс, так как мы ожидаем только статус 200)
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    void readAge__returnStatus200AndStudentsList() {

        int ageToSearch = 25;
        ResponseEntity<List<Student>> response = restTemplate.exchange(
                "http://localhost:" + port + "/student/age/" + ageToSearch,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                }
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(200);


    }

    @Test
    public void testFindByAgeBetween() {
        int minAge = 20;
        int maxAge = 30;
        ResponseEntity<List<Student>> response = restTemplate.exchange(
                "http://localhost:" + port + "/student/age/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                },
                minAge, maxAge
        );
        assertThat(response.getStatusCodeValue()).isEqualTo(200); // не понял
    }

    @Test
    public void findStudentFaculty__returnStatus200() {

        Faculty f = facultyRepository.save(faculty);
        student.setFaculty(f);
        Student s = studentRepository.save(student);

        ResponseEntity<Faculty> responseEntity = restTemplate.
                exchange("http://localhost:" + port + "/student/" +
                        s.getId() + "/faculty", HttpMethod.GET, null, Faculty.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), f);


    }

    @Test
    void findAllStudents__returnStatus200AndStudentsList() {

        String url = "http://localhost:" + port + "/student/all";
        ResponseEntity<List<Student>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                });
        List<Student> students = responseEntity.getBody();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(students);
    }

}













