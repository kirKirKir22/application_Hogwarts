package ru.hogwarts.school.service.implementation;

import nonapi.io.github.classgraph.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.interfaces.StudentService;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AvatarServiceImplTest {

    StudentService studentService = mock(StudentService.class);

    AvatarRepository avatarRepository = mock(AvatarRepository.class);

    String avatarsDir = "./src/test/resources/avatar";

    AvatarServiceImpl avatarService = new AvatarServiceImpl(avatarsDir, studentService, avatarRepository);


    @Test
    void uploadAvatar__avatarSavedToDbAndDirectory() throws IOException {

        Long studentId = 1L;
        Student student = new Student();
        when(studentService.read(studentId)).thenReturn(student);

        MockMultipartFile avatarFile = new MockMultipartFile(
                "10.pdf", "10.pdf", "pdf",new byte[]{});

        when(avatarRepository.findByStudent_id(studentId)).thenReturn(Optional.empty());

        avatarService.uploadAvatar(studentId, avatarFile);

        verify(studentService, times(1)).read(studentId);
        verify(avatarRepository, times(1)).findByStudent_id(studentId);
        verify(avatarRepository, times(1)).save(any(Avatar.class));
    }

       /* MultipartFile file = new MockMultipartFile
                ("10.pdf", "10.pdf", "pdf", new byte[]{});

        when(studentService.read(student.getId())).thenReturn(student);
        when(avatarRepository.findByStudent_id(student.getId())).thenReturn(Optional.empty());
        avatarService.uploadAvatar(student.getId(), file);

        verify(avatarRepository, times(1)).save(any());
        assertTrue(FileUtils.canRead(new File(avatarsDir + "/" + student.getId() + "." + file.getContentType())));
    }*/


    @Test
    void readFromDB_avatarExists_returnedAvatar() {

        long id = 1L;
        Avatar expectedAvatar = new Avatar();
        when(avatarRepository.findById(id)).thenReturn(Optional.of(expectedAvatar));
        Avatar result = avatarService.readFromDB(id);

        assertNotNull(result);
        assertEquals(expectedAvatar, result);
        verify(avatarRepository, times(1)).findById(id);
    }

    @Test
    void readFromDB_avatarNotExists_throwException() {

        long id = 1L;
        when(avatarRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AvatarException.class, () -> avatarService.readFromDB(id));

    }
}