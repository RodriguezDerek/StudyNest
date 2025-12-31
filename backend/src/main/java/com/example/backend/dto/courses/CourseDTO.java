package com.example.backend.dto.courses;

import com.example.backend.dto.assignments.AssignmentDTO;
import com.example.backend.dto.notes.NoteDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private Integer id;
    private String name;
    private String courseCode;
    private String roomCode;
    private String location;
    private String professorName;
    private String professorEmail;
    private String department;
    private String semester;
    private List<NoteDTO> notes;
    private List<CourseMeetingDTO> courseMeetings;
    private List<AssignmentDTO> assignments;
    private long completedAssignments;
    private long inProgressAssignments;
    private long notStartedAssignments;
    private double progressPercent;
}
