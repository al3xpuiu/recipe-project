package com.bishoptod3.converters;

import com.bishoptod3.commands.RecipeCommand;
import com.bishoptod3.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by Loky on 13/09/2018.
 */
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        return null;
    }
}
