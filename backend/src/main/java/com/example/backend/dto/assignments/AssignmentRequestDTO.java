package com.example.backend.dto.assignments;

import com.example.backend.status.AssignmentStatus;
import com.example.backend.status.AssignmentType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentRequestDTO {
    @NotBlank(message = "Assignment name is required")
    @Size(max = 100, message = "Assignment name cannot exceed 100 characters")
    private String assignmentName;

    @NotNull(message = "Due date is required")
    private LocalDateTime dueDate;

    @NotNull(message = "Assignment status is required")
    private AssignmentStatus status;

    @NotNull(message = "Assignment type is required")
    private AssignmentType type;

    @DecimalMin(value = "0.0", inclusive = true, message = "Grade cannot be negative")
    @DecimalMax(value = "100.0", inclusive = true, message = "Grade cannot exceed 100")
    private Float grade;
}
