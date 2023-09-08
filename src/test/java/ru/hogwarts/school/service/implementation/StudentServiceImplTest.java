package ru.hogwarts.school.service.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

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
    void read_studentInDatabase_readAndReturn() {

        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student));
        Student result = underTest.read(1L);
        assertEquals(student, result);

    }

    @Test
    void update_studentInDatabase_updateAndReturn() {

        when(studentRepository.save(student)).thenReturn(student);
        Student result = underTest.update(student);
        assertEquals(student, result);

    }

    @Test
    void delete_studentInDatabase_delete() {

        doNothing().when(studentRepository).deleteById(student.getId());
        underTest.delete(student.getId());
        verify(studentRepository, times(1)).deleteById(student.getId());

    }

    @Test
    void findByAge_areStudentWithAgeInDatabase_returnListWithStudentByAge() {

        when(studentRepository.findByAge(student.getAge())).thenReturn(List.of(student));
        List<Student> result = underTest.findByAge(student.getAge());
        assertEquals(List.of(student), result);

    }


}