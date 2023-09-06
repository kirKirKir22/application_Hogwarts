package ru.hogwarts.school.service.implementation;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exception.FacultyCRUDException;
import ru.hogwarts.school.exception.StudentCRUDException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.interfaces.FacultyService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceImplTest {

    FacultyService underTest = new FacultyServiceImpl();

    Faculty faculty1 = new Faculty(1L, "Юрфак", "красный");
    Faculty faculty2 = new Faculty(1L, "Журфак", "красный");
    Faculty faculty3 = new Faculty(1L, "Педфак", "синий");

    @Test
    void create_facultyNotMap_facultyAdded() {

        Faculty result = underTest.create(faculty1);
        assertEquals(faculty1, result);
        assertEquals(1, faculty1.getId());


    }

    @Test
    void create_facultyInMap_throwException() {

        underTest.create(faculty1);
        assertThrows(FacultyCRUDException.class, () -> underTest.create(faculty1));


    }

    @Test
    void read_facultyInMap_facultyAdded() {

        underTest.create(faculty1);
        Faculty faculty = underTest.reade(1L);

        assertNotNull(faculty);
        assertEquals(1L, faculty.getId());
        assertEquals("Юрфак", faculty.getName());

    }

    @Test
    void read_facultyNotMap_throwException() {

        underTest.create(faculty1);
        Faculty faculty = underTest.reade(1L);

        assertThrows(FacultyCRUDException.class, () -> underTest.reade(2L));


    }

    @Test
    void update_facultyInMap_facultyAdded() {

        underTest.create(faculty1);
        Faculty updatedFaculty = new Faculty(1L, "Иняз", "жёлтый");
        Faculty result = underTest.update(updatedFaculty);

        assertEquals(updatedFaculty.getId(), result.getId());
        assertEquals(updatedFaculty.getName(), result.getName());
        assertEquals(updatedFaculty, underTest.reade(updatedFaculty.getId()));


    }

    @Test
    void update_facultyNotMap_throwException() {

        underTest.create(faculty1);
        Faculty updatedFaculty = new Faculty(3L, "Иняз", "жёлтый");

        assertThrows(FacultyCRUDException.class, () -> underTest.update(updatedFaculty));


    }

    @Test
    void delete_facultyInMap_facultyAdded(){
        underTest.create(faculty1);

        Faculty result = underTest.delete(1L);
        assertEquals(faculty1, result);
    }

    @Test
    void delete_facultyNotMap_throwException(){

        underTest.create(faculty1);
        Faculty result = underTest.delete(1L);
        assertThrows(FacultyCRUDException.class, () -> underTest.reade(2L));

    }

    @Test
    void findColor_facultyInMap_facultyAdded(){

        underTest.create(faculty1);
        underTest.create(faculty2);
        underTest.create(faculty3);

        String targetColor = "красный";
        List<Faculty> result = underTest.findColor(targetColor);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

    }

    @Test
    void findColor_facultyNotMap_facultyEmptyList(){

        underTest.create(faculty1);
        underTest.create(faculty2);
        underTest.create(faculty3);

        String targetColor = "чёрный";
        List<Faculty> result = underTest.findColor(targetColor);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());

    }
}