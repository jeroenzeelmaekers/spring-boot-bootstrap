package bootstrap.controllers;

import bootstrap.controllers.v1.NoteController;
import bootstrap.dtos.note.CreateNoteDto;
import bootstrap.entities.Note;
import bootstrap.exception.NoNoteFoundException;
import bootstrap.services.NoteService;
import bootstrap.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ActiveProfiles(value = "test")
@WebMvcTest(NoteController.class)
public class NoteControllerTests {

    @MockBean
    private NoteService noteService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @SneakyThrows
    public void getById_ShouldReturnAnExistingNote_WhenAValidIdIsGiven() {

        Note note = Note.builder()
                .id(1L)
                .content("Test Note")
                .build();

        when(noteService.getNoteById(1L)).thenReturn(note);

        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/v1/note/1"))
                    .andExpect(MockMvcResultMatchers.status()
                            .isOk())
                    .andExpect(MockMvcResultMatchers
                            .content()
                            .json(JsonUtil.toJson(note)));
        } catch (JsonProcessingException e) {
            fail("Failed to convert to json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(noteService, times(1)).getNoteById(1L);

    }

    @Test
    @SneakyThrows
    public void getById_ShouldReturnAnErrorMessage_WhenAnInvalidNoteIdIsGiven() {

        long id = 1L;

        when(noteService.getNoteById(1L)).thenThrow(new NoNoteFoundException("There is no note with id " + id));

        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/v1/note/1"))
                    .andExpect(MockMvcResultMatchers.status()
                            .isOk())
                    .andExpect(MockMvcResultMatchers
                            .content()
                            .string("There is no note with id " + id));
        } catch (JsonProcessingException e) {
            fail("Failed to convert to json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(noteService, times(1)).getNoteById(1L);

    }

    @Test
    @SneakyThrows
    public void create_ShouldSuccessfullyCreateANNewNote() {

        CreateNoteDto createNoteDto = CreateNoteDto.builder()
                .content("Test note")
                .build();

        Note note = Note.builder()
                .id(1L)
                .content("Test note")
                .build();

        when(noteService.create(createNoteDto)).thenReturn(note);

        try {
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/v1/note")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(JsonUtil.toJson(createNoteDto)))
                    .andExpect(MockMvcResultMatchers.status()
                            .isOk())
                    .andExpect(MockMvcResultMatchers
                            .content()
                            .json(JsonUtil.toJson(note)));
        } catch (JsonProcessingException e) {
            fail("Failed to convert to json");
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(noteService, times(1)).create(createNoteDto);

    }

}
