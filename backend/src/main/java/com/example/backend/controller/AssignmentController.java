package com.example.backend.controller;

import com.example.backend.dto.GenericResponseDTO;
import com.example.backend.dto.assignments.AssignmentDTO;
import com.example.backend.dto.assignments.AssignmentRequestDTO;
import com.example.backend.dto.assignments.LatestAssignmentDTO;
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
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsThisWeek() {
        return ResponseEntity.status(HttpStatus.OK).body(assignmentService.getAssignmentsThisWeek());
    }

    @GetMapping("/assignment/latest")
    public ResponseEntity<LatestAssignmentDTO> getLatestAssignment() {
        return ResponseEntity.status(HttpStatus.OK).body(assignmentService.getLatestAssignment());
    }

    @PutMapping("/assignment/{assignmentId}")
    public ResponseEntity<?> updateAssignment(@PathVariable Integer assignmentId, @Valid @RequestBody AssignmentRequestDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(assignmentService.updateAssignment(assignmentId, request));
    }

    @DeleteMapping("/assignment/{assignmentId}")
    public ResponseEntity<?> deleteAssignment(@PathVariable Integer assignmentId) {
        return ResponseEntity.status(HttpStatus.OK).body(assignmentService.deleteAssignment(assignmentId));
    }
}
