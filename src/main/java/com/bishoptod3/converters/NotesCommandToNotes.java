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
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(@Nullable NotesCommand source) {

        if (source == null)
            return null;

        Notes notes = new Notes();
        notes.setId( source.getId() );
        notes.setRecipeNotes( source.getRecipeNotes() );

        return notes;
    }
}
