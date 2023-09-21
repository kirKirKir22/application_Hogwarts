package ru.hogwarts.school.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.interfaces.AvatarService;
import ru.hogwarts.school.service.interfaces.StudentService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService {

    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    private String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(@Value("${path.to.avatars.folder}")
                             String avatarsDir,
                             StudentService studentService,
                             AvatarRepository avatarRepository) {
        this.avatarsDir = avatarsDir;
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentService.read(studentId);

        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = avatarRepository.findByStudent_id(studentId).orElse(new Avatar());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);

        logger.info("Аватар успешно загружен для студента c id" + studentId);
    }

    @Override
    public Avatar readFromDB(long id) {
        logger.info("Аватар найден:");
        return avatarRepository.findById(id).
                orElseThrow(() -> new AvatarException("аватар не найден"));
    }

    @Override
    public List<Avatar> getPage(int pageNo, int size) {
        PageRequest request = PageRequest.of(pageNo, size);
        List<Avatar> avatars = avatarRepository.findAll(request).getContent();
        logger.info("Получена страница  с аватарами страница" + pageNo + " размер" + size);
        return avatars;
    }

    private String getExtensions(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");

        if (lastDotIndex != -1 && lastDotIndex < fileName.length() - 1) {
            String extension = fileName.substring(lastDotIndex + 1);

            logger.info("Расширение файла '{}' определено как '{}'", fileName, extension);

            return extension;
        } else {

            logger.warn("Расширение файла '{}' не найдено или не корректно", fileName);

            return "";
        }
    }
}

/* return fileName.substring(fileName.lastIndexOf(".") + 1);*/




