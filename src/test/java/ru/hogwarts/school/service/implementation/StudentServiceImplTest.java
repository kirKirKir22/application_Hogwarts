package ru.hogwarts.school.service.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import ru.hogwarts.school.exception.StudentCRUDException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentServiceImpl underTest;

    Student student = new Student(1L, "Igor", 10);

    @Test
    void create_newStudent_addAndReturn() {

        when(studentRepository.save(student)).thenReturn(student);
        Student result = underTest.create(student);
        assertEquals(student, result);

    }

    @Test
    void create_StudentInDatabase_throwStudentCRUDException() {

        when(studentRepository.findByNameAndAge(student.getName(), student.getAge()))
                .thenReturn(Optional.of(student));
        StudentCRUDException result = assertThrows(StudentCRUDException.class, () -> underTest.create(student));
        assertEquals("такой студент уже есть в базе данных", result.getMessage());

    }

    @Test
    void read_studentInDatabase_readAndReturn() {

        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student));
        Student result = underTest.read(1L);
        assertEquals(student, result);

    }

    @Test
    void read_StudentNotInDatabase_throwStudentCRUDException() {

        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        StudentCRUDException result = assertThrows(StudentCRUDException.class, () -> underTest.read(1L));
        assertEquals("студент в базе не найден", result.getMessage());

    }

    @Test
    void update_studentInDatabase_updateAndReturn() {

        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);
        Student result = underTest.update(student);
        assertEquals(student, result);

    }

    @Test
    void update_StudentNotInDatabase_throwStudentCRUDException() {

        when(studentRepository.findById(student.getId())).thenReturn(Optional.empty());
        StudentCRUDException result = assertThrows(StudentCRUDException.class, () -> underTest.update(student));
        assertEquals("студент в базе не найден", result.getMessage());
    }

    @Test
    void delete_studentInDatabase_deleteAndReturn() {

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).deleteById(1L);
        Student result = underTest.delete(1L);
        assertEquals(student, result);
    }

    @Test
    void delete_StudentNotInDatabase_throwStudentCRUDException() {

        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        StudentCRUDException result = assertThrows(StudentCRUDException.class, () -> underTest.read(1L));
        assertThrows(StudentCRUDException.class, () -> underTest.read(1L));
        assertEquals("студент в базе не найден", result.getMessage());

    }

    @Test
    void findByAge_areStudentWithAgeInDatabase_returnListWithStudentByAge() {

        when(studentRepository.findByAge(student.getAge())).thenReturn(List.of(student));
        List<Student> result = underTest.findByAge(student.getAge());
        assertEquals(List.of(student), result);

    }


    @Test
    void findByAge_areNotStudentWithAgeInDatabase_returnEmptyList() {

        when(studentRepository.findByAge(10)).thenReturn(new ArrayList<Student>());
        List<Student> result = underTest.findByAge(10);
        List<Student> expected = Collections.<Student>emptyList();
        assertEquals(expected, result);

    }

}