package com.example.backend.dto.assignments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TotalAssignmentCountDTO {
    private long homeworkCount;
    private long labCount;
    private long quizCount;
}
