package com.example.backend.model;

import com.example.backend.status.AssignmentStatus;
import com.example.backend.status.AssignmentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    @NotBlank(message = "Assignment Title is required")
    private String name;

    @Column(name = "due_date", nullable = false)
    @NotNull(message = "Assignment due date is required")
    private LocalDateTime dueDate;

    @Column(name = "status", nullable = false)
    @NotNull(message = "Assignment status is required")
    @Enumerated(value = EnumType.STRING)
    private AssignmentStatus status;

    @Column(name = "type", nullable = false)
    @NotNull(message = "Assignment type is required")
    @Enumerated(value = EnumType.STRING)
    private AssignmentType type;

    @Column(name = "grade")
    @Min(value = 0, message = "Grade cannot be negative")
    private Float grade;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = AssignmentStatus.NOT_STARTED;
        }

        if (this.grade == null) {
            this.grade = 0f;
        }
    }
}
