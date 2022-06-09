package bootstrap.services;

import java.util.List;

import bootstrap.dtos.note.CreateNoteDto;
import bootstrap.entities.Note;
import bootstrap.exception.NoNoteFoundException;

public interface NoteService {

    List<Note> getAll();

    Note getNoteById(Long id) throws NoNoteFoundException;

    Note create(CreateNoteDto createNoteDto);

}
