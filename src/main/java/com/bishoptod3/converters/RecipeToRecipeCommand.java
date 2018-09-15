package com.bishoptod3.converters;

import com.bishoptod3.commands.RecipeCommand;
import com.bishoptod3.domain.Recipe;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Created by Loky on 13/09/2018.
 */
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private NotesToNotesCommand notesToNotesCommand;
    private CategoryToCategoryCommand categoryToCategoryCommand;

    @Autowired
    public RecipeToRecipeCommand(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 NotesToNotesCommand notesToNotesCommand,
                                 CategoryToCategoryCommand categoryToCategoryCommand) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.notesToNotesCommand = notesToNotesCommand;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(@Nullable Recipe source) {

        if (source == null)
            return null;

        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setIngredients(source.getIngredients().stream().map(ingredientToIngredientCommand::convert)
                .collect(Collectors.toSet()));
        recipeCommand.setImage(source.getImage());
        recipeCommand.setNotes(notesToNotesCommand.convert(source.getNotes()));
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setCategories(source.getCategories().stream().map(categoryToCategoryCommand::convert)
                .collect(Collectors.toSet()));

        return recipeCommand;
    }
}
