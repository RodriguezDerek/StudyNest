package com.example.backend.dto.notes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {
    private Integer id;
    private Integer courseId;
    private String title;
    private String content;
}
