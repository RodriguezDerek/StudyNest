package com.example.backend.dto.courses;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCourseRequestDTO {
    @NotBlank(message = "Course name is required")
    @Size(max = 100, message = "Course name cannot exceed 100 characters")
    private String courseName;

    @NotBlank(message = "Course code is required")
    @Size(max = 20, message = "Course code cannot exceed 20 characters")
    private String courseCode;

    @NotBlank(message = "Semester is required")
    private String courseSemester;

    @Size(max = 120, message = "Location cannot exceed 120 characters")
    private String courseLocation;

    @Size(max = 20, message = "Room code cannot exceed 20 characters")
    private String courseRoomCode;

    @NotBlank(message = "Department is required")
    private String courseDepartment;

    @NotBlank(message = "Professor name is required")
    private String courseProfessor;

    @Email(message = "Invalid professor email format")
    @Size(max = 120, message = "Email cannot exceed 120 characters")
    private String courseProfessorEmail;

    @NotEmpty(message = "At least one class day is required")
    private List<@NotBlank(message = "Day value cannot be blank") String> courseDays;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @AssertTrue(message = "End time must be later than start time")
    public boolean isValidTimeRange() {
        if (startTime == null || endTime == null) return true;
        return endTime.isAfter(startTime);
    }
}
