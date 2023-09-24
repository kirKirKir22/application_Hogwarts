package ru.hogwarts.school.service.interfaces;

import ru.hogwarts.school.model.Student;

public interface ThreadService {

    public void threadOne();

    public void threadTwo();

    public void studentLog(Student student);
}
