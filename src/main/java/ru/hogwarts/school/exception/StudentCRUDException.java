package ru.hogwarts.school.exception;

public class StudentCRUDException extends RuntimeException {
    public StudentCRUDException(String message) {
        super(message);
    }
}
