package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByNameAndAge(String name, int age);

    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(Integer min, Integer max);

    List<Student> findByFacultyId(long id);

    @Query("select (s) from Student s")
    Integer findStudentCount();

    @Query("select (s) from Student s")
    Integer findAvgAge();

    @Query(value = "select * from student s order by id desc limit: last", nativeQuery = true)
    List<Student> findLastStudent(int last);
}
