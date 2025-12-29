package com.example.backend.service;

import com.example.backend.dto.assignments.AssignmentDTO;
import com.example.backend.model.Assignment;
import com.example.backend.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public static AssignmentDTO toAssignmentDTO(Assignment assignment) {
        return AssignmentDTO.builder()
                .id(assignment.getId())
                .courseId(assignment.getCourse().getId())
                .title(assignment.getTitle())
                .dueDate(assignment.getDueDate())
                .status(assignment.getStatus())
                .type(assignment.getType())
                .grade(assignment.getGrade())
                .build();
    }
}
