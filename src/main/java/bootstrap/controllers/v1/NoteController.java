package bootstrap.controllers.v1;

import bootstrap.dtos.note.CreateNoteDto;
import bootstrap.entities.Note;
import bootstrap.exception.NoNoteFoundException;
import bootstrap.services.NoteService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/note")
public record NoteController(NoteService noteService) {

    @GetMapping
    ResponseEntity<?> getAll() {

        List<Note> notes;

        notes = noteService.getAll();

        return ResponseEntity.ok(notes);

    }

    @GetMapping(value = "/{noteId}")
    ResponseEntity<?> getById(@PathVariable(value = "noteId") Long id) {

        Note note;

        try {
            note = noteService.getNoteById(id);
        } catch (NoNoteFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        log.info(String.format("Getting %s", note.toString()));

        return ResponseEntity.ok(note);
    }

    @PostMapping(value = "")
    ResponseEntity<?> create(@RequestBody CreateNoteDto createNoteDto) {

        Note note;

        try {
            note = noteService.create(createNoteDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        log.info(String.format("Created %s", note.toString()));

        return ResponseEntity.ok(note);
    }

}
