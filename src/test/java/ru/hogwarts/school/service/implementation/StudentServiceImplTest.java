package ru.hogwarts.school.service.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentServiceImpl underTest;
    Faculty faculty = new Faculty(1L, "физкультурный", "малиновый");

    Student student = new Student(1L, "Igor", 10, faculty);

    Student student1 = new Student(2L, "Misha", 10, faculty);
    Student student2 = new Student(3L, "Petr", 15, faculty);
    Student student3 = new Student(4L, "Oleg", 20, faculty);


    List<Student> students = Arrays.asList(student1, student2, student3);


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
        StudentException result = assertThrows(StudentException.class, () -> underTest.create(student));
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
        StudentException result = assertThrows(StudentException.class, () -> underTest.read(1L));
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
        StudentException result = assertThrows(StudentException.class, () -> underTest.update(student));
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
        StudentException result = assertThrows(StudentException.class, () -> underTest.read(1L));
        assertThrows(StudentException.class, () -> underTest.read(1L));
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

    @Test
    void findByAgeBetween_areStudentWithAgeInDatabase_returnListWithStudentByAge() {

        when(studentRepository.findByAgeBetween(10, 20)).thenReturn(students);

        List<Student> result = underTest.findByAgeBetween(10, 20);

        assertEquals(3, result.size());
        assertEquals(student1, result.get(0));
        assertEquals(student2, result.get(1));
        assertEquals(student3, result.get(2));

        verify(studentRepository, times(1)).findByAgeBetween(10, 20);

    }

    @Test
    void findByAgeBetween_areNotStudentWithAgeInDatabase_throwStudentCRUDException() {

        when(studentRepository.findByAgeBetween(50, 60)).thenReturn(Arrays.asList());

        assertThrows(StudentException.class, () -> underTest.findByAgeBetween(50, 60));
        verify(studentRepository, times(1)).findByAgeBetween(50, 60);

    }

}