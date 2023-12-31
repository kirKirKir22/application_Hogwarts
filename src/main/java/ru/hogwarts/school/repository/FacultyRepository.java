package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Optional<Faculty> findByNameAndColor(String name, String color);

    List<Faculty> findByColor(String color);

    List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

}
