package com.example.backend.dto.assignments;

import com.example.backend.status.AssignmentStatus;
import com.example.backend.status.AssignmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentDTO {
    private Integer id;
    private Integer courseId;
    private String title;
    private LocalDateTime dueDate;
    private AssignmentStatus status;
    private AssignmentType type;
    private Float grade;
}
