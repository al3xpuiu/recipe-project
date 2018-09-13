package com.bishoptod3.converters;

import com.bishoptod3.commands.NotesCommand;
import com.bishoptod3.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by Loky on 13/09/2018.
 */
@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(@Nullable Notes source) {

        if (source == null)
            return null;

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId( source.getId() );
        notesCommand.setRecipeNotes( source.getRecipeNotes() );

        return notesCommand;
    }
}
