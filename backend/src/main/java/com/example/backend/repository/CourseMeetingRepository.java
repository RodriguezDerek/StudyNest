package com.example.backend.repository;

import com.example.backend.model.CourseMeeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseMeetingRepository extends JpaRepository<CourseMeeting, Integer> {
}
