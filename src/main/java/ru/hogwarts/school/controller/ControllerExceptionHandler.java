package ru.hogwarts.school.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.hogwarts.school.exception.FacultyCRUDException;
import ru.hogwarts.school.exception.StudentCRUDException;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(StudentCRUDException.class)
    public ResponseEntity<String> handleStudentException(StudentCRUDException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(FacultyCRUDException.class)
    public ResponseEntity<String> handleStudentException(FacultyCRUDException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}


