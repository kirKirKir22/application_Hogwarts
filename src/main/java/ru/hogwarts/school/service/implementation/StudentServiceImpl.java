package ru.hogwarts.school.service.implementation;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentCRUDException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.interfaces.StudentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final Map<Long, Student> studentMap = new HashMap<>();

    private long counterId = 1L;

    @Override
    public Student create(Student student) {
        if (studentMap.containsValue(student)) {
            throw new StudentCRUDException("такой студент уже существует");
        }
        student.setId(counterId++);
        studentMap.put(student.getId(), student);
        return student;
    }

    @Override
    public Student reade(long id) {
        if (!studentMap.containsKey(id)) {
            throw new StudentCRUDException("студент не найден");
        }
        return studentMap.get(id);
    }


    @Override
    public Student update(Student student) {
        if (!studentMap.containsKey(student.getId())) {
            throw new StudentCRUDException("студент не найден");
        }
        studentMap.put(student.getId(), student);
        return student;
    }

    @Override
    public Student delete(long id) {
        Student student = studentMap.remove(id);
        if (student == null) {
            throw new StudentCRUDException("студент не найден");
        }

        return student;
    }

    @Override
    public List<Student> findAge(int age) {
        return studentMap.values().stream()
                .filter(st -> st.getAge() == age)
                .collect(Collectors.toUnmodifiableList());

    }
}
