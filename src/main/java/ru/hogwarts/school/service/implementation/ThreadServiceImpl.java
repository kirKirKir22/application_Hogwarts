package ru.hogwarts.school.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.interfaces.ThreadService;

import java.util.List;

@Service
public class ThreadServiceImpl implements ThreadService {

    private Logger logger = LoggerFactory.getLogger(ThreadServiceImpl.class);

    private StudentServiceImpl studentService;

    public ThreadServiceImpl(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }


    public void threadOne() {
        List<Student> all = studentService.findAllStudents();
        studentLog(all.get(0));
        studentLog(all.get(1));

        new Thread(() -> {
            studentLog(all.get(2));
            studentLog(all.get(3));
        }).start();

        new Thread(() -> {
            studentLog(all.get(4));
            studentLog(all.get(5));
        }).start();

    }

    public synchronized void threadTwo() {
        List<Student> all = studentService.findAllStudents();
        studentLog(all.get(0));
        studentLog(all.get(1));

        new Thread(() -> {
            studentLog(all.get(2));
            studentLog(all.get(3));
        }).start();

        new Thread(() -> {
            studentLog(all.get(4));
            studentLog(all.get(5));
        }).start();

    }

    public void studentLog(Student student) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info(student.toString());
    }
}