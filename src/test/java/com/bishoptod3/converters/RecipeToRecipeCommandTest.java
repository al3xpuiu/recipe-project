package com.bishoptod3.converters;

import com.bishoptod3.commands.CategoryCommand;
import com.bishoptod3.commands.IngredientCommand;
import com.bishoptod3.commands.RecipeCommand;
import com.bishoptod3.domain.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Loky on 13/09/2018.
 */
public class RecipeToRecipeCommandTest {

    private RecipeToRecipeCommand converter;

    private static final Long ID_RECIPE = 1L;
    private static final String DESCRIPTION_RECIPE = "Recipe description";
    private static final Integer PREP_TIME = 5;
    private static final Integer COOK_TIME = 10;
    private static final Integer SERVINGS = 4;
    private static final String SOURCE = "Some website";
    private static final String URL = "www.test.com";
    private static final String DIRECTIONS = "Some not so complicated directions";
    private static final Difficulty DIFFICULTY = Difficulty.MODERATE;

    private static final Long ID_INGREDIENT = 2L;
    private static final String DESCRIPTION_INGREDIENT = "Ingredient description";
    private static final Long ID_UNIT_OF_MEASURE = 3L;
    private static final String DESCRIPTION_UNIT_OF_MEASURE = "Unit of measure description";

    private static final Long ID_NOTES = 4L;
    private static final String DESCRIPTION_NOTES = "Notes description";
    private static final Long ID_CATEGORY = 5L;
    private static final String DESCRIPTION_CATEGORY = "Category description";

    @Before
    public void setUp() {
        CategoryToCategoryCommand categoryConverter = new CategoryToCategoryCommand();
        NotesToNotesCommand notesConverter = new NotesToNotesCommand();

        UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureConverter = new UnitOfMeasureToUnitOfMeasureCommand();
        IngredientToIngredientCommand ingredientConverter = new IngredientToIngredientCommand(unitOfMeasureConverter);

        converter = new RecipeToRecipeCommand(ingredientConverter, notesConverter,
                categoryConverter);
    }

    @Test
    public void testNullObject() {
        Assert.assertNull(converter.convert(null));
    }

    @Test
    public void testNotNullObject() {
        Assert.assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    public void testConverter() {

        //given
        Category category = new Category();
        category.setId(ID_CATEGORY);
        category.setDescription(DESCRIPTION_CATEGORY);

        Notes notes = new Notes();
        notes.setId(ID_NOTES);
        notes.setRecipeNotes(DESCRIPTION_NOTES);

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(ID_UNIT_OF_MEASURE);
        unitOfMeasure.setDescription(DESCRIPTION_UNIT_OF_MEASURE);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_INGREDIENT);
        ingredient.setDescription(DESCRIPTION_INGREDIENT);
        ingredient.setUom(unitOfMeasure);

        Recipe recipe = new Recipe();
        recipe.setId(ID_RECIPE);
        recipe.setDescription(DESCRIPTION_RECIPE);
        recipe.setPrepTime(PREP_TIME);
        recipe.setCookTime(COOK_TIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setDirections(DIRECTIONS);
        recipe.setDifficulty(DIFFICULTY);

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(ingredient);
        recipe.setIngredients(ingredients);

        recipe.setNotes(notes);

        Set<Category> categories = new HashSet<>();
        categories.add(category);
        recipe.setCategories(categories);

        //when
        RecipeCommand recipeCommand = converter.convert(recipe);

        //then
        Assert.assertNotNull(recipeCommand);
        Assert.assertEquals(ID_RECIPE, recipeCommand.getId());
        Assert.assertEquals(DESCRIPTION_RECIPE, recipeCommand.getDescription());
        Assert.assertEquals(PREP_TIME, recipeCommand.getPrepTime());
        Assert.assertEquals(COOK_TIME, recipeCommand.getCookTime());
        Assert.assertEquals(SERVINGS, recipeCommand.getServings());
        Assert.assertEquals(SOURCE, recipeCommand.getSource());
        Assert.assertEquals(URL, recipeCommand.getUrl());
        Assert.assertEquals(DIRECTIONS, recipeCommand.getDirections());
        Assert.assertEquals(DIFFICULTY, recipeCommand.getDifficulty());
        Assert.assertEquals(1, recipeCommand.getIngredients().size());
        Assert.assertEquals(1, recipeCommand.getCategories().size());
        Assert.assertNotNull(recipeCommand.getIngredients());
        Assert.assertNotNull(recipeCommand.getCategories());

        IngredientCommand ingredientCommand = recipeCommand.getIngredients()
                .stream()
                .findFirst()
                .orElse(null);
        CategoryCommand categoryCommand = recipeCommand.getCategories()
                .stream()
                .findFirst()
                .orElse(null);
        Assert.assertNotNull(ingredientCommand);
        Assert.assertNotNull(categoryCommand);
        Assert.assertEquals(ID_INGREDIENT, ingredientCommand.getId());
        Assert.assertEquals(DESCRIPTION_INGREDIENT, ingredientCommand.getDescription());
        Assert.assertEquals(ID_CATEGORY, categoryCommand.getId());
        Assert.assertEquals(DESCRIPTION_CATEGORY, categoryCommand.getDescription());

    }

}