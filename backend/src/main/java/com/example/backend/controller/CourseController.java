package com.example.backend.controller;

import com.example.backend.dto.courses.CourseDTO;
import com.example.backend.dto.GenericResponseDTO;
import com.example.backend.dto.courses.CreateCourseRequestDTO;
import com.example.backend.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/courses")
    public ResponseEntity<GenericResponseDTO> createCourse(@Valid @RequestBody CreateCourseRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(request));
    }

    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return null;
    }

    public ResponseEntity<List<CourseDTO>> getCoursesByTerm() {
        return null;
    }

    public ResponseEntity<GenericResponseDTO> updateCourse() {
        return null;
    }

    public ResponseEntity<GenericResponseDTO> deleteCourse() {
        return null;
    }


}
