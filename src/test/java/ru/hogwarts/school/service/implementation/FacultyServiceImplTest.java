package ru.hogwarts.school.service.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

import static java.util.List.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {

    @Mock
    FacultyRepository facultyRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    FacultyServiceImpl underTest;

    Faculty faculty = new Faculty(1L, "Юрфак", "синий");
    Student student = new Student(1L, "Igor", 10, faculty);

    @Test
    void create_newFaculty_addAndReturn() {

        when(facultyRepository.save(faculty)).thenReturn(faculty);
        Faculty res = underTest.create(faculty);
        assertEquals(faculty, res);
    }

    @Test
    void create_FacultyInDatabase_throwFacultyCRUDException() {

        when(facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()))
                .thenReturn(Optional.of(faculty));
        FacultyException result = assertThrows(FacultyException.class, () -> underTest.create(faculty));
        assertEquals("такой факультет уже есть в базе", result.getMessage());

    }

    @Test
    void read_FacultyInDatabase_addAndReturn() {

        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.ofNullable(faculty));
        Faculty res = underTest.read(faculty.getId());
        assertEquals(faculty, res);
    }

    @Test
    void read_FacultyNotInDatabase_throwFacultyCRUDException() {

        when(facultyRepository.findById(1L)).thenReturn(Optional.empty());
        FacultyException result = assertThrows(FacultyException.class, () -> underTest.read(1L));
        assertEquals("факультет в базе не найден", result.getMessage());
    }

    @Test
    void update_FacultyInDatabase_addAndReturn() {

        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        Faculty result = underTest.update(faculty);
        assertEquals(faculty, result);
    }

    @Test
    void update_FacultyNotInDatabase_throwFacultyCRUDException() {

        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.empty());
        FacultyException result = assertThrows(FacultyException.class, () -> underTest.update(faculty));
        assertEquals("факультет в базе не найден", result.getMessage());
    }


    @Test
    void delete_FacultyInDatabase_deleteAndReturn() {

        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        doNothing().when(facultyRepository).deleteById(1L);
        Faculty result = underTest.delete(1L);
        assertEquals(faculty, result);
    }

    @Test
    void delete_FacultyNotInDatabase_throwFacultyCRUDException() {

        when(facultyRepository.findById(1L)).thenReturn(Optional.empty());
        FacultyException result = assertThrows(FacultyException.class, () -> underTest.read(1L));
        assertThrows(FacultyException.class, () -> underTest.read(1L));
        assertEquals("факультет в базе не найден", result.getMessage());

    }

    @Test
    void findByColor_areFacultyWithColorInDatabase_returnListWithFacultyByColor() {

        when(facultyRepository.findByColor(faculty.getColor())).thenReturn(of(faculty));
        List<Faculty> res = underTest.findByColor(faculty.getColor());
        assertEquals(of(faculty), res);

    }

    @Test
    void findByColor_areNotFacultyWithColorInDatabase_returnListEmptyList() {

        when(facultyRepository.findByColor("синий")).thenReturn(new ArrayList<>());
        List<Faculty> result = underTest.findByColor("синий");
        List<Faculty> expected = Collections.<Faculty>emptyList();
        assertEquals(expected, result);

    }

    @Test
    void findByNameOrColor_areFacultyWithColorOrNameInDatabase_returnListWithFacultyByColorOrName() {
        List<Faculty> facultyList = List.of(faculty);

        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(faculty.getName(), faculty.getColor())).thenReturn(facultyList);
        List<Faculty> result = underTest.findByNameIgnoreCaseOrColorIgnoreCase(faculty.getName(), faculty.getColor());
        assertEquals(1, result.size());
        assertEquals(faculty, result.get(0));
    }

    @Test
    void findStudentByFaculty_studentsInFaculty_returnListStudents() {

        List<Student> studentList = List.of(student);

        when(studentRepository.findByFacultyId(faculty.getId())).thenReturn(studentList);
        List<Student> result = underTest.findStudentsByFaculty(1L);

        assertEquals(studentList, result);

    }

    @Test
    public void findLongestNameByFaculty__NotEmptyRepository_returnedLongestNameByFaculty() {

        Faculty faculty = new Faculty(1L, "Юрфак", "синий");
        Faculty faculty1 = new Faculty(2L, "педфак", "жёлтый");
        Faculty faculty2 = new Faculty(3L, "иняз", "красный");
        Faculty faculty3 = new Faculty(4L, "психологический", "белый");

        List<Faculty> facultyList = List.of(faculty, faculty1, faculty2, faculty3);

        when(facultyRepository.findAll()).thenReturn(facultyList);
        String result = underTest.findLongestNameByFaculty();

        Optional<String> expectedLongestName = facultyList.stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length));

        assertTrue(expectedLongestName.isPresent());
        assertEquals(expectedLongestName.get(), result);
        assertEquals("психологический", result);

    }

    @Test
    public void findLongestNameByFaculty__EmptyRepository_returnedLongestNameByFaculty() {

        when(facultyRepository.findAll()).thenReturn(Collections.emptyList());

        FacultyException exception = assertThrows(FacultyException.class, () -> {
            underTest.findLongestNameByFaculty();
        });

        assertEquals("в БД нет факультетов", exception.getMessage());
    }


}