package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.interfaces.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student) { // сделан 1 часть
        return studentService.create(student);
    }

    @GetMapping("{id}")
    public Student read(@PathVariable long id) { // сделан 1 часть
        return studentService.read(id);
    }

    @PutMapping
    public Student update(@RequestBody Student student) { // не пошёл
        return studentService.update(student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id) { // не пошёл
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age/{age}")
    public List<Student> readAge(@PathVariable int age) { // сделан 1 часть
        return studentService.findByAge(age);

    }

    @GetMapping("/age/{ageMin}/{ageMax}")
    public List<Student> findByAgeBetween(@RequestParam int min, @RequestParam int max) { // не пошёл
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/{id}/faculty")
    public Faculty findStudentFaculty(@PathVariable Long id) { // не пошёл
        return studentService.findStudentFaculty(id);
    }

    @GetMapping("/all")
    public List<Student> findAllStudents() { // сделан 1 часть
        return studentService.findAllStudents();
    }
}
