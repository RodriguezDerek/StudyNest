package com.example.backend.controller;

import com.example.backend.dto.courses.CourseDTO;
import com.example.backend.dto.GenericResponseDTO;
import com.example.backend.dto.courses.CourseRequestDTO;
import com.example.backend.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/course")
    public ResponseEntity<GenericResponseDTO> createCourse(@Valid @RequestBody CourseRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(request));
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllCourses());
    }

    @GetMapping("/semester")
    public ResponseEntity<List<CourseDTO>> getCoursesBySemester(@RequestParam String semester) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCoursesBySemester(semester));
    }

    @PutMapping("/course/{id}")
    public ResponseEntity<GenericResponseDTO> updateCourse(@PathVariable Integer id, @Valid @RequestBody CourseRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.updateCourse(id, request));
    }

    @DeleteMapping("/course/{id}")
    public ResponseEntity<GenericResponseDTO> deleteCourse(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.deleteCourse(id));
    }
}
