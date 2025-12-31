package com.example.backend.service;

import com.example.backend.dto.notes.NoteDTO;
import com.example.backend.model.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteService {

    public NoteDTO toNoteDTO(Note note) {
        return NoteDTO.builder()
                .id(note.getId())
                .courseId(note.getCourse().getId())
                .title(note.getTitle())
                .content(note.getContent())
                .build();
    }
}
