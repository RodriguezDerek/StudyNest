package com.example.backend.controller;

import com.example.backend.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping("/assignment/{courseId}")
    public ResponseEntity<?> createAssignment(@PathVariable Integer courseId) {
        return null;
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> getAssignmentsByCourse(@PathVariable Integer courseId) {
        // Format Assignments based on status - COMPLETED, INPROGESS, NOTSTARTED
        return null;
    }

    @GetMapping
    public ResponseEntity<?> getAllAssignments() {
        // Format Assignments based on type - HW, LAB, Quiz
        return null;
    }

    @GetMapping("/week")
    public ResponseEntity<?> getAssignmentsThisWeek() {
        return null;
    }

    @GetMapping("/assignment/latest")
    public ResponseEntity<?> getLatestAssignment() {
        return null;
    }
}
