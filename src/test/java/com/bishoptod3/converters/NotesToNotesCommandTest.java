package com.bishoptod3.converters;

import com.bishoptod3.commands.NotesCommand;
import com.bishoptod3.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Loky on 13/09/2018.
 */
public class NotesToNotesCommandTest {

    private NotesToNotesCommand converter;
    private static final Long ID = 1L;
    private static final String RECIPE_NOTES = "Some notes";

    @Before
    public void setUp() {
        converter = new NotesToNotesCommand();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testNotNullObject() {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    public void testConverter() {
        //given
        Notes notes = new Notes();
        notes.setId(ID);
        notes.setRecipeNotes(RECIPE_NOTES);

        //when
        NotesCommand notesCommand = converter.convert(notes);

        //then
        assertNotNull(notesCommand);
        assertEquals(ID, notesCommand.getId());
        assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
    }

}