package com.example.backend.service;

import com.example.backend.dto.GenericResponseDTO;
import com.example.backend.dto.courses.CourseDTO;
import com.example.backend.dto.courses.CourseMeetingDTO;
import com.example.backend.dto.courses.CourseRequestDTO;
import com.example.backend.exception.CourseExistsException;
import com.example.backend.exception.CourseNotFoundException;
import com.example.backend.exception.InvalidDayOfWeekException;
import com.example.backend.model.Course;
import com.example.backend.model.CourseMeeting;
import com.example.backend.repository.CourseMeetingRepository;
import com.example.backend.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMeetingRepository courseMeetingRepository;

    public GenericResponseDTO createCourse(CourseRequestDTO request) {
        if (courseRepository.findByName(request.getCourseName()).isPresent() || courseRepository.findByCourseCode(request.getCourseCode()).isPresent()) {
            throw new CourseExistsException("A course with the same name or code already exists.");
        }

        List<String> courseDays = request.getCourseDays();
        checkCourseDays(courseDays);

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

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(this::toCourseDTO).toList();
    }

    public List<CourseDTO> getCoursesBySemester(String semester) {
        return courseRepository.findAllBySemester(semester).stream().map(this::toCourseDTO).toList();
    }

    public GenericResponseDTO updateCourse(Integer id, CourseRequestDTO request) {
        Optional<Course> optionalCourse = courseRepository.findById(id);

        if (optionalCourse.isEmpty()) {
            throw new CourseNotFoundException("Course with id not found");
        }

        List<String> courseDays = request.getCourseDays();
        checkCourseDays(courseDays);

        if (courseRepository.existsByNameAndIdNot(request.getCourseName(), id) || courseRepository.existsByCourseCodeAndIdNot(request.getCourseCode(), id)) {
            throw new CourseExistsException("Course with name or code already exists");
        }

        Course course = optionalCourse.get();

        // If nothing changed, skip update
        if (isCourseDataUnChanged(course, request) && isCourseMeetingsDataUnChanged(course, request, courseDays)) {
            return GenericResponseDTO.builder()
                    .message("No changes detected. Course not updated.")
                    .status(HttpStatus.OK.value())
                    .timeStamp(LocalDateTime.now())
                    .build();
        }

        course.setName(request.getCourseName());
        course.setCourseCode(request.getCourseCode());
        course.setRoomCode(request.getCourseRoomCode());
        course.setLocation(request.getCourseLocation());
        course.setProfessorName(request.getCourseProfessor());
        course.setProfessorEmail(request.getCourseProfessorEmail());
        course.setDepartment(request.getCourseDepartment());
        course.setSemester(request.getCourseSemester());

        courseRepository.save(course);

        if (!isCourseMeetingsDataUnChanged(course, request, courseDays)) {
            course.getCourseMeetings().clear();

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
        }

        return GenericResponseDTO.builder()
                .message("Course updated successfully")
                .status(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public GenericResponseDTO deleteCourse(Integer id) {
        if (courseRepository.findById(id).isEmpty()) {
            throw new CourseNotFoundException("Course with id not found");
        }

        courseRepository.deleteById(id);

        return GenericResponseDTO.builder()
                .message("Course deleted successfully")
                .status(HttpStatus.OK.value())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    private static boolean isCourseMeetingsDataUnChanged(Course course, CourseRequestDTO request, List<String> courseDays) {
        // Check if CourseMeeting fields are unchanged
        Set<DayOfWeek> existingDays = course.getCourseMeetings().stream()
                .map(CourseMeeting::getDayOfWeek)
                .collect(Collectors.toSet());

        Set<DayOfWeek> requestDays = courseDays.stream()
                .map(String::toUpperCase)
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toSet());

        return existingDays.equals(requestDays) && course.getCourseMeetings().stream()
                .allMatch(m -> m.getStartTime().equals(request.getStartTime()) && m.getEndTime().equals(request.getEndTime()));
    }

    private static boolean isCourseDataUnChanged(Course course, CourseRequestDTO request) {
        // Check if Course fields are unchanged
        return course.getName().equals(request.getCourseName()) &&
                course.getCourseCode().equals(request.getCourseCode()) &&
                course.getRoomCode().equals(request.getCourseRoomCode()) &&
                course.getLocation().equals(request.getCourseLocation()) &&
                course.getProfessorName().equals(request.getCourseProfessor()) &&
                course.getProfessorEmail().equals(request.getCourseProfessorEmail()) &&
                course.getDepartment().equals(request.getCourseDepartment()) &&
                course.getSemester().equals(request.getCourseSemester());
    }

    private static void checkCourseDays(List<String> courseDays) {
        Set<String> validDays = Set.of("MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY", "SUNDAY");
        for (String day : courseDays) {
            if (!validDays.contains(day.toUpperCase())) {
                throw new InvalidDayOfWeekException("Course meeting day '" + day + "' is not valid.");
            }
        }
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
