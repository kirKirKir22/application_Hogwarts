package ru.hogwarts.school.service.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {

    @Mock
    FacultyRepository facultyRepository;

    @InjectMocks
    FacultyServiceImpl underTest;

    Faculty faculty = new Faculty(1L, "Юрфак", "синий");

    @Test
    void create_newFaculty_addAndReturn() {
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        Faculty res = underTest.create(faculty);
        assertEquals(faculty, res);

    }

    @Test
    void read_FacultyInDatabase_addAndReturn() {
        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.ofNullable(faculty));
        Faculty res = underTest.reade(faculty.getId());
        assertEquals(faculty, res);
    }

    @Test
    void update_FacultyInDatabase_addAndReturn(){
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        Faculty res = underTest.update(faculty);
        assertEquals(faculty,res);
    }

    @Test
    void delete__FacultyInDatabase_delete(){
        doNothing().when(facultyRepository).deleteById(faculty.getId());
        underTest.delete(faculty.getId());
        verify(facultyRepository,times(1)).deleteById(faculty.getId());
    }

    @Test
    void findByColor_areFacultyWithColorInDatabase_returnListWithFacultyByColor(){

        when(facultyRepository.findByColor(faculty.getColor())).thenReturn(List.of(faculty));
        List<Faculty> res = underTest.findByColor(faculty.getColor());
        assertEquals(List.of(faculty),res);

    }

}