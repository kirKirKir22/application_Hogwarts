package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FacultyControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    Faculty faculty = new Faculty(1L, "Юрфак", "синий");
    Student student = new Student(1L, "Igor", 10, faculty);

    @Test
    public void testCreateFaculty() {

        ResponseEntity<Faculty> response = restTemplate.postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(faculty.getName(), response.getBody().getName());
        assertEquals(faculty.getColor(), response.getBody().getColor());


    }
}