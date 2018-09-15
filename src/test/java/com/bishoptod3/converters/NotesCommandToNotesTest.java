package com.bishoptod3.converters;

import com.bishoptod3.commands.NotesCommand;
import com.bishoptod3.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Loky on 13/09/2018.
 */
public class NotesCommandToNotesTest {

    private NotesCommandToNotes converter;
    private static final Long ID = 1L;
    private static final String RECIPE_NOTES = "Some notes";

    @Before
    public void setUp() {
        converter = new NotesCommandToNotes();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testNotNullObject() {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    public void testConverter() {

        //given
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID);
        notesCommand.setRecipeNotes(RECIPE_NOTES);

        //when
        Notes notes = converter.convert(notesCommand);

        //then
        assertNotNull(notes);
        assertEquals(ID, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getRecipeNotes());

    }

}