package com.example.backend.service;

import com.example.backend.dto.GenericResponseDTO;
import com.example.backend.dto.assignments.AssignmentDTO;
import com.example.backend.dto.assignments.AssignmentRequestDTO;
import com.example.backend.dto.assignments.LatestAssignmentDTO;
import com.example.backend.dto.assignments.TotalAssignmentCountDTO;
import com.example.backend.exception.AssignmentExistsException;
import com.example.backend.exception.AssignmentNotFoundException;
import com.example.backend.exception.CourseNotFoundException;
import com.example.backend.model.Assignment;
import com.example.backend.model.Course;
import com.example.backend.repository.AssignmentRepository;
import com.example.backend.repository.CourseRepository;
import com.example.backend.status.AssignmentType;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;

    public GenericResponseDTO createAssignment(Integer courseId, AssignmentRequestDTO request) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalCourse.isEmpty()) {
            throw new CourseNotFoundException("Course with id not found");
        }

        if (assignmentRepository.findByNameAndCourseId(request.getAssignmentName(), courseId).isPresent()) {
            throw new AssignmentExistsException("Assignment with name exists");
        }

        Assignment assignment = Assignment.builder()
                .name(request.getAssignmentName())
                .dueDate(request.getDueDate())
                .status(request.getStatus())
                .type(request.getType())
                .grade(request.getGrade())
                .course(optionalCourse.get())
                .build();

        assignmentRepository.save(assignment);

        return GenericResponseDTO.builder()
                .message("Assignment created successfully")
                .status(HttpStatus.CREATED.value())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public List<AssignmentDTO> getAllAssignments() {
        return assignmentRepository.findAll().stream().map(this::toAssignmentDTO).toList();
    }

    public TotalAssignmentCountDTO getTotalAssignmentCount() {
        return TotalAssignmentCountDTO.builder()
                .homeworkCount(assignmentRepository.countByType(AssignmentType.HOMEWORK))
                .labCount(assignmentRepository.countByType(AssignmentType.LAB))
                .quizCount(assignmentRepository.countByType(AssignmentType.QUIZ))
                .build();
    }

    public List<AssignmentDTO> getAssignmentsThisWeek() {
        LocalDateTime startOfWeek = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atStartOfDay();
        LocalDateTime endOfWeek = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).toLocalDate().atTime(23, 59, 59, 999_999_999);
        return assignmentRepository.findByDueDateBetween(startOfWeek, endOfWeek).stream().map(this::toAssignmentDTO).toList();
    }
    public LatestAssignmentDTO getLatestAssignment() {
        Optional<Assignment> optionalAssignment = assignmentRepository.findFirstByOrderByCreatedAtDesc();

        if (optionalAssignment.isEmpty()) {
            throw new AssignmentNotFoundException("There are no assignments");
        }

        Assignment assignment = optionalAssignment.get();

        return LatestAssignmentDTO.builder()
                .assignmentName(assignment.getName())
                .dueDate(assignment.getDueDate())
                .courseCode(assignment.getCourse().getCourseCode())
                .courseProfessor(assignment.getCourse().getProfessorName())
                .build();
    }

    public AssignmentDTO toAssignmentDTO(Assignment assignment) {
        return AssignmentDTO.builder()
                .id(assignment.getId())
                .courseId(assignment.getCourse().getId())
                .name(assignment.getName())
                .dueDate(assignment.getDueDate())
                .status(assignment.getStatus())
                .type(assignment.getType())
                .grade(assignment.getGrade())
                .build();
    }
}
