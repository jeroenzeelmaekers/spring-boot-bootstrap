package bootstrap.services.impl;

import bootstrap.dtos.note.CreateNoteDto;
import bootstrap.entities.Note;
import bootstrap.exception.NoNoteFoundException;
import bootstrap.repositories.NoteRepository;
import bootstrap.services.NoteService;
import org.springframework.stereotype.Service;

@Service
public record NoteServiceImpl(NoteRepository noteRepository) implements NoteService {

    @Override
    public Note getNoteById(Long id) throws NoNoteFoundException {
        return noteRepository.findById(id).orElseThrow(() ->
                new NoNoteFoundException("There is no note with id:" + id));
    }

    @Override
    public Note create(CreateNoteDto createNoteDto) {
        Note note = Note
                .builder()
                .content(createNoteDto.content())
                .build();

        return noteRepository.save(note);
    }

}
