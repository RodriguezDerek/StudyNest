package com.example.backend.controller;

import com.example.backend.dto.GenericResponseDTO;
import com.example.backend.dto.assignments.AssignmentDTO;
import com.example.backend.dto.assignments.AssignmentRequestDTO;
import com.example.backend.dto.assignments.TotalAssignmentCountDTO;
import com.example.backend.service.AssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping("/assignment/{courseId}")
    public ResponseEntity<GenericResponseDTO> createAssignment(@PathVariable Integer courseId, @Valid @RequestBody AssignmentRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(assignmentService.createAssignment(courseId, request));
    }

    @GetMapping
    public ResponseEntity<List<AssignmentDTO>> getAllAssignments() {
        return ResponseEntity.status(HttpStatus.OK).body(assignmentService.getAllAssignments());
    }

    @GetMapping("/count")
    public ResponseEntity<TotalAssignmentCountDTO> getTotalAssignmentCount() {
        return ResponseEntity.status(HttpStatus.OK).body(assignmentService.getTotalAssignmentCount());
    }

    @GetMapping("/week")
    public ResponseEntity<?> getAssignmentsThisWeek() {
        return null;
    }

    @GetMapping("/assignment/latest")
    public ResponseEntity<?> getLatestAssignment() {
        return null;
    }

    @PutMapping("/assignment/{courseId}")
    public ResponseEntity<?> updateAssignment() {
        return null;
    }

    @DeleteMapping("/assignment/{courseId}")
    public ResponseEntity<?> deleteAssignment() {
        return null;
    }
}
