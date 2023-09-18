package ru.hogwarts.school.service.implementation;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import ru.hogwarts.school.exception.AvatarException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.interfaces.StudentService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    @Test
    void GetPage__returnListOfAvatars() {

        int pageNo = 0;
        int size = 10;

        List<Avatar> expectedAvatarList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Avatar avatar = new Avatar();
            avatar.setId((long) i);
            expectedAvatarList.add(avatar);
        }

        Page<Avatar> expectedPage = new PageImpl<>(expectedAvatarList);

        when(avatarRepository.findAll(PageRequest.of(pageNo, size))).thenReturn(expectedPage);
        List<Avatar> result = avatarService.getPage(pageNo, size);
        assertEquals(expectedAvatarList, result);
    }
}