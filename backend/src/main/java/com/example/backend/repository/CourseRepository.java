package com.example.backend.repository;

import com.example.backend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByName(String name);
    Optional<Course> findByCourseCode(String code);
    List<Course> findAllBySemester(String semester);
    boolean existsByNameAndIdNot(String name, Integer id);
    boolean existsByCourseCodeAndIdNot(String courseCode, Integer id);
}
