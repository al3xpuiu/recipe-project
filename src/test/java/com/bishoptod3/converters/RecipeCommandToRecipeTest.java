package com.bishoptod3.converters;

import com.bishoptod3.commands.*;
import com.bishoptod3.domain.Category;
import com.bishoptod3.domain.Difficulty;
import com.bishoptod3.domain.Ingredient;
import com.bishoptod3.domain.Recipe;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Loky on 13/09/2018.
 */
public class RecipeCommandToRecipeTest {

    private RecipeCommandToRecipe converter;


    private static final Long ID_RECIPE_COMMAND = 1L;
    private static final String DESCRIPTION_RECIPE_COMMAND = "Recipe command description";
    private static final Integer PREP_TIME = 5;
    private static final Integer COOK_TIME = 10;
    private static final Integer SERVINGS = 4;
    private static final String SOURCE = "Some website";
    private static final String URL = "www.test.com";
    private static final String DIRECTIONS = "Some not so complicated directions";
    private static final Difficulty DIFFICULTY = Difficulty.MODERATE;

    private static final Long ID_INGREDIENT_COMMAND = 2L;
    private static final String DESCRIPTION_INGREDIENT_COMMAND = "Ingredient command description";
    private static final Long ID_UNIT_OF_MEASURE_COMMAND = 3L;
    private static final String DESCRIPTION_UNIT_OF_MEASURE_COMMAND ="Unit of measure command description";

    private static final Long ID_NOTES_COMMAND = 4L;
    private static final String DESCRIPTION_NOTES_COMMAND = "Notes command description";
    private static final Long ID_CATEGORY_COMMAND = 5L;
    private static final String DESCRIPTION_CATEGORY_COMMAND = "Category command description";

    @Before
    public void setUp() {
        CategoryCommandToCategory categoryCommandConverter = new CategoryCommandToCategory();
        NotesCommandToNotes notesCommandConverter = new NotesCommandToNotes();

        UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureConverter = new UnitOfMeasureCommandToUnitOfMeasure();
        IngredientCommandToIngredient ingredientCommandConverter = new IngredientCommandToIngredient(unitOfMeasureConverter);

        converter = new RecipeCommandToRecipe(ingredientCommandConverter, notesCommandConverter,
                categoryCommandConverter);
    }

    @Test
    public void testNullObject() {
        Assert.assertNull(converter.convert(null));
    }

    @Test
    public void testNotNullObject() {
        Assert.assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void testConverter() {

        //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID_CATEGORY_COMMAND);
        categoryCommand.setDescription(DESCRIPTION_CATEGORY_COMMAND);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID_NOTES_COMMAND);
        notesCommand.setRecipeNotes(DESCRIPTION_NOTES_COMMAND);

        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(ID_UNIT_OF_MEASURE_COMMAND);
        unitOfMeasureCommand.setDescription(DESCRIPTION_UNIT_OF_MEASURE_COMMAND);
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID_INGREDIENT_COMMAND);
        ingredientCommand.setDescription(DESCRIPTION_INGREDIENT_COMMAND);
        ingredientCommand.setUom(unitOfMeasureCommand);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID_RECIPE_COMMAND);
        recipeCommand.setDescription(DESCRIPTION_RECIPE_COMMAND);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setDifficulty(DIFFICULTY);

        Set<IngredientCommand> ingredientCommands = new HashSet<>();
        ingredientCommands.add(ingredientCommand);
        recipeCommand.setIngredients(ingredientCommands);

        recipeCommand.setNotes(notesCommand);

        Set<CategoryCommand> categoryCommands = new HashSet<>();
        categoryCommands.add(categoryCommand);
        recipeCommand.setCategories(categoryCommands);

        //when
        Recipe recipe = converter.convert(recipeCommand);

        //then
        Assert.assertNotNull(recipe);
        Assert.assertEquals(ID_RECIPE_COMMAND, recipe.getId());
        Assert.assertEquals(DESCRIPTION_RECIPE_COMMAND, recipe.getDescription());
        Assert.assertEquals(PREP_TIME, recipe.getPrepTime());
        Assert.assertEquals(COOK_TIME, recipe.getCookTime());
        Assert.assertEquals(SERVINGS, recipe.getServings());
        Assert.assertEquals(SOURCE, recipe.getSource());
        Assert.assertEquals(URL, recipe.getUrl());
        Assert.assertEquals(DIRECTIONS, recipe.getDirections());
        Assert.assertEquals(DIFFICULTY, recipe.getDifficulty());
        Assert.assertEquals(1, recipe.getIngredients().size());
        Assert.assertEquals(1, recipe.getCategories().size());
        Assert.assertNotNull(recipe.getIngredients());
        Assert.assertNotNull(recipe.getCategories());

        Ingredient ingredient = recipe.getIngredients()
                .stream()
                .findFirst()
                .orElse(null);
        Category category = recipe.getCategories()
                .stream()
                .findFirst()
                .orElse(null);
        Assert.assertNotNull(ingredient);
        Assert.assertNotNull(category);
        Assert.assertEquals(ID_INGREDIENT_COMMAND, ingredient.getId());
        Assert.assertEquals(DESCRIPTION_INGREDIENT_COMMAND, ingredient.getDescription());
        Assert.assertEquals(ID_CATEGORY_COMMAND, category.getId());
        Assert.assertEquals(DESCRIPTION_CATEGORY_COMMAND, category.getDescription());

    }
}