package com.bishoptod3.services;

import com.bishoptod3.commands.RecipeCommand;
import com.bishoptod3.converters.RecipeToRecipeCommand;
import com.bishoptod3.domain.Recipe;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by DD on 9/15/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

    private static final String DESCRIPTION = "Some new description";

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Test
    @Transactional
    public void testSaveOfDescription() {

        //given
        Set<Recipe> recipes = recipeService.getRecipes();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        //when
        Assert.assertNotNull(testRecipeCommand);
        testRecipeCommand.setDescription(DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        //then
        Assert.assertNotNull(savedRecipeCommand);
        Assert.assertEquals(DESCRIPTION, savedRecipeCommand.getDescription());
        Assert.assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        Assert.assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        Assert.assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());

        testRecipe = recipeService.findById(savedRecipeCommand.getId());

        Assert.assertEquals(testRecipe.getDescription(), savedRecipeCommand.getDescription());
        Assert.assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        Assert.assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        Assert.assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());

    }
}
