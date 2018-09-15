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
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private IngredientCommandToIngredient ingredientCommandToIngredient;
    private NotesCommandToNotes notesCommandToNotes;
    private CategoryCommandToCategory categoryCommandToCategory;

    @Autowired
    public RecipeCommandToRecipe(IngredientCommandToIngredient ingredientCommandToIngredient,
                                 NotesCommandToNotes notesCommandToNotes,
                                 CategoryCommandToCategory categoryCommandToCategory) {
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.notesCommandToNotes = notesCommandToNotes;
        this.categoryCommandToCategory = categoryCommandToCategory;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(@Nullable RecipeCommand source) {

        if (source == null)
            return null;

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setCookTime(source.getCookTime());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setDirections(source.getDirections());
        recipe.setIngredients(source.getIngredients().stream().map(ingredientCommandToIngredient::convert)
                .collect(Collectors.toSet()));
        recipe.setImage(source.getImage());
        recipe.setNotes(notesCommandToNotes.convert(source.getNotes()));
        recipe.setDifficulty(source.getDifficulty());
        recipe.setCategories(source.getCategories().stream().map(categoryCommandToCategory::convert)
                .collect(Collectors.toSet()));

        return recipe;
    }
}
