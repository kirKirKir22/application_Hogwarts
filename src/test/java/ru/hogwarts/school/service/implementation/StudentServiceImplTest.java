package ru.hogwarts.school.service.implementation;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exception.StudentCRUDException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.interfaces.StudentService;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {

    StudentService underTest = new StudentServiceImpl();

    Student student = new Student(1L, "Петя", 10);
    Student student1 = new Student(2L, "Вася", 10);
    Student student2 = new Student(3L, "Толик", 27);

    @Test
    void create_studentNotMap_studentAdded() {

        Student result = underTest.create(student);
        assertEquals(student, result);
        assertEquals(1, student.getId());
    }

    @Test
    void create_studentInMap_throwException() {

        underTest.create(student);
        assertThrows(StudentCRUDException.class, () -> underTest.create(student));
    }

    @Test
    void reade_studentInMap_returnedStudent() {

        underTest.create(student);
        Student student = underTest.reade(1L);

        assertNotNull(student);
        assertEquals(1L, student.getId());
        assertEquals("Петя", student.getName());

    }

    @Test
    void reade_studentNotMap_throwException() {

        underTest.create(student);
        Student student = underTest.reade(1L);

        assertThrows(StudentCRUDException.class, () -> underTest.reade(2L));
    }

    @Test
    void update_studentInMap_returnedStudent() {
        underTest.create(student);
        Student updatedStudent = new Student(1L, "Вова", 15);
        Student result = underTest.update(updatedStudent);

        assertEquals(updatedStudent.getId(), result.getId());
        assertEquals(updatedStudent.getName(), result.getName());
        assertEquals(updatedStudent, underTest.reade(updatedStudent.getId()));


    }

    @Test
    void update_studentNotMap_returnedStudent() {

        underTest.create(student);
        Student updatedStudent = new Student(3L, "Вова", 15);

        assertThrows(StudentCRUDException.class, () -> underTest.update(updatedStudent));


    }

    @Test
    void delete_studentInMap_returnedStudent() {
        underTest.create(student);

        Student result = underTest.delete(1L);
        assertEquals(student, result);

    }

    @Test
    void delete_studentNotMap_throwException() {
        underTest.create(student);
        Student result = underTest.delete(1L);
        assertThrows(StudentCRUDException.class, () -> underTest.reade(2L));

    }

    @Test
    void findAge_studentsInMap_returnedStudents() {
        underTest.create(student);
        underTest.create(student1);
        underTest.create(student2);

        int targetAge = 10;
        List<Student> result = underTest.findAge(targetAge);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

    }

    @Test
    void findAge_studentsNotMap_returnedEmptyList() {

        underTest.create(student);
        underTest.create(student1);
        underTest.create(student2);

        int targetAge = 50;
        List<Student> result = underTest.findAge(targetAge);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());

    }
}
