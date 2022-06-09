package bootstrap.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.thedeanda.lorem.LoremIpsum;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import bootstrap.entities.Note;
import bootstrap.repositories.NoteRepository;

@Component
public record Seeder(NoteRepository noteRepository) {

    @EventListener
    private void SeedNotes(ContextRefreshedEvent event) {
        List<Note> notes = generateListOfNotes(100);
        noteRepository.saveAll(notes);
    }

    private List<Note> generateListOfNotes(int numberOfNotes) {
        List<Note> notes = new ArrayList<Note>();

        LoremIpsum lorum = LoremIpsum.getInstance();
        Random random = new Random();

        for (int i = 0; i < numberOfNotes; i++) {
            notes.add(Note.builder()
                    .content(lorum.getWords(random.nextInt(5, 20)))
                    .build());
        }

        return notes;
    }
}
