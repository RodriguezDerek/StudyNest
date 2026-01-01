package com.example.backend.repository;

import com.example.backend.model.Assignment;
import com.example.backend.status.AssignmentStatus;
import com.example.backend.status.AssignmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    Optional<Assignment> findByNameAndCourseId(String name, Integer id);
    Optional<Assignment> findFirstByOrderByCreatedAtDesc();
    List<Assignment> findByDueDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    long countByCourseIdAndStatus(Integer courseId, AssignmentStatus status);
    long countByType(AssignmentType type);
    boolean existsByNameAndIdNot(String name, Integer id);
}
