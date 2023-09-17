package ru.hogwarts.school.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.implementation.FacultyServiceImpl;
import java.util.List;
import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FacultyController.class)
public class FacultyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FacultyController facultyController;

    @MockBean
    FacultyRepository facultyRepository;

    @MockBean
    StudentRepository studentRepository;
    @SpyBean
    FacultyServiceImpl facultyService;

    @Autowired
    ObjectMapper objectMapper;

    Faculty faculty = new Faculty(1L, "иняз", "синий");

    @Test
    void create__returnStatus200() throws Exception {
        when(facultyRepository.save(faculty)).thenReturn(faculty);

        mockMvc.perform(post("/faculty")
                        .content(objectMapper.writeValueAsBytes(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

    }


    @Test
    void read__returnFacultyAndStatus200() throws Exception {

        when(facultyRepository.findById(anyLong())).thenReturn(of(faculty));

        mockMvc.perform(get("/faculty/" + faculty.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

    }


    @Test
    void update__returnNewFacultyAndStatus200() throws Exception {

        when(facultyRepository.save(any())).thenReturn(faculty);
        when(facultyRepository.findById(any())).thenReturn(of(faculty));

        mockMvc.perform(put("/faculty")
                        .content(objectMapper
                                .writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));


    }

    @Test
    void delete__returnNewFacultyAndStatus200() throws Exception {

        when(facultyRepository.save(any())).thenReturn(faculty);
        when(facultyRepository.findById(any())).thenReturn(of(faculty));

        mockMvc.perform(delete("/faculty/" + faculty.getId()))
                .andExpect(status().isOk());


    }

    //endregion
    @Test
    void findColor__returnStatus200() throws Exception {

        when(facultyRepository.findByColor(faculty.getColor())).thenReturn(List.of(faculty));
        mockMvc.perform(get("/faculty/color/" + faculty.getColor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(faculty.getName()))
                .andExpect(jsonPath("$.[0].color").value(faculty.getColor()));
    }

    @Test
    void findByNameIgnoreCaseOrColorIgnoreCase_returnStatus200AndListStudents() throws Exception {

        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase
                (faculty.getName(),faculty.getColor())).thenReturn(List.of(faculty));
        mockMvc.perform(get("/faculty/color/" + faculty.getName() + faculty.getColor()))
                .andExpect(status().isOk());

    }

    @Test
    void findByFacultyId_returnStatus200AndListStudents() throws Exception {

        Student student = new Student(1L, "Ron", 10, faculty);

        when(studentRepository.findByFacultyId(anyLong())).thenReturn(List.of(student));
        mockMvc.perform(get("/faculty/" + faculty.getId() + "/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(student.getName()))
                .andExpect(jsonPath("$.[0].age").value(student.getAge()));


    }

    @Test
    void findAllFaculties__returnStatus200AndListStudents() throws Exception {
        when(facultyRepository.findAll()).thenReturn(List.of(faculty));

        mockMvc.perform(get("/faculty/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(faculty.getName()))
                .andExpect(jsonPath("$.[0].color").value(faculty.getColor()));


    }

}