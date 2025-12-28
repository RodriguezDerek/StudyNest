package com.example.backend.service;

import com.example.backend.dto.GenericResponseDTO;
import com.example.backend.dto.courses.CourseDTO;
import com.example.backend.dto.courses.CourseMeetingDTO;
import com.example.backend.dto.courses.CreateCourseRequestDTO;
import com.example.backend.exception.CourseExistsException;
import com.example.backend.exception.InvalidDayOfWeekException;
import com.example.backend.model.Course;
import com.example.backend.model.CourseMeeting;
import com.example.backend.repository.CourseMeetingRepository;
import com.example.backend.repository.CourseRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMeetingRepository courseMeetingRepository;

    public GenericResponseDTO createCourse(CreateCourseRequestDTO request) {
        if (courseRepository.findByName(request.getCourseName()).isPresent() || courseRepository.findByCourseCode(request.getCourseCode()).isPresent()) {
            throw new CourseExistsException("A course with the same name or code already exists.");
        }

        List<String> courseDays = request.getCourseDays();
        for (String day : courseDays) {
            if (!Set.of("MON","TUE","WED","THU","FRI","SAT","SUN").contains(day.toUpperCase())) {
                throw new InvalidDayOfWeekException("Course meeting day '" + day + "' is not valid.");
            }
        }

        Course course = Course.builder()
                .name(request.getCourseName())
                .courseCode(request.getCourseCode())
                .roomCode(request.getCourseRoomCode())
                .location(request.getCourseLocation())
                .professorName(request.getCourseProfessor())
                .professorEmail(request.getCourseProfessorEmail())
                .department(request.getCourseDepartment())
                .semester(request.getCourseSemester())
                .build();

        courseRepository.save(course);

        for (String day : courseDays) {
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(day.toUpperCase().strip());

            CourseMeeting courseMeeting = CourseMeeting.builder()
                    .course(course)
                    .dayOfWeek(dayOfWeek)
                    .startTime(request.getStartTime())
                    .endTime(request.getEndTime())
                    .build();

            courseMeetingRepository.save(courseMeeting);
        }

        return GenericResponseDTO.builder()
                .message("The course has been successfully created.")
                .status(HttpStatus.CREATED.value())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public CourseDTO toCourseDTO(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .courseCode(course.getCourseCode())
                .roomCode(course.getRoomCode())
                .location(course.getLocation())
                .professorName(course.getProfessorName())
                .professorEmail(course.getProfessorEmail())
                .department(course.getDepartment())
                .semester(course.getSemester())
                .assignments(course.getAssignments().stream().map(AssignmentService::toAssignmentDTO).toList())
                .notes(course.getNotes().stream().map(NoteService::toNoteDTO).toList())
                .courseMeetings(course.getCourseMeetings().stream().map(this::toCourseMeetingDTO).toList())
                .build();
    }

    public CourseMeetingDTO toCourseMeetingDTO(CourseMeeting courseMeeting) {
        return CourseMeetingDTO.builder()
                .id(courseMeeting.getId())
                .courseId(courseMeeting.getCourse().getId())
                .dayOfWeek(courseMeeting.getDayOfWeek())
                .startTime(courseMeeting.getStartTime())
                .endTime(courseMeeting.getEndTime())
                .build();
    }
}
