package com.example.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false, unique = true)
    @NotEmpty(message = "Event title is required")
    private String title;

    @Column(name = "description", nullable = false)
    @NotEmpty(message = "Event description is required")
    private String description;

    @Column(name = "location", nullable = false)
    @NotEmpty(message = "Location is required")
    private String location;

    @Column(name = "date", nullable = false)
    @NotNull(message = "Date is required")
    private LocalDateTime date;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
