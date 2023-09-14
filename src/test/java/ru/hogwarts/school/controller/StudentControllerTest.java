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
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.exception.StudentException;
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
        ResponseEntity<String> stringResponseEntity = restTemplate.getForEntity("http://localhost:" + port + "/student" + student.getId(), String.class);

        assertEquals(HttpStatus.NOT_FOUND, stringResponseEntity.getStatusCode());

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

    @Test
    void readAgeTest() {

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
    public void testUpdateStudent() {


    }

    @Test
    public void testDeleteStudent() {

       /* facultyRepository.save(faculty);
        studentRepository.save(student);
        ResponseEntity<Void> response = restTemplate.exchange
        ( "http://localhost:" + port + "/student" + student.getId(), HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(studentRepository.findById(student.getId())).isEmpty();*/
    }

    /*@Test
    public void testFindByAgeBetween() {

        int minAge = 20;
        int maxAge = 30;


        ResponseEntity<List<Student>> response = restTemplate.exchange(
                "http://localhost:" + port + "/student/age/{min}/{max}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {},
                minAge, maxAge
        );


        assertThat(response.getStatusCodeValue()).isEqualTo(200);
*/


    @Test
    public void testFindStudentFaculty() {

        Long studentId = 1L;

        ResponseEntity<Faculty> response = restTemplate.getForEntity("http://localhost:" + port + "/student/{id}/faculty", Faculty.class, studentId);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        Faculty faculty = response.getBody();
        assertThat(faculty).isNotNull();


    }


}















