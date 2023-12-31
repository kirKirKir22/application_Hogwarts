package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.interfaces.FacultyService;

import java.util.List;


@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;


    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;

    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.create(faculty);
    }

    @GetMapping("/{id}")
    public Faculty read(@PathVariable long id) {
        return facultyService.read(id);
    }

    @PutMapping
    public Faculty update(@RequestBody Faculty faculty) {
        return facultyService.update(faculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id) {
        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/color/{color}")
    public List<Faculty> findColor(@PathVariable String color) {
        return facultyService.findByColor(color);
    }

    @GetMapping("/color/{color}/{name}")
    public List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase
            (@RequestParam(required = false) String name,
             @RequestParam(required = false) String color) {
        return facultyService.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @GetMapping("/{id}/student")
    public List<Student> findByFacultyId(@PathVariable long id) {
        return facultyService.findStudentsByFaculty(id);
    }

    @GetMapping("/all")
    public List<Faculty> findAllFaculties() {
        return facultyService.findAllFaculties();

    }

    @GetMapping("/longest-name")
    public String findLongestNameByFaculty() {
        return facultyService.findLongestNameByFaculty();
    }
}

