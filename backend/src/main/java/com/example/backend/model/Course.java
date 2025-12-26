package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    @NotEmpty(message = "Course name is required")
    private String name;

    @Column(name = "course_code", nullable = false, unique = true)
    @NotEmpty(message = "Course code is required")
    private String courseCode;

    @Column(name = "room_code", nullable = false)
    @NotEmpty(message = "Room code is required")
    private String roomCode;

    @Column(name = "location", nullable = false)
    @NotEmpty(message = "Location is required")
    private String location;

    @Column(name = "professor_name", nullable = false)
    @NotEmpty(message = "Professor name is required")
    private String professorName;

    @Column(name = "professor_email", nullable = false)
    @Email(message = "Invalid email format")
    @NotEmpty(message = "Professor email is required")
    private String professorEmail;

    @Column(name = "department", nullable = false)
    @NotEmpty(message = "Department is required")
    private String department;

    @Column(name = "semester", nullable = false)
    @NotEmpty(message = "Semester is required")
    private String semester;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignment> assignments;

    @JsonManagedReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
