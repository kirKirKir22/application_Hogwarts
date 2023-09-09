package ru.hogwarts.school.service.interfaces;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {

    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar readFromDB(long id);

    List<Avatar> getPage(int pageNo, int size);
}


