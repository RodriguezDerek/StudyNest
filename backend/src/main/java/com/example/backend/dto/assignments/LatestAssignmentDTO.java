package com.example.backend.dto.assignments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LatestAssignmentDTO {
    private String assignmentName;
    private LocalDateTime dueDate;
    private String courseCode;
    private String courseProfessor;
}
