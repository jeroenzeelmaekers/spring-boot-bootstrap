package bootstrap.services;

import bootstrap.dtos.note.CreateNoteDto;
import bootstrap.entities.Note;
import bootstrap.exception.NoNoteFoundException;

public interface NoteService {

    Note getNoteById(Long id) throws NoNoteFoundException;

    Note create(CreateNoteDto createNoteDto);
}
