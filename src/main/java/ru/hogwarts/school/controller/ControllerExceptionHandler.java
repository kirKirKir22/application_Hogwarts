package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ru.hogwarts.school.exception.AvatarException;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.service.implementation.AvatarServiceImpl;


@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    @ExceptionHandler({StudentException.class, FacultyException.class, AvatarException.class})
    public ResponseEntity<String> handleHogwartsException(RuntimeException ex) {

        logger.warn(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}


